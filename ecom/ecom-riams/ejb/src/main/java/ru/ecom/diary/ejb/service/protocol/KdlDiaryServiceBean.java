package ru.ecom.diary.ejb.service.protocol;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import org.json.JSONException;
import org.xml.sax.helpers.DefaultHandler;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.domain.licence.DocumentParameter;
import ru.ecom.mis.ejb.domain.licence.ExternalMedservice;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameter;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameterGroup;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.service.prescription.PrescriptionServiceBean;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;

import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import java.io.*;
import java.util.*;
import com.itextpdf.text.pdf.parser.RenderFilter;

@Stateless
@Remote(IKdlDiaryService.class)
public class KdlDiaryServiceBean extends DefaultHandler implements IKdlDiaryService {
	
	public ParsedPdfInfo getPdfInfoByBarcode( List<ParsedPdfInfo> list,String aBarcode) {
		if (list!=null&&!list.isEmpty()) {
			for (ParsedPdfInfo p: list) {
				if (p.getBarcode().equals(aBarcode)) {
					return p;
				}
			}
		}
		return new ParsedPdfInfo();
	}
	
	public void checkPdf() throws IOException, NoSuchFieldException, IllegalAccessException, JSONException {
		System.out.println("==== Запускаем функцию проверки наличия PDF ====");
		//**Перечень директорий*//*
		String homeDirectory  =  getDir("jboss.labPdfDocumentDir","/opt/tomcat"); //= "C:\\Users\\vtsybulin\\workspace\\pdfParser\\pdf";
        //String homeDirectory = "/home/user/opt/tomcat";
		String pdfDirectory = homeDirectory + "/parse_pdf/";
        String txtDirectory = homeDirectory + "/parse_txt/";
        String archDirectory = homeDirectory + "/parse_archive/";
        System.out.println("Ищу файлы в папке " + pdfDirectory);
        /**Сперва должны получить список всех файлов в формате pdf*/
        File[] fileList = getFiles(pdfDirectory);
        
        if (fileList!=null&&fileList.length>0){
        	System.out.println("В массиве имеются файлы!");
	
	
        	
        
        for (int i = 0; i < fileList.length; i++){
        	List<ParsedPdfInfo> resultList = new ArrayList<ParsedPdfInfo>();
        	//resultList = new ArrayList<ParsedPdfInfo>();
         //   ParsedPdfInfo result = new ParsedPdfInfo();
        	File file = fileList[i];        	
        	String fileName = file.getName();        
            file.getParentFile().mkdirs();           
            String[] temp_container = fileName.split("\\.");
            Character t1 = 'g';
            if (t1.equals(temp_container[0].substring(temp_container[0].length() - 1))) {
                System.out.println("+true");
            } else {
                System.out.println("+false");
            }
            System.out.println("last character: " +
                    temp_container[0].substring(temp_container[0].length() - 1));
            String typeFile = "100014";	
            if (typeFile.equals("100014")) {
//            	TODO
//            	 * Находим штрих-код. 
//            	 
            	String barCode = ""; 
            	barCode = getBarCode(pdfDirectory+fileName);
            	ParsedPdfInfo ppi = getPdfInfoByBarcode(resultList, barCode); //Создаем или находим объект, хранящий все анализы по одному штрих-коду
            	List <ParsedPdfInfoResult> res = new ArrayList<ParsedPdfInfoResult>();
            	try{
            		String[] paramName = null;
            		paramName = fillColumn(paramName, 30, 560, 100, 770, pdfDirectory + fileList[i].getName(), txtDirectory + (String) fileList[i].getName().substring(0, fileList[i].getName().length() - 4) + ".txt");
            		String[] resultValue = null;
            		resultValue = fillColumn(paramName, 190, 560, 210, 770, pdfDirectory + fileList[i].getName(), txtDirectory + (String) fileList[i].getName().substring(0, fileList[i].getName().length() - 4) + ".txt");
            		String[] measureUnit = null;
            		measureUnit = fillColumn(paramName, 215, 560, 250, 770, pdfDirectory + fileList[i].getName(), txtDirectory + (String) fileList[i].getName().substring(0, fileList[i].getName().length() - 4) + ".txt");
            		String[] nomRange = null; 
            		nomRange = fillColumn(paramName, 255, 560, 340, 770, pdfDirectory + fileList[i].getName(), txtDirectory + (String) fileList[i].getName().substring(0, fileList[i].getName().length() - 4) + ".txt");
                    fileName = fileList[i].getName().substring(0, fileList[i].getName().length() - 4);
                    for (int j = 0; j < paramName.length; j++) {
                            ParsedPdfInfoResult ppir = new ParsedPdfInfoResult();
                            ppir.setCode(chk(j, paramName));
                            ppir.setValue(chk(j, resultValue));
                            ppir.setMeasurementUnit(chk(j, measureUnit));
                            ppir.setRefInterval(chk(j, nomRange));
                            res.add(ppir);
                    }
            	} catch(Exception e){
            		e.printStackTrace();
                    	System.out.println("Исключение в цикле while");
                    	}
                moveFile(pdfDirectory, archDirectory, fileName + ".pdf");
                ppi.setBarcode(barCode);
                ppi.setResults(res);
           resultList.add(ppi);
                	}            
            System.out.println("Выводим строку №7");
            System.out.println(resultList.get(6).getResults().get(0));
            System.out.println(resultList.get(6).getResults().get(1));
            System.out.println(resultList.get(6).getResults().get(2));
            System.out.println(resultList.get(6).getResults().get(3));
            System.out.println("Запускаем функцию по заполнению дневника");
            PrescriptionServiceBean service = new PrescriptionServiceBean();
            service.setDefaultDiaryCycle(resultList);
            	}
        }       
        else{
        	System.out.println("В массиве нет файлов!");
        }
}
	public static String getBarCode(String pdf) throws IOException {
        String barcode = "";
        PdfReader reader = new PdfReader(pdf);
        StringBuilder text = new StringBuilder();
        Rectangle rect = new Rectangle(0, 0, 1000, 1000);
        RenderFilter filter = new RegionTextRenderFilter(rect);
        TextExtractionStrategy strategy;
        for (int page = 1; page <= reader.getNumberOfPages(); page++) {
            strategy = new FilteredTextRenderListener(
                    new LocationTextExtractionStrategy(), filter);
            String currentText = PdfTextExtractor.getTextFromPage(reader, page, strategy);
            barcode = "Код пробы: ";
            int position = currentText.indexOf(barcode);
            barcode = currentText.substring(position+11, 172);
            System.out.println(barcode);
        }
        reader.close();
        return  barcode;
    }
	public static String chk(int i, String[] arr) {
		if (arr.length>(i)) {
			return arr[i];
		} else {
			return "";
		}
	}
	public static String[] fillColumn(String[] cont, int x1, int x2, int y1, int y2, String pdf, String txt) throws IOException {
        PdfReader reader = new PdfReader(pdf);
        PrintWriter out = new PrintWriter(new FileOutputStream(txt));
        Rectangle rect = new Rectangle(x1, x2, y1, y2);
        RenderFilter filter = new RegionTextRenderFilter(rect);
        TextExtractionStrategy strategy;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            strategy = new FilteredTextRenderListener(
                    new LocationTextExtractionStrategy(), filter);
            String temp = PdfTextExtractor.getTextFromPage(reader, i, strategy);
            out.println(temp);
            cont = temp.split("\n");
        }
        out.flush();
        out.close();
        reader.close();
        return cont;
    }
	public static String[] concat(String[] a, String[] b) {
        int aLen = a.length;
        int bLen = b.length;
        String[] c = new String[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
	
	/**Перемещение файла из одной директории в другую*/
    public static void moveFile(String pdfDirectory, String archDirectory, String fileName) {
        try {
            final File myFile = new File(pdfDirectory + fileName );
            if (myFile.renameTo(new File(archDirectory + fileName))) {
                System.out.println("Файл "+ fileName + " успешно перенесен из директории " + pdfDirectory + " в директорию " + archDirectory);
            }
            else{
                 System.out.println("Файл не был перенесен!");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
	
	/** Вывод всех файлов в папке */
   public static File[] getFiles(String path) {
    	try{
        File dir = new File(path);
        System.out.println(dir.exists());
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".pdf");
            }});
        
        return files;
    	}
    	catch(Exception e)
    	{
    		System.out.println("Директория не обнаружена. Проверьте правильность.");
    		e.printStackTrace();
    		return null;
    	}
       
    }
	
	public static boolean checkIsExist(String filePath, boolean resultExist)    {
        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()) {
            System.out.println("Файл существует.");
            resultExist = true;
        } else {
            System.out.println("Файл не существует.");
            resultExist = false;
        }
        return resultExist;
    }
	
	
	public String getDir(String aKey, String aDefaultValue) {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		return config.get(aKey, aDefaultValue) ;
	}
	public void parseFile(String aUri) throws Exception 
	{
		ExternalMedservice externalMedservice;
		@SuppressWarnings("unchecked")
		List<ExternalMedservice> list = theManager == null? null : theManager.createQuery("FROM Document WHERE dtype='ExternalMedservice' AND referenceTo='"+aUri+"'").getResultList();
		if (list == null? false : list.size()>0) {
			externalMedservice = list.get(0) ;			
		} else {
			externalMedservice = new ExternalMedservice();
		}
        theComment = new StringBuilder();
        theDocumentParameterGroups = new TreeMap<String, String>();
    	theDocumentParametersTree = new TreeMap<String, TreeMap<String, Object>>(); 
        theDocumentParameterObj = new HashMap<String, VocDocumentParameter>();
    	theDocumentParameterGroupsObj = new HashMap<String, VocDocumentParameterGroup>(); 	try{
    		printVariable("Start parse",aUri);
	    	File in = new File(aUri);
	        theFileUri = aUri;
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
    	}
    	catch(Exception e){
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
	        String[] firstMiddleName = fmn==null? null: fmn.split(" ") ;
	        String firstname = (firstMiddleName==null||firstMiddleName.length<1)?"":firstMiddleName[0];
	        String middlename = (firstMiddleName==null||firstMiddleName.length<2)?"":firstMiddleName[1];
	        Date birthday = toDate(aPatient.getChildText("DOB"));
        
	        //Long sex = ConvertSql.parseLong(patient.getChildText("Sex")=="M"?1:2);
	        
	        String patientSql = "FROM Patient WHERE lastname=:lastname and firstname=:firstname and middlename=:middlename and birthday=:birthday" ;
	        List<Patient> list = null ;
	        if (StringUtil.isNullOrEmpty(lastname)) lastname = "Х" ;
	        if (StringUtil.isNullOrEmpty(firstname)) firstname = "Х" ;
	        if (StringUtil.isNullOrEmpty(middlename)) middlename = "Х" ;
	        if ((birthday!=null) && (theManager != null)) {
	        	list = theManager.createQuery(patientSql)
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
        aExternalMedservice.setReferenceTo(theFileUri);
        
	}
	@SuppressWarnings("unchecked")
	private void parsePages(Element pages) 
	{
		for (Element page: (List<Element>) pages.getChildren("Page")) 
		{
			parsePage(page);
		}
	}

	@SuppressWarnings("unchecked")
	private void parsePage(Element aPage) 
	{
		for (Element section: (List<Element>) aPage.getChildren("Section")) 
		{
			parseSection(section);
		}
	}
	public void parseSection(Element aSection){
		//String pageId = page.getAttributeValue("PageID");
		String sectionId = aSection.getAttributeValue("SectionID");
		String sectionName = aSection.getChildText("SectionName");
		VocDocumentParameterGroup group = theDocumentParameterGroupsObj.get(sectionId) ;
		if (group == null) {
			
			@SuppressWarnings("unchecked")
			List<VocDocumentParameterGroup> list1 = theManager==null? null: theManager.createQuery("FROM VocDocumentParameterGroup WHERE code='"+sectionId+"'").getResultList() ;
			if (list1 == null? false: list1.size()>0) {
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
		theDocumentParameterGroups.put(sectionId, sectionName);
		if (group!=null) theDocumentParameterGroupsObj.put(sectionId, group);
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
		
		VocDocumentParameterGroup vocDocumentParameterGroup = theDocumentParameterGroupsObj.get(sectionID);
		if (vocDocumentParameterGroup==null) printVariable("NullGroup",sectionID);
		
		VocDocumentParameter vocDocumentParameter = theDocumentParameterObj.get(testShortName) ;
		if (vocDocumentParameter==null) {
			@SuppressWarnings("unchecked")
			List<VocDocumentParameter> list = theManager == null? null : theManager.createQuery("FROM VocDocumentParameter WHERE code='"+testShortName+"'").getResultList();
			if (list == null? false : list.size()>0) {
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
			theDocumentParameterObj.put(testShortName, vocDocumentParameter) ;
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
				norm="";
				if (vocDocumentParameter.getNormMinimum()!=null){
					norm=new StringBuilder().append(vocDocumentParameter.getNormMinimum()).append("-").append(vocDocumentParameter.getNormMaximum()).toString();
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
		aExternalMedservice.setComment(theComment.toString());
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
	public static String substring(String aString, int aLength){
		if (aString!=null && aString.length()>aLength) return new StringBuilder().append(aString.trim().substring(0, aLength-4)).append("...").toString() ;
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
			if (type.equals("D")) {
				message = message + " ("+((java.util.Date) DateFormat.parseDate(value, "yyyy-MM-dd"))+")";
			}
			if (type.equals("T")) {
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
	public String getKdlErrorDir() {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir =config.get("kdl.errordir", "/opt/kdl/testError");
		return workDir ;
	}
	public void setPermissions(File aFile){
		run("chmod -R 777 "+aFile.getAbsolutePath());
	}
	public String run(String Command){
		try{
		Runtime.getRuntime().exec(Command);
		return ("0");
		}
		catch (Exception e){
		System.out.println("Error running command: " + Command +
		"\n" + e.getMessage());
		return(e.getMessage());
		}
	} 
	
	@PersistenceContext EntityManager theManager ;

    String theFileUri;
    StringBuilder theComment;
    TreeMap<String, String> theDocumentParameterGroups;
    HashMap<String, VocDocumentParameter> theDocumentParameterObj ;
	HashMap<String, VocDocumentParameterGroup> theDocumentParameterGroupsObj;
	TreeMap<String, TreeMap<String, Object>> theDocumentParametersTree ;
}
