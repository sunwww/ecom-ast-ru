package ru.ecom.diary.ejb.service.protocol;

import java.io.File;
import java.text.ParseException;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.helpers.DefaultHandler;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.domain.licence.DocumentParameter;
import ru.ecom.mis.ejb.domain.licence.ExternalMedservice;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameter;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameterGroup;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.util.format.DateFormat;

@Stateless
@Remote(IKdlDiaryService.class)
public class KdlDiaryServiceBean extends DefaultHandler implements IKdlDiaryService {
	
	public static void main(String[] args) {
		//service.setManager() ;
		KdlDiaryServiceBean service = new KdlDiaryServiceBean() ;
		service.getFiles() ;
	}
	public void getFiles() {
		theDirName = getKdlDir();
		theArcDirName = getKdlArcDir();
		
		parseDir(theDirName, theArcDirName, true);
	}

	public void parseDir(String dirName, String arcDirName, Boolean aRootDir){
		File dir = new File(dirName);
		theWorkDirName = dir.getName();
		theWorkArcDir = new File(theArcDirName, theWorkDirName);
		setPermissions(dir);
		parseDir(dir, theWorkArcDir, aRootDir);
		}
	public void parseDir(File aDir, File aArcDir, Boolean aRootDir){
		printVariable("Dir", aDir.getAbsolutePath()) ;
		printVariable("ArcDir", aArcDir.getAbsolutePath()) ;
		printVariable("RootDir", aRootDir? "true": "false");
		if (!aRootDir){
			if (!aArcDir.exists()) {
				aArcDir.mkdir();
				setPermissions(aArcDir);
			}
		}

		File[] files=aDir.listFiles();
		if (files == null) {
		} else {
		    for (File file:files) {
		    	if(file.isDirectory()==false){
		    		printVariable("\nfile",file.getAbsolutePath()) ;
			    	parseFile(file.getAbsolutePath());
			    	File newFile = new File(aArcDir, file.getName());
			    	file.renameTo(newFile);
		    	} else {
		    			theWorkDirName = file.getName();
		    			theWorkArcDir = new File(theArcDirName, theWorkDirName);
		    			parseDir(file, theWorkArcDir, false);
		    		}
			    }
		}
	    if (!aRootDir)	aDir.delete();
	}
	public void parseFile(String uri)
	{
        //theManager.getTransaction().begin() ;
		ExternalMedservice externalMedservice = new ExternalMedservice();
        theComment = new StringBuilder();
    	theDocumentParameterGroups = new TreeMap<String, String>();
    	theVocDocumentParameters = new HashMap<Object, Object>();
    	theDocumentParametersTree = new TreeMap<String, TreeMap<String, Object>>(); 
	    
    	try
	    { 
	    	File in = new File(uri);
	        theFileUri = uri;
	        Document doc = new SAXBuilder().build(in);
	        Element root = doc.getRootElement();
	        Element header = root.getChild("Header");
	        parseHeader(header,externalMedservice);
	        Element order = root.getChild("Order");
	        parseOrder(order,externalMedservice);
	        Element patient = order.getChild("Patient") ;
	        parsePatient(patient,externalMedservice);
			Element pages = order.getChild("Pages");
			parsePages(pages);
			Element tests = order.getChild("Tests");
			parseTests(tests,externalMedservice);
			prepareComment(externalMedservice);

			//return theExternalMedservice;
	    }
	    catch (Exception e)
	    {
	    	//theManager.getTransaction().rollback() ;
	      System.err.println(e);
	      //return null;
	    }		
	    
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
		theManager.persist(aExternalMedservice) ;
		//persist(theExternalMedservice);
		
	}
	public void parseOrder(Element order,ExternalMedservice aExternalMedservice)
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
	public void parsePatient(Element patient,ExternalMedservice aExternalMedservice)
	{
		//String patientId = patient.getAttributeValue("PatientID");
        String lastname = patient.getChildText("LastName").toUpperCase();
        String[] firstMiddleName = patient.getChildText("FirstMiddleName").toUpperCase().split(" ") ;
        String firstname = firstMiddleName[0];
        String middlename = firstMiddleName[1];
        Date birthday = toDate(patient.getChildText("DOB"));
        //Long sex = ConvertSql.parseLong(patient.getChildText("Sex")=="M"?1:2);
        
        StringBuilder sb = new StringBuilder();
        sb.append("FROM Patient");
        sb.append(" WHERE lastname='").append(lastname).append("'");
        sb.append(" AND firstname='").append(firstname).append("'");
        sb.append(" AND middlename='").append(middlename).append("'");
        sb.append(" AND birthday='").append(birthday).append("'");
        Patient patientId = null;
        try{
        patientId = (Patient) theManager.createQuery(sb.toString()).getSingleResult();	        
        } catch(Exception e){}
        
        aExternalMedservice.setPatient(patientId);
        aExternalMedservice.setPatientLastname(lastname);
        aExternalMedservice.setPatientFirstname(firstname);
        aExternalMedservice.setPatientMiddlename(middlename);
        aExternalMedservice.setPatientBirthday(birthday);
        aExternalMedservice.setReferenceTo(theFileUri);
        persist(aExternalMedservice);
        
	}
	@SuppressWarnings("unchecked")
	private void parsePages(Element pages) 
	{
		for (Element page: (List<Element>) pages.getChildren("Page")) 
		{
			parsePage(page);
		}
	}
	public void parsePage(Element page) 
	{
		//String pageId = page.getAttributeValue("PageID");
		Element section = page.getChild("Section");
		String sectionId = section.getAttributeValue("SectionID");
		String sectionName = section.getChildText("SectionName");
		
		theDocumentParameterGroups.put(sectionId, sectionName);
/*		printVariable("sectionId",sectionId);
		printVariable("sectionName",sectionName);
		printVariable("theSectionId",theDocumentParameterGroups.get(sectionId));*/
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
		String value = test.getChildText("Value").toUpperCase();
		String normalValueMin = test.getChildText("NormalValueMin");
		String normalValueMax = test.getChildText("NormalValueMax");
		String normalValue = test.getChildText("NormalValue");
		Date valueDate = toDate(test.getChildText("ValueDate"));
		Time valueTime = toTime(test.getChildText("ValueTime"));
		String testName = test.getChildText("TestName").toUpperCase();
		String dimension = test.getChildText("Dimension").toUpperCase();
		String testShortName = test.getChildText("TestShortName").toUpperCase();
		
		Element testPosition = test.getChild("TestPosition");
		String sectionID = testPosition.getChildText("SectionID");
		int sectionItem = toInt(testPosition.getChildText("SectionItem"));
		
		VocDocumentParameterGroup vocDocumentParameterGroup = null ;
		try {
		vocDocumentParameterGroup = (VocDocumentParameterGroup) theManager.createQuery("FROM VocDocumentParameterGroup WHERE code=:code").setParameter("code", sectionID).getSingleResult();
		} 
		catch (Exception e) {}
		if (vocDocumentParameterGroup == null) {
			vocDocumentParameterGroup = (VocDocumentParameterGroup) new VocDocumentParameterGroup() ;
			vocDocumentParameterGroup.setCode(sectionID);
			vocDocumentParameterGroup.setName((String) theDocumentParameterGroups.get(sectionID).toUpperCase());
			persist(vocDocumentParameterGroup);
		}		
		
		VocDocumentParameter vocDocumentParameter = null ;
		try {
		vocDocumentParameter = (VocDocumentParameter) theManager.createQuery("FROM VocDocumentParameter WHERE code=:code").setParameter("code", testShortName).getSingleResult();
		} 
		catch (Exception e) {}
		if (vocDocumentParameter==null) {
			
			vocDocumentParameter = (VocDocumentParameter) new VocDocumentParameter();
			
			vocDocumentParameter.setCode(testShortName);
			vocDocumentParameter.setName(testName);
			vocDocumentParameter.setNormMinimum(normalValueMin);
			vocDocumentParameter.setNormMaximum(normalValueMax);
			vocDocumentParameter.setNorm(normalValue);
			vocDocumentParameter.setDimension(dimension);
			vocDocumentParameter.setParameterGroup(vocDocumentParameterGroup);
			persist(vocDocumentParameter);
			
			theVocDocumentParameters.put(vocDocumentParameter, vocDocumentParameter);
		}
			
		DocumentParameter documentParameter = new DocumentParameter();
		documentParameter.setDocument(aExternalMedservice);
		documentParameter.setValue(value);
		documentParameter.setValueDate(valueDate);
		documentParameter.setValueTime(valueTime);
		documentParameter.setOrderNumber(sectionItem);
		documentParameter.setParameter(vocDocumentParameter);
		persist(documentParameter);
		
		if (theDocumentParametersTree.get(sectionID)==null){
			theDocumentParametersTree.put(sectionID, new TreeMap<String, Object>());
		}
		theDocumentParametersTree.get(sectionID).put(toString(100+sectionItem).substring(1), documentParameter);
	}
	public void prepareComment(ExternalMedservice aExternalMedservice){
		prepareCommentHead(aExternalMedservice);
		prepareTestsComment(aExternalMedservice);
		//print(theComment.toString());
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
		Set<Entry<String, TreeMap<String, Object>>> parametersTree = theDocumentParametersTree.entrySet();
		Entry<String, TreeMap<String, Object>> tm;
		Entry<String, Object> pm;
		String groupKey;
		String norm="";
		
		Iterator<Entry<String, TreeMap<String, Object>>> ti = parametersTree.iterator();		
		int i=0;
		while(ti.hasNext()) {
			i=i+1;
			tm = ti.next();
			groupKey=tm.getKey();
			commentAdd(theDocumentParameterGroups.get(groupKey));
			commentNewLine();
			Set<Entry<String, Object>> parametersSet = tm.getValue().entrySet();
			Iterator<Entry<String, Object>> pi = parametersSet.iterator();
			int i1=0;
			while(pi.hasNext()) {
				i1=i1+1;
				pm = pi.next();
				DocumentParameter documentParameter = (DocumentParameter) pm.getValue();
				VocDocumentParameter vocDocumentParameter = (VocDocumentParameter) documentParameter.getParameter();
				//printVariable("testId",documentParameter.GetTestId);
				norm="";
				if (vocDocumentParameter.getNormMinimum()!=null){
					norm=vocDocumentParameter.getNormMinimum()+"-"+vocDocumentParameter.getNormMaximum();
				}
				else  if (vocDocumentParameter.getNorm()!=null){
					norm=vocDocumentParameter.getNorm();
				}
				commentAdd(vocDocumentParameter.getName());
				commentAdd(": "+documentParameter.getValue());
				commentAdd(" "+vocDocumentParameter.getDimension());
				if (norm!="") commentAdd(" ("+norm+") ");
				commentNewLine();
				//commentAdd("valueDate",toString(documentParameter.getValueDate()));
				//commentAdd("valueTime",toString(documentParameter.getValueTime()));
				//commentAdd("sectionItem",toString(documentParameter.getOrderNumber()));
				//commentAdd("testShortName",vocDocumentParameter.getCode());
				//printVariable("sectionID",vocDocumentParameterGroup.getCode());
			}
		}
		aExternalMedservice.setComment(theComment.toString());
		persist(aExternalMedservice);
	} 
	public static Time toTime(String aString){
		try {
			return aString == null ? null:(Time) DateFormat.parseSqlTime(aString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String toString(Date aDate){
		return aDate == null ? "":(String) DateFormat.formatToDate(aDate);		
	}
	public static String toString(Time aTime){
		return aTime == null ? "":(String) DateFormat.formatToTime(aTime);		
	}
	public static String toString(int aInt){
		return String.valueOf(aInt);
	}
	public static String toString(long aLong){
		return Long.toString(aLong);
	}
	public static int toInt(String aString){
		return aString == null ? 0: Integer.parseInt(aString);
	}
	public static Date toDate(String aString){
		try {
			return aString == null ? null: DateFormat.parseSqlDate(aString, "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}		
	public void commentNewLine(){
		theComment.append("\n");
	}
	public void commentAdd(String arg0){
		theComment.append(arg0);
	}
	public void commentAdd(String arg0, String arg1){
		theComment.append(arg0).append(": ").append(arg1).append(" ");
	}
	public void printVariable(String variable, String value) {
		printVariable(variable, value, "");
	}
	public static void printVariable(String variable, String value, String type )
	{
		try {
			String message ;
			message = variable+": "+(value==null?"":value);
			if (type == "D") {
				message = message + " ("+((java.util.Date) DateFormat.parseDate(value, "yyyy-MM-dd"))+")";
			}
			if (type == "T") {
				message = message + " ("+((java.sql.Time) DateFormat.parseSqlTime(value)+")");
			}

			println(message);
		} catch (Exception e) {}
	}
	public static void print(String aString){
		System.out.print(aString);
	}		
	public static void println(String aString){
		System.out.println(aString);
	}		
	private void persist(Object object){

			if ((object!=null)&&(theManager!=null)) {
				theManager.persist(object);
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
				if (column!=null) ret=column.name();
			}
		}
		return ret;
	}
	public String getKdlDir() {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir =config.get("kdl.dir", "/opt/kdl/test");
		return workDir ;
	}
	public String getKdlArcDir() {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir =config.get("kdl.arcdir", "/opt/kdl/testArc");
		return workDir ;
	}
	public void setPermissions(File aFile){
		run("chmod -R 777 "+aFile.getAbsolutePath());
	}
	public String run(String Command){
		try{
		Runtime.getRuntime().exec(Command);
		return("0");
		}
		catch (Exception e){
		System.out.println("Error running command: " + Command +
		"\n" + e.getMessage());
		return(e.getMessage());
		}
	} 
	
	@PersistenceContext EntityManager theManager ;
    //static KdlDiaryServiceBean theService = new KdlDiaryServiceBean();
    String theDirName;
    String theArcDirName;
    String theWorkDirName;
    File theWorkArcDir;
    String theFileUri;
    //ExternalMedservice theExternalMedservice;
    StringBuilder theComment;
	TreeMap<String, String> theDocumentParameterGroups;
	TreeMap<String, TreeMap<String, Object>> theDocumentParametersTree ;
	HashMap<Object, Object> theVocDocumentParameters;
}
