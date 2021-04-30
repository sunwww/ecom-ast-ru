package ru.ecom.diary.ejb.service.protocol;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.helpers.DefaultHandler;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.external.ExternalCovidAnalysis;
import ru.ecom.mis.ejb.domain.licence.DocumentParameter;
import ru.ecom.mis.ejb.domain.licence.ExternalMedservice;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameter;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameterGroup;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocSexName;
import ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.ecom.poly.ejb.domain.voc.VocIllnesPrimary;
import ru.ecom.poly.ejb.domain.voc.VocReason;
import ru.ecom.poly.ejb.domain.voc.VocVisitResult;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileReader;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

@Stateless
@Remote(IKdlDiaryService.class)
public class KdlDiaryServiceBean extends DefaultHandler implements IKdlDiaryService {
	private static final Logger LOG = Logger.getLogger(KdlDiaryServiceBean.class);

	@Override
	public void importCovidAnalysis(String filename, String username) {
		importCovidAnalysis(filename, username,null, null,null,null, null,null);
	}
	//Импорт csv файла с результатами лаб. анализов на ковид
	@Override
	public void importCovidAnalysis(String filename, String username
	,Long workFunctionId, Long visitResultId
			, Long visitReasonId, Long primaryId, Long mkbId, Long workPlaceId)  {
		try {
			File file = new File(filename);
			HeaderColumnNameMappingStrategy<ExternalCovidAnalysis> strategy = new HeaderColumnNameMappingStrategy<>();
			strategy.setType(ExternalCovidAnalysis.class);
			CsvToBean<ExternalCovidAnalysis> bean = new CsvToBean<>();
			CSVParser parser = new CSVParserBuilder()
					.withSeparator(';')
					.withIgnoreQuotations(true)
					.build();

			CSVReader csvReader = new CSVReaderBuilder(new FileReader(file))
					.withSkipLines(0)
					.withCSVParser(parser)
					.build();

			bean.setMappingStrategy(strategy);
			bean.setCsvReader(csvReader);
			List<ExternalCovidAnalysis> anaList = bean.parse();
			for (ExternalCovidAnalysis a : anaList) {
				if (!StringUtil.isNullOrEmpty(a.getLastname())) {
					a.setCreateUsername(username);
					manager.persist(a);

				}

			}
			if (workFunctionId!=null) generateVisitByCovidAnalysis(anaList,workFunctionId, visitResultId, visitReasonId, primaryId, mkbId, workPlaceId, username);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}

	/** Находит либо создает пациента по ФИО
	 * */
	private Patient getOrCreatePatient(String lastname, String firstname, String middlename, Date birthday) {
		List<Patient> pats = manager.createNamedQuery("Patient.getByLastAndFirstAndMiddleAndBirthday").setParameter("lastname", lastname)
				.setParameter("firstname", firstname).setParameter("middlename", middlename).setParameter("birthday", birthday).getResultList();
		if (pats.isEmpty()) {
			Patient patient = new Patient();
			patient.setLastname(lastname);
			patient.setFirstname(firstname);
			patient.setMiddlename(middlename);
			patient.setBirthday(birthday);
			try {
				VocSexName sexx = (VocSexName) manager.createNamedQuery("VocSexName.getByName").setParameter("name",firstname).getSingleResult();
				patient.setSex(sexx.getSex());
			} catch (Exception e) {
				LOG.warn("no vocSex for name "+firstname,e);
			}
			manager.persist(patient);
			patient.setPatientSync("К"+patient.getId());
			manager.persist(patient);
			return patient;
		}
		return pats.size()==1 ? pats.get(0) : null;
	}

	//Имеются ли дубли по пациенту, врачу и дате визита
	private boolean hasDoubleByPatientAndDoctorAndDate(Long patientId, Long doctorId, Date visitDate ) {
		List list = manager.createNativeQuery("select id from medcase where patient_id =:patientId and datestart=:visitDate" +
				" and workfunctionexecute_id=:doctorId").setParameter("patientId", patientId).setParameter("doctorId", doctorId)
				.setParameter("visitDate", visitDate).getResultList();
		return !list.isEmpty();
	}
	//генерируем визит к врачу лаборатории
	private void generateVisitByCovidAnalysis(List<ExternalCovidAnalysis> analyses, Long workFunctionId, Long visitResultId
	, Long visitReasonId, Long primaryId, Long mkbId, Long workPlaceId,String username) {
		PolyclinicMedCase pmc;
		ShortMedCase ticket;
		Diagnosis diagnosis;
		PersonalWorkFunction wf = manager.find(PersonalWorkFunction.class, workFunctionId);
		VocServiceStream vss = manager.find(VocServiceStream.class,1L); //ОМС
		VocVisitResult result = manager.find(VocVisitResult.class, visitResultId);
		VocReason reason = manager.find(VocReason.class, visitReasonId);
		VocPriorityDiagnosis priorityDiagnosis = manager.find(VocPriorityDiagnosis.class, 1L) ; //всегда - основной.
		VocIllnesPrimary primary = manager.find(VocIllnesPrimary.class, primaryId);
		VocWorkPlaceType workPlace = manager.find(VocWorkPlaceType.class, workPlaceId);
		VocIdc10 mkb = manager.find(VocIdc10.class,mkbId);
		int i=0;
		for (ExternalCovidAnalysis a : analyses) {
			if (!StringUtil.isNullOrEmpty(a.getLastname())) {
				Patient patient = getOrCreatePatient(a.getLastname(), a.getFirstname(), a.getMiddlename(),a.getBirthday());
				Date visitDate = a.getDateResult();
				if (patient!=null && !hasDoubleByPatientAndDoctorAndDate(patient.getId(), workFunctionId, visitDate) ) {
					pmc = new PolyclinicMedCase();
					pmc.setPatient(patient);
					pmc.setStartFunction(wf);
					pmc.setOwnerFunction(wf);
					pmc.setFinishFunction(wf);
					pmc.setServiceStream(vss);
					pmc.setDateStart(visitDate);
					pmc.setDateFinish(visitDate);
					pmc.setUsername(username);
					pmc.setLpu(wf.getWorker().getLpu());
					pmc.setIdc10(mkb);
					manager.persist(pmc);

					ticket = new ShortMedCase();
					ticket.setParent(pmc);
					ticket.setPatient(patient);
					ticket.setUsername(username);
					ticket.setDateStart(visitDate);
					ticket.setServiceStream(vss);
					ticket.setWorkFunctionExecute(wf);
					ticket.setVisitResult(result);
					ticket.setVisitReason(reason);
					ticket.setWorkPlaceType(workPlace);
					manager.persist(ticket);

					diagnosis = new Diagnosis();
					diagnosis.setEstablishDate(visitDate);
					diagnosis.setPriority(priorityDiagnosis);
					diagnosis.setIdc10(mkb);
					diagnosis.setName(mkb.getName());
					diagnosis.setMedCase(ticket);
					diagnosis.setIllnesPrimary(primary);
					diagnosis.setCreateUsername(username);
					manager.persist(diagnosis);
					i++;
				}
			}
		}
		LOG.info("created visit = "+i);

	}

	@Override
	public String getDir(String aKey, String aDefaultValue) {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		return config.get(aKey, aDefaultValue) ;
	}

	@Override
	public void parseFile(String aUri) throws Exception {
		@SuppressWarnings("unchecked")
		List<ExternalMedservice> list = manager == null? new ArrayList<>() : manager.createQuery("FROM Document WHERE dtype='ExternalMedservice' AND referenceTo='"+aUri+"'").getResultList();
		ExternalMedservice externalMedservice = list.isEmpty() ? new ExternalMedservice() : list.get(0) ;
        comment = new StringBuilder();
        documentParameterGroups = new TreeMap<>();
    	documentParametersTree = new TreeMap<>();
        documentParameterObj = new HashMap<>();
    	documentParameterGroupsObj = new HashMap<>();
    	try {
    		printVariable("Start parse",aUri);
	    	File in = new File(aUri);
	        fileUri = aUri;
	        Document doc = new SAXBuilder().build(in);
	        Element root = doc.getRootElement();
	        Element header = root.getChild("Header");
	        parseHeader(header, externalMedservice);
	        persist(externalMedservice);
	        Element order = root.getChild("Order");
	        parseOrder(order, externalMedservice);
	        Element patient = order.getChild("Patient") ;
	        parsePatient(patient, externalMedservice);
			Element pages = order.getChild("Pages");
			parsePages(pages);
			Element tests = order.getChild("Tests");
			parseTests(tests, externalMedservice);
			prepareComment(externalMedservice);
			
			persist(externalMedservice);
    	} catch(Exception e){
    		e.printStackTrace() ;
    	    printVariable("FileException", e.getMessage());
    	    throw e;
    	}
    	
	printVariable("EndParse", toString(externalMedservice.getId()));    
	}

	private void parseHeader(Element aHeader, ExternalMedservice aExternalMedservice) {
		Date fileDate = toDate(aHeader.getChildText("FileDate"));
		Time fileTime = toTime(aHeader.getChildText("FileTime"));
		String laboratoryName = aHeader.getChildText("LaboratoryName");
		String clinicName = aHeader.getChildText("ClinicName");
		
		aExternalMedservice.setCreateDate(fileDate);
		aExternalMedservice.setCreateTime(fileTime);
		aExternalMedservice.setWhomIssued(laboratoryName);
		aExternalMedservice.setOrderLpu(clinicName);
		
	}
	private void parseOrder(Element order,ExternalMedservice aExternalMedservice)
	{
		String orderId = order.getAttributeValue("OrderID");
		Date orderDate = toDate(order.getChildText("OrderDate"));
		Time orderTime = toTime(order.getChildText("OrderTime"));
		String doctor = order.getChildText("Doctor");
		//String readyDateTime = order.getChildText("ReadyDateTime");
		
		aExternalMedservice.setNumberDoc(orderId);
		aExternalMedservice.setOrderDate(orderDate);
		aExternalMedservice.setOrderTime(orderTime);
		aExternalMedservice.setOrderer(doctor);
					
	}
	@SuppressWarnings("unchecked")
	private void parsePatient(Element aPatient,ExternalMedservice aExternalMedservice)
	{
		//String patientId = patient.getAttributeValue("PatientID");
		if (aPatient != null) {
	        String lastname = upperCase(aPatient.getChildText("LastName"));
	        String fmn = upperCase(aPatient.getChildText("FirstMiddleName")) ;
	        String syncPat = upperCase(aPatient.getChildText("InsuranceNumber")) ;
	        String[] firstMiddleName = fmn.split(" ");
	        String firstname = firstMiddleName.length < 1 ?"":firstMiddleName[0];
	        String middlename = firstMiddleName.length < 2 ?"":firstMiddleName[1];
	        Date birthday = toDate(aPatient.getChildText("DOB"));
        
	        //Long sex = ConvertSql.parseLong(patient.getChildText("Sex")=="M"?1:2);
	        
	        String patientSql = "FROM Patient WHERE lastname=:lastname and firstname=:firstname and middlename=:middlename and birthday=:birthday" ;
	        List<Patient> list = null ;
	        if (StringUtil.isNullOrEmpty(lastname)) lastname = "Х" ;
	        if (StringUtil.isNullOrEmpty(firstname)) firstname = "Х" ;
	        if (StringUtil.isNullOrEmpty(middlename)) middlename = "Х" ;
	        if ((birthday!=null) && (manager != null)) {
	        	list = manager.createQuery(patientSql)
	        		.setParameter("lastname", lastname)
	        		.setParameter("firstname", firstname)
	        		.setParameter("middlename", middlename)
	        		.setParameter("birthday", birthday)
	        		.setMaxResults(2).getResultList();
			}
	        Patient patientId = (list!=null && list.size()==1)?list.get(0):null;
	        aExternalMedservice.setPatientSync(syncPat) ;
	        aExternalMedservice.setPatient(patientId);
	        aExternalMedservice.setPatientLastname(lastname);
	        aExternalMedservice.setPatientFirstname(firstname);
	        aExternalMedservice.setPatientMiddlename(middlename);
	        aExternalMedservice.setPatientBirthday(birthday);
		}
        aExternalMedservice.setReferenceTo(fileUri);
        
	}
	@SuppressWarnings("unchecked")
	private void parsePages(Element pages) {
		for (Element page: (List<Element>) pages.getChildren("Page")) {
			parsePage(page);
		}
	}

	@SuppressWarnings("unchecked")
	private void parsePage(Element aPage) {
		for (Element section: (List<Element>) aPage.getChildren("Section")) {
			parseSection(section);
		}
	}
	public void parseSection(Element aSection){
		//String pageId = page.getAttributeValue("PageID");
		String sectionId = aSection.getAttributeValue("SectionID");
		String sectionName = aSection.getChildText("SectionName");
		VocDocumentParameterGroup group = documentParameterGroupsObj.get(sectionId) ;
		if (group == null) {
			
			@SuppressWarnings("unchecked")
			List<VocDocumentParameterGroup> list1 = manager==null? null: manager.createQuery("FROM VocDocumentParameterGroup WHERE code='"+sectionId+"'").getResultList() ;
			if (list1 != null && !list1.isEmpty()) {
				group =list1.get(list1.size()-1) ;
			} 
			if (group == null) {
				group = new VocDocumentParameterGroup() ;
				group.setCode(sectionId);
				group.setName(sectionName) ;
				persist(group) ;
				printVariable("GroupSave",sectionId+":"+sectionName);
			}
		}
		documentParameterGroups.put(sectionId, sectionName);
		documentParameterGroupsObj.put(sectionId, group);
	}
	
	@SuppressWarnings("unchecked")
	private void parseTests(Element tests,ExternalMedservice aExternalMedservice) {
		for (Element test: (List<Element>) tests.getChildren("Test")) 
		{
			parseTest(test,aExternalMedservice);
		}	
		
	}
	private void parseTest(Element test, ExternalMedservice aExternalMedservice) {
		
		//String testId = test.getAttributeValue("TestID");
		String value = upperCase(test.getChildText("Value"));
		String normalValueMin = substring(test.getChildText("NormalValueMin"),255);
		String normalValueMax = substring(test.getChildText("NormalValueMax"),255);
		String normalValue = substring(test.getChildText("NormalValue"),255);
		Date valueDate = toDate(test.getChildText("ValueDate"));
		Time valueTime = toTime(test.getChildText("ValueTime"));
		String testName = substring(upperCase(test.getChildText("TestName")),255);
		String dimension = substring(upperCase(test.getChildText("Dimension")),255);
		String testShortName = substring(upperCase(test.getChildText("TestShortName")),255);
		
		Element testPosition = test.getChild("TestPosition");
		String sectionID = testPosition.getChildText("SectionID");
		int sectionItem = toInt(testPosition.getChildText("SectionItem"));
		
		VocDocumentParameterGroup vocDocumentParameterGroup = documentParameterGroupsObj.get(sectionID);
		if (vocDocumentParameterGroup==null) printVariable("NullGroup",sectionID);
		
		VocDocumentParameter vocDocumentParameter = documentParameterObj.get(testShortName) ;
		if (vocDocumentParameter==null) {
			@SuppressWarnings("unchecked")
			List<VocDocumentParameter> list = manager == null? null : manager.createQuery("FROM VocDocumentParameter WHERE code='"+testShortName+"'").getResultList();
			if (list != null && !list.isEmpty()) {
				vocDocumentParameter = list.get(0) ;
			} else {
				vocDocumentParameter = new VocDocumentParameter();
				vocDocumentParameter.setCode(testShortName);
				vocDocumentParameter.setName(testName);
				vocDocumentParameter.setNormMinimum(normalValueMin);
				vocDocumentParameter.setNormMaximum(normalValueMax);
				vocDocumentParameter.setNorm(normalValue);
				vocDocumentParameter.setDimension(dimension);
				vocDocumentParameter.setParameterGroup(vocDocumentParameterGroup);
				persist(vocDocumentParameter) ;
			}
			documentParameterObj.put(testShortName, vocDocumentParameter) ;
		}
		
		DocumentParameter documentParameter = new DocumentParameter();
		documentParameter.setDocument(aExternalMedservice);
		documentParameter.setValue(value);
		documentParameter.setValueDate(valueDate);
		documentParameter.setValueTime(valueTime);
		documentParameter.setOrderNumber(sectionItem);
		documentParameter.setParameter(vocDocumentParameter);
		
		persist(documentParameter);
		
		if (documentParametersTree.get(sectionID)==null){
			documentParametersTree.put(sectionID, new TreeMap<>());
		}
		
		documentParametersTree.get(sectionID).put(toString(100+sectionItem).substring(1), documentParameter);
	}
	public void prepareComment(ExternalMedservice aExternalMedservice){
		//prepareCommentHead(aExternalMedservice);
		prepareTestsComment(aExternalMedservice);
	}
	public void prepareCommentHead(ExternalMedservice aExternalMedservice){	
		commentAdd("Лаборатория",aExternalMedservice.getWhomIssued());
		commentNewLine();
		commentAdd("Фамилия",aExternalMedservice.getPatientLastname());
		commentAdd("Имя",aExternalMedservice.getPatientFirstname());
		commentAdd("Отчество",aExternalMedservice.getPatientMiddlename());
		commentAdd("Дата рождения",toString(aExternalMedservice.getPatientBirthday()));		
		commentNewLine();
		commentAdd("ЛПУ",aExternalMedservice.getOrderLpu());
		commentAdd("Врач",aExternalMedservice.getOrderer());
		commentNewLine();		
		commentAdd("Номер направления",aExternalMedservice.getNumberDoc());
		commentAdd("Дата направления",toString(aExternalMedservice.getOrderDate()));
		commentAdd("Время направления",toString(aExternalMedservice.getOrderTime()));		
		commentNewLine();	
		commentAdd("Дата получения результата",toString(aExternalMedservice.getCreateDate()));
		commentAdd("Время получения результата",toString(aExternalMedservice.getCreateTime()));
		commentNewLine();
	}
	public void prepareTestsComment(ExternalMedservice aExternalMedservice){
		Set<Entry<String, TreeMap<String, Object>>> parametersTree = documentParametersTree.entrySet();
		Entry<String, TreeMap<String, Object>> tm;
		Entry<String, Object> pm;
		String groupKey;
		String norm;
		
		Iterator<Entry<String, TreeMap<String, Object>>> ti = parametersTree.iterator();		
		int i=0;
		while(ti.hasNext()) {
			i=i+1;
			tm = ti.next();
			groupKey=tm.getKey();
			commentAdd(documentParameterGroups.get(groupKey));
			commentNewLine();
			Set<Entry<String, Object>> parametersSet = tm.getValue().entrySet();
			Iterator<Entry<String, Object>> pi = parametersSet.iterator();
			int i1=0;
			while(pi.hasNext()) {
				i1=i1+1;
				pm = pi.next();
				DocumentParameter documentParameter = (DocumentParameter) pm.getValue();
				VocDocumentParameter vocDocumentParameter =  documentParameter.getParameter();
				norm="";
				if (vocDocumentParameter.getNormMinimum()!=null){
					norm= vocDocumentParameter.getNormMinimum() + "-" + vocDocumentParameter.getNormMaximum();
					norm = substring(norm, 255);
				}
				else  if (vocDocumentParameter.getNorm()!=null){
					norm=vocDocumentParameter.getNorm();
				}
				
				commentAdd(vocDocumentParameter.getName());
				commentAdd(": "+documentParameter.getValue());
				commentAdd(" "+vocDocumentParameter.getDimension());
				if (!norm.equals("")) commentAdd(" ("+norm+") ");
				commentNewLine();
			}
		}
		aExternalMedservice.setComment(comment.toString());
	} 
	public static Time toTime(String aString){
		try {
			return aString == null ? null:DateFormat.parseSqlTime(aString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String toString(Date aDate){
		return aDate == null ? "" : DateFormat.formatToDate(aDate);
	}
	public static String toString(Time aTime){
		return aTime == null ? "" : DateFormat.formatToTime(aTime);
	}
	public static String toString(int aInt){
		return String.valueOf(aInt);
	}
	public static String substring(String aString, int aLength){
		if (aString!=null && aString.length()>aLength) return aString.trim().substring(0, aLength - 4) + "...";
		return aString==null?"":aString;
	}
	public static String upperCase(String aString){
		if (aString!=null) return aString.trim().toUpperCase() ;
		return "";
	}
	public static String toString(long aLong){
		return Long.toString(aLong);
	}
	public static int toInt(String aString){
		return aString == null ? 0: Integer.parseInt(aString);
	}
	public static Date toDate(String aString){
		try {
			return aString == null ? null: toInt(aString.substring(0,4)) < 1850? null:DateFormat.parseSqlDate(aString, "yyyy-MM-dd");
		} catch (ParseException e) {
			//e.printStackTrace();
			return null;
		}
	}		
	public void commentNewLine(){
		comment.append("\n");
	}
	public void commentAdd(String arg0){
		comment.append(arg0);
	}
	public void commentAdd(String arg0, String arg1){
		comment.append(arg0).append(": ").append(arg1).append(" ");
	}
	public void printVariable(String variable, String value) {
		printVariable(variable, value, "");
	}
	public static void printVariable(String variable, String value, String type )
	{
		try {
			String message ;
			message = variable+": "+(value==null?"":value);
			if (type.equals("D")) {
				message = message + " ("+(DateFormat.parseDate(value, "yyyy-MM-dd"))+")";
			}
			if (type.equals("T")) {
				message = message + " ("+(DateFormat.parseSqlTime(value)+")");
			}

			LOG.info(message);
		} catch (Exception e) {}
	}
	private void persist(Object object){
		if (object!=null && manager!=null) {
			manager.persist(object);
			
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static String getIdColumn(Class aClass){
		String ret = "id";
		if (aClass!=null){
			@SuppressWarnings("unchecked")
			AttributeOverride attributeOverride = (AttributeOverride) aClass.getAnnotation(AttributeOverride.class);
			if (attributeOverride!=null){
				Column column = attributeOverride.column();
				ret=column.name();
			}
		}
		return ret;
	}
	public String getKdlDir() {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		return config.get("kdl.dir", "/opt/kdl/test");
	}
	public String getKdlArcDir() {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		return config.get("kdl.arcdir", "/opt/kdl/testArc");
	}
	public String getKdlErrorDir() {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		return config.get("kdl.errordir", "/opt/kdl/testError");
	}
	public void setPermissions(File aFile){
		run("chmod -R 777 "+aFile.getAbsolutePath());
	}
	public String run(String command){
		try{
		Runtime.getRuntime().exec(command);
		return ("0");
		}
		catch (Exception e){
		LOG.error("Error running command: " + command +"" + e.getMessage(),e);
		return(e.getMessage());
		}
	} 
	
	@PersistenceContext EntityManager manager ;

    String fileUri;
    StringBuilder comment;
    TreeMap<String, String> documentParameterGroups;
    HashMap<String, VocDocumentParameter> documentParameterObj ;
	HashMap<String, VocDocumentParameterGroup> documentParameterGroupsObj;
	TreeMap<String, TreeMap<String, Object>> documentParametersTree ;
}
