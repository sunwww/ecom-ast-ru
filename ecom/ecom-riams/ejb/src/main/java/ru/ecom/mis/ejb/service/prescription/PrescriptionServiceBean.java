package ru.ecom.mis.ejb.service.prescription;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.diary.ejb.domain.protocol.parameter.FormInputProtocol;
import ru.ecom.diary.ejb.domain.protocol.parameter.Parameter;
import ru.ecom.diary.ejb.domain.protocol.parameter.user.UserValue;
import ru.ecom.diary.ejb.service.protocol.ParsedPdfInfo;
import ru.ecom.diary.ejb.service.protocol.ParsedPdfInfoResult;
import ru.ecom.ejb.sequence.service.SequenceHelper;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.medcase.ServiceMedCase;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType;
import ru.ecom.mis.ejb.domain.prescription.AbstractPrescriptionList;
import ru.ecom.mis.ejb.domain.prescription.DietPrescription;
import ru.ecom.mis.ejb.domain.prescription.DrugPrescription;
import ru.ecom.mis.ejb.domain.prescription.ModePrescription;
import ru.ecom.mis.ejb.domain.prescription.PrescriptList;
import ru.ecom.mis.ejb.domain.prescription.PrescriptListTemplate;
import ru.ecom.mis.ejb.domain.prescription.Prescription;
import ru.ecom.mis.ejb.domain.prescription.ServicePrescription;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.service.worker.WorkerServiceBean;
import ru.ecom.poly.ejb.domain.protocol.Protocol;
import ru.ecom.poly.ejb.domain.protocol.RoughDraft;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;


@Stateless
@Remote(IPrescriptionService.class)
public class PrescriptionServiceBean implements IPrescriptionService {
	
	public void sout(Object o) {sout(0,o);}
	
	public void sout(int debug, Object o) {
		if (debug==1) {
			System.out.println("===== Лаборатория: "+o.toString());
		} else {
			System.out.println(o.toString());
		}
	}
	
	
	public void checkXmlFiles() throws JSONException, ParserConfigurationException, SAXException, IOException
	{
		sout(1,"==== Начинаю поиск ====");
		
		String homeDirectory  =  getDir("jboss.lab.xmldir","/opt/lab/");
		String xmlDirectory = homeDirectory + "/xml/";
        String xmlArchDirectory = homeDirectory + "/archive/";
        
        sout(1,"Ищу файлы в папке :" + xmlDirectory);
        
        File[] fileList = getFiles(xmlDirectory);
        
        if (fileList!=null&&fileList.length>0)
	    {
        	sout(1,"Найдено "+fileList.length+" файлов");
        	
        	for (int i = 0; i < fileList.length; i++)
        	{
        		File file = fileList[i];        	
	        	String fileName = file.getName(); 
	        	
	        	String[] expansions = fileName.split("\\.");
	        
	        	if(expansions[1].equals("xml"))
	        	{
	        		sout(1,"Файл "+fileName+" - верный формат" );
	        		List<ParsedPdfInfo> l = ReadXML(xmlDirectory+fileName);
	        		if (!l.isEmpty()) {
	        			ParsedPdfInfo p = l.get(0);
	        			sout(1,"baracode: "+p.getBarcode());
		        		sout(1,"Code: "+(p.getResults()!=null&&!p.getResults().isEmpty()?p.getResults().get(0).getCode():"Нет кода"));
		        		sout(1,"Value: "+(p.getResults()!=null&&!p.getResults().isEmpty()?p.getResults().get(0).getValue():"Нет значения"));
	        		}
	        		
	        		
	        		setDefaultDiaryCycle(l);
	        		moveFile(xmlDirectory,xmlArchDirectory,fileName);
	        	}
        	}
	    } else {
	    	sout(1,"Файлов не найдено, заканчиваем...");
	    }
	}
	
	public static List<ParsedPdfInfo> ReadXML(String namefile) throws ParserConfigurationException, SAXException, IOException
	{
		ParsedPdfInfo parsedPdfInfo = new ParsedPdfInfo();
		List<ParsedPdfInfoResult> parsedPdfInfoResults = new ArrayList<ParsedPdfInfoResult>();
        List<ParsedPdfInfo>parsedPdfInfos = new ArrayList<ParsedPdfInfo>();
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        DocumentBuilder builder = null;
        
        
            builder = f.newDocumentBuilder();
            Document doc = builder.parse(new File(namefile));
            NodeList nodeList = doc.getElementsByTagName("Message");
        
            for (int i = 0; i < nodeList.getLength(); i++) {
            	 parsedPdfInfo = new ParsedPdfInfo();

                 Node node = nodeList.item(i);
                 if (Node.ELEMENT_NODE == node.getNodeType()) {
                     Element element = (Element) node;

                     parsedPdfInfo.setBarcode(element.getElementsByTagName("Barcode").item(i).getTextContent());

                     for (int j = 0; j < element.getElementsByTagName("Name").getLength(); j++) {

                         ParsedPdfInfoResult parsedPdfInfoResult = new ParsedPdfInfoResult();
                         parsedPdfInfoResult.setCode(element.getElementsByTagName("Name").item(j).getTextContent());
                         parsedPdfInfoResult.setValue(element.getElementsByTagName("Result").item(j).getTextContent());
                         parsedPdfInfoResults.add(parsedPdfInfoResult);
                     }
                     parsedPdfInfo.setResults(parsedPdfInfoResults);
                 }
                 parsedPdfInfos.add(parsedPdfInfo);
            }
            
            return parsedPdfInfos;
	}
	
	public String getDir(String aKey, String aDefaultValue) {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		return config.get(aKey, aDefaultValue) ;
	}
	
	/** Вывод всех файлов в папке */
	    public static File[] getFiles(String path) {
	     	try{
	         File dir = new File(path);
	         System.out.println(dir.exists());
	         File[] files = dir.listFiles(new FilenameFilter() {
	             public boolean accept(File dir, String name) {
	                 return name.toLowerCase().endsWith(".xml");
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
	
	    
	


	//endregion
	
	
	//region "Robot"
	public String setDefaultDiaryCycle(List<ParsedPdfInfo> parsedPdfInfos) throws JSONException
	{
		sout(1,"Start setDefaultDiaryCycle");
		sout(1,"РАЗМЕР ЛИСТА"+parsedPdfInfos.size());
		sout(1,"barcode= "+parsedPdfInfos.get(0).getBarcode());
		sout(1,"код= "+parsedPdfInfos.get(0).getResults().get(0).getCode());
		
		for (int i = 0; i < parsedPdfInfos.size(); i++) {
			
			ParsedPdfInfo parsedPdfInfo = parsedPdfInfos.get(i);
		    setDefaultDiary(parsedPdfInfo);
		}
		sout(1,"Finish setDefaultDiaryCycle");
		return "0";
	}
	
	public String setDefaultDiary(ParsedPdfInfo parsedPdfInfo) throws JSONException	{
		sout(1,"Start setDefaultDiary");
		//ParsedPdfInfo parsedPdfInfo = doObject();
		if (parsedPdfInfo!=null&&parsedPdfInfo.getBarcode()!=null&&!parsedPdfInfo.getBarcode().trim().equals("")) {
		
		StringBuilder sb = new StringBuilder() ;
		StringBuilder err = new StringBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		
		
		sout(1,"Количество элементов в parsedPdfInfo.getResults= "+parsedPdfInfo.getResults().size());
		String SqlAdd="";
		for(int i=0;i<parsedPdfInfo.getResults().size();i++) {
			SqlAdd += "'"+parsedPdfInfo.getResults().get(i).getCode()+"'";
			if((i+1)<parsedPdfInfo.getResults().size()){
				SqlAdd+=",";
			}
		}
		
		sout(1,"Начинаю 1 запрос");
		sql.append("select pres.id as pid, ms.id as msid, max(tp.id) as templateId "+
					"from prescription pres "+
					"left join medservice ms on ms.id=pres.medservice_id "+
					"left join templateprotocol tp on tp.medservice_id=ms.id "+
					"left join parameterbyform pf on pf.template_id = tp.id "+
					"left join parameter p on p.id=pf.parameter_id "+
		        	"where pres.barcodeNumber ='"+parsedPdfInfo.getBarcode()+"' "+
					"and p.externalcode in("+SqlAdd+") "+
					"group by pres.id,ms.id");
		
		 sout(1,sql);

		
		Collection<WebQueryResult> list = executeNativeSql(sql.toString(), theManager);
		//System.out.println(list.size());
		sout(1,"запрос 1 окончен, размер = "+list.size());
		
		if (!list.isEmpty()) {
			sout(1,"List is not empty");
			String username = "LabRobot";
			
	
			for (WebQueryResult res: list) {
				try {
					sout(1," setDefaultDiary incycle");
					Long pid = Long.parseLong(res.get1().toString());
				//	String msid = res.get2().toString();
					Long templateId = Long.parseLong(res.get3().toString());
					sql = new StringBuilder() ;
					sql.append("select p.id as p1id,p.name as p2name"+
					", p.shortname as p3shortname,p.type as p4type"+
					", p.minimum as p5minimum, p.normminimum as p6normminimum"+
					", p.maximum as p7maximum, p.normmaximum as p8normmaximum"+
					", p.minimumbd as p9minimumbd, p.normminimumbd as p10normminimumbd"+
					", p.maximumbd as p11maximumbd, p.normmaximumbd as p12normmaximumbd"+
					", vmu.id as v13muid,vmu.name as v14muname"+
					", vd.id as v15did,vd.name as v16dname"+
					", p.cntdecimal as p17cntdecimal"+
					", ''||p.id||case when p.type='2' then 'Name' else '' end as p18enterid "+
					", " +CreateSQLQuerty(parsedPdfInfo)+" as p19valuetextdefault "+
					",case when uv.useByDefault='1' then uv.name else '' end as p20valueVoc "+
					"from prescription pres "+
					"left join templateprotocol tp on tp.medservice_id=pres.medservice_id "+
					"left join parameterbyform pf on pf.template_id = tp.id "+
					"left join parameter p on p.id=pf.parameter_id "+
					"left join userDomain vd on vd.id=p.valueDomain_id "+
					"left join userValue uv on uv.domain_id=vd.id and uv.useByDefault='1' "+
					"left join vocMeasureUnit vmu on vmu.id=p.measureUnit_id "+
					"where pres.barcodeNumber ='"+parsedPdfInfo.getBarcode()+"'"+
					"order by pf.position");
				
					sout(1,"Начинаю 2 запрос - формирование json с результатами");
					
					Collection<WebQueryResult> lwqr = executeNativeSql(sql.toString(), theManager);
	
					sb.setLength(0);
					sb.append("{");
					sb.append("\"workFunction\":\"0\",") ;
					sb.append("\"workFunctionName\":\""+"\",") ;
					sb.append("\"isdoctoredit\":\"1\",") ;
					sb.append("\"params\":[") ;
					boolean firstPassed = false ;
					boolean firstError = false ;
					String[][] props = {{"1","id"},{"2","name"},{"3","shortname"}
					,{"4","type"},{"5","min"},{"6","nmin"},{"7","max"},{"8","nmax"}
					,{"9","minbd"},{"10","nminbd"},{"11","maxbd"},{"12","nmaxbd"}
					,{"13","unitid"},{"14","unitname"}
					,{"15","vocid"},{"16","vocname"},{"17","cntdecimal"}
					,{"18","idEnter"},{"19","value"},{"20","valueVoc"}
					} ;
					for(WebQueryResult wqr : lwqr) {
						StringBuilder par = new StringBuilder() ;
						par.append("{") ;
						boolean isFirtMethod = false ;
						boolean isError = false ;
						//System.out.println("-------*-*-*errr--"+wqr.get4()+"-------*-*-*errr--"+wqr.get15()) ;
						if (String.valueOf(wqr.get4()).equals("2")) {
							//System.out.println("-------*-*-*errr--"+wqr.get1()) ;
							if (wqr.get15()==null) {
								isError = true ;
								//System.out.println("-------*-*-*errr--"+wqr.get1()) ;
							}
						}
						try {
							for(String[] prop : props) {
								Object value = PropertyUtil.getPropertyValue(wqr, prop[0]) ;
								String strValue = value!=null?value.toString():"";
								
								if(isFirtMethod) par.append(", ") ;else isFirtMethod=true;
								par.append("\"").append(prop[1]).append("\":\"").append(str(strValue)).append("\"") ;
								
							}
							
						} catch (Exception e) {
							throw new IllegalStateException(e);
						}
					par.append("}") ;
					if (isError) {
						if(firstError) err.append(", ") ;else firstError=true;
						err.append(par) ;
					}else{
						if(firstPassed) sb.append(", ") ;else firstPassed=true;
						sb.append(par) ;
					}
				}
				sb.append("]") ;
				sb.append(",\"errors\":[").append(err).append("]") ;
				sb.append(",\"template\":\"").append(templateId).append("\"") ;
				sb.append(",\"protocol\":\"").append("\"") ;
				sb.append("}") ;
				
			    sout(1,"==== JSSON= "+sb.toString());
				 
			    saveLabAnalyzed(Long.valueOf(0),pid,Long.valueOf(0),sb.toString(),username, templateId) ;
				
				}catch(Exception e){
					e.printStackTrace();
				} 
			}
		
			
			}

		sout(1,"Finish setDefaultDiary");
		return sb.toString();
		
		} else {
			sout(1, "Штрих код пустой, либо это нет");
			return null;
		}
				
	}
	
	 private String CreateSQLQuerty (ParsedPdfInfo parsedPdfInfo)
	    {
	        int size = parsedPdfInfo.getResults().size();
	        StringBuilder s = new StringBuilder();
	        s.append("case");
	       List<ParsedPdfInfoResult> r = parsedPdfInfo.getResults(); 
	        for (ParsedPdfInfoResult p: r) {

	            s.append(" when p.externalcode='"+p.getCode()+
	               "' then '"+p.getValue()+"' ");
	            //if((i+1)<size) s+=" ||' '||";
	            //System.out.println(parsedPdfInfoResults.size());
	        }
	        s.append(" end");
	        return s.toString();
	    }
	 
	private String str(String aValue) {
    	if (aValue.indexOf("\"")!=-1) {
    		aValue = aValue.replaceAll("\"", "\\\\\"") ;
    	}
    	return aValue ;
    }
	
private Collection<WebQueryResult> executeNativeSql(String aQuery,Integer aMaxResult, EntityManager aManager) {
		
		return executeQuery(aManager.createNativeQuery(aQuery.replace("&#xA;", " ").replace("&#x9;", " ")),aMaxResult,theManager) ;
	}
private Collection<WebQueryResult> executeNativeSql(String aQuery, EntityManager aManager) {
		
		return executeNativeSql(aQuery,null,aManager) ;
	}
	
	private Collection<WebQueryResult> executeQuery(Query aQuery,Integer aMaxResult, EntityManager aManager) {

	List<Object> list ;

	if (aMaxResult!=null) {
		list= aQuery.setMaxResults(aMaxResult).getResultList() ;
	} else {
		list= aQuery.getResultList() ;
	}
	
	LinkedList<WebQueryResult> ret = new LinkedList<WebQueryResult>() ;
	long i = 0 ;
	Class<WebQueryResult> clazz = WebQueryResult.class ;
	Class<Object> obj_clazz =Object.class ;
	for (Object rowL : list) {
		
		WebQueryResult result = new WebQueryResult() ;
		if (rowL instanceof Object[]) {
			
			Object[] row = (Object[])rowL ;
			for (int ii =0 ;ii<row.length&&ii<27;ii++) {
				try {
					Method ejbSetterMethod = clazz.getMethod("set"+(ii+1), obj_clazz);
					ejbSetterMethod.invoke(result, row[ii]) ;
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		} else {
			result.set1(rowL) ;
		}
		
		result.setSn(++i) ;
		ret.add(result) ;
	}
	return ret ;
	}
	
	
	//-------------
	public Long clonePrescription(Long aPrescriptionId, Long aMedServiceId, Long aWorkFunctionId, String aCreateUsername) {
		ServicePrescription p = theManager.find(ServicePrescription.class, aPrescriptionId);
		
		Long ret = null;
		if (p!=null) { //Дублируем лаб. назначение (для бак. лаборатории)
			WorkFunction workFunction = theManager.find(WorkFunction.class, aWorkFunctionId);
			MedService medService = theManager.find(MedService.class, aMedServiceId);
			Long currentDate = new java.util.Date().getTime();
			System.out.println("=== all Good"+ p.getId());
			ServicePrescription presOld = (ServicePrescription) p;
			ServicePrescription presNew = new ServicePrescription();
			presNew.setCreateDate(new java.sql.Date(currentDate));
			presNew.setPrescriptionList(presOld.getPrescriptionList());
			presNew.setPlanStartDate(presOld.getPlanStartDate());
			presNew.setPrescriptSpecial(workFunction); 
			presNew.setMedService(medService);
			presNew.setCreateTime(new java.sql.Time(currentDate));
			presNew.setCreateUsername(aCreateUsername);
			presNew.setPrescriptType(presOld.getPrescriptType());
			presNew.setIntakeDate(presOld.getIntakeDate());
			
			presNew.setIntakeTime(presOld.getIntakeTime());
			presNew.setIntakeUsername(presOld.getIntakeUsername());
			presNew.setMaterialId(presOld.getMaterialId());
			presNew.setPrescriptCabinet(presOld.getPrescriptCabinet());
			presNew.setTransferDate(presOld.getTransferDate());
			presNew.setTransferTime(presOld.getTransferTime());
			presNew.setTransferUsername(presOld.getTransferUsername());
			presNew.setIntakeSpecial(presOld.getIntakeSpecial());
			theManager.persist(presNew);
			ret = presNew.getId();
		}
		return ret;
	}
	public String createNewDirectionFromPrescription(Long aPrescriptionListId, Long aWorkFunctionPlanId, Long aDatePlanId, Long aTimePlanId, Long aMedServiceId, String aUsername, Long aOrderWorkFunction) {
		MedService sms = theManager.find(MedService.class, aMedServiceId);
	if (sms!=null) {
		long date = new java.util.Date().getTime() ;
	
	PrescriptList pl = theManager.find(PrescriptList.class, aPrescriptionListId);
	Patient pat = pl.getMedCase().getPatient();
	WorkFunction wfp = theManager.find(WorkFunction.class, aWorkFunctionPlanId);
	WorkFunction wfo = theManager.find(WorkFunction.class, aOrderWorkFunction);
	WorkCalendarTime wct = theManager.find(WorkCalendarTime.class, aTimePlanId);
	Visit vis;
	if (wct.getMedCase()!=null) {
		vis = (Visit) wct.getMedCase();
	} else {
		vis = new Visit();
		MedCase mc = pl.getMedCase() ;
		if (mc instanceof HospitalMedCase|| mc instanceof DepartmentMedCase) {
			VocServiceStream  vss = (VocServiceStream) theManager.createQuery("from VocServiceStream where code=:code").setParameter("code", "HOSPITAL").getSingleResult();
			vis.setServiceStream(vss);
		} else {
			vis.setServiceStream(mc.getServiceStream()) ;
		}
		VocWorkPlaceType wpt = (VocWorkPlaceType) theManager.createQuery("from VocWorkPlaceType where code=:code").setParameter("code", "POLYCLINIC").getSingleResult();
		vis.setWorkPlaceType(wpt) ;
		
		vis.setPatient(pat);
		vis.setCreateDate(new java.sql.Date(date));
		vis.setCreateTime(new java.sql.Time(date));
		if (aDatePlanId!=null && !aDatePlanId.equals(wct.getWorkCalendarDay().getId())) {
			System.out.println("==== Создание визита из назначения пошло не так. PL= "+aPrescriptionListId+" : "+aDatePlanId+" <> "+ wct.getWorkCalendarDay().getId());
			return null;
		}
		vis.setDatePlan(wct.getWorkCalendarDay());
		vis.setNoActuality(false);
		vis.setTimePlan(wct);
		vis.setWorkFunctionPlan(wfp);
		vis.setOrderWorkFunction(wfo);
		vis.setUsername(aUsername);
		theManager.persist(vis);
	}
	
	
	
	ServiceMedCase smc = new ServiceMedCase();
	smc.setParent(vis);
	smc.setMedService(sms);
	smc.setPatient(pat);
	smc.setNoActuality(false);
	theManager.persist(smc);
	wct.setMedCase(vis) ;
	theManager.persist(wct) ;
	return ""+vis.getId();	
	}
		return null ;
	}
	public String saveLabAnalyzed(Long aSmoId,Long aPrescriptId,Long aProtocolId, String aParams, String aUsername, Long aTemplateId) throws JSONException {
		Protocol d =null;
		//if (aProtocolId!=null )) {
		JSONObject obj = new JSONObject(aParams) ;
		String wf = String.valueOf(obj.get("workFunction"));
		System.out.print("workfunction================"+wf);
		
		StringBuilder sql = new StringBuilder() ;
		sql.append("select trim(ms.code|| ' ' ||ms.name) from prescription p " +
				" left join medservice ms on ms.id=p.medservice_id where p.id=").append(aPrescriptId);
		String ms=theManager.createNativeQuery(sql.toString()).getSingleResult().toString();
		if(ms!=null&&!ms.equals("")) ms+="\n";
		Visit m = theManager.find(Visit.class, aSmoId) ;
		 
		if (m!=null) {
			List<Object> l = null;
			if (aProtocolId!=null && !aProtocolId.equals(Long.valueOf(0))) {
				sql = new StringBuilder() ;
				sql.append("select id from Diary where id=").append(aProtocolId).append(" and medCase_id=").append(aSmoId).append("") ;
				l = theManager.createNativeQuery(sql.toString()).getResultList() ;
			}
			if (l==null || l.isEmpty()) {
				sql = new StringBuilder() ;
				sql.append("select id from Diary where medCase_id=").append(aSmoId).append("") ;
				l = theManager.createNativeQuery(sql.toString()).getResultList() ;
			}
			if (!l.isEmpty()) {
				Long idD = ConvertSql.parseLong(l.get(0)) ;
				d = theManager.find(Protocol.class, idD) ;
				theManager.createNativeQuery("delete from FormInputProtocol where docProtocol_id="+d.getId()).executeUpdate() ;
			}
		} else {
			Long smo = checkLabAnalyzed(aPrescriptId,Long.valueOf(wf),aUsername) ;
			m = theManager.find(Visit.class, smo) ;
		}
		if (d == null) {
			d = new RoughDraft() ;
			d.setMedCase(m) ;
			d.setTemplateProtocol(aTemplateId);
			theManager.persist(d) ;
		}
		Prescription pres = theManager.find(Prescription.class,aPrescriptId) ;
		//}
		JSONArray params = obj.getJSONArray("params");
		StringBuilder sb = new StringBuilder() ;
		//sb.append(ms).append("Забор биоматериала произведен: ").append(DateFormat.formatToDate(pres.getIntakeDate()));
		sb.append("Забор биоматериала произведен: ").append(DateFormat.formatToDate(pres.getIntakeDate()));
		sb.append(" ").append(DateFormat.formatToTime(pres.getIntakeTime())).append("\n") ;
		//sb.append("Дата передачи в лабораторию: ").append(DateFormat.formatToDate(pres.getTransferDate()));
		//sb.append(" ").append(DateFormat.formatToTime(pres.getTransferTime())).append("\n") ;
		for (int i = 0; i < params.length(); i++) {
			//boolean isSave = true ;
			JSONObject param = (JSONObject) params.get(i);
			FormInputProtocol fip = new FormInputProtocol() ;
			fip.setDocProtocol(d) ;
			Parameter p = theManager.find(Parameter.class, ConvertSql.parseLong(param.get("id"))) ;
			fip.setParameter(p) ;
			fip.setPosition(Long.valueOf(i+1)) ;
			String type = String.valueOf(param.get("type"));
			// 1-числовой
			// 4-числовой с плав точкой
			String value = String.valueOf(param.get("value"));
			if (type.equals("1")||type.equals("4")) {
				if (!StringUtil.isNullOrEmpty(value)) {
					fip.setValueBD(new BigDecimal(value)) ;
					if (sb.length()>0) sb.append("\n") ;
					sb.append(param.get("name")).append(": ") ;
					sb.append(value).append(" ") ;
					sb.append(param.get("unitname")).append(" ") ;
					//start Добавление отображения референтных интервалов
					//sb.append("Реф. инт: ").append(p.getNormMinimumBD()).append(" <> ").append(p.getNormMaximumBD());
					//end
				}
				//пользовательский справочник
			} else if (type.equals("2")) {
				if (value!=null&&!value.equals("")){
					Long id = ConvertSql.parseLong(value) ;
					if (id!=null && !id.equals(Long.valueOf(0))) {
						UserValue uv = theManager.find(UserValue.class, id) ;
						fip.setValueVoc(uv) ;
						if (sb.length()>0) sb.append("\n") ;
						sb.append(param.get("name")).append(": ") ;
						sb.append(param.get("valueVoc")).append(" ") ;
						sb.append(param.get("unitname")).append(" ") ;
					}
				}
				//3-текстовый
				//5-текстовый с ограничением
			} else if (type.equals("3")||type.equals("5")) {
				if (!StringUtil.isNullOrEmpty(value)) {
					fip.setValueText(String.valueOf(value)) ;
					if (sb.length()>0) sb.append("\n") ;
					sb.append(param.get("name")).append(": ") ;
					sb.append(value).append(" ") ;
					sb.append(param.get("unitname")).append(" ") ;
				}
			}
			theManager.persist(fip) ;
		}
		d.setRecord(sb.toString()) ;
		theManager.persist(d) ;
		if (wf!=null && !wf.equals("0")) {
			WorkFunction wfo = theManager.find(WorkFunction.class, Long.valueOf(wf)) ;
			m.setWorkFunctionExecute(wfo) ;
		} else {
			m.setWorkFunctionExecute(m.getWorkFunctionPlan()) ;
			theManager.persist(m) ;
		}
		theManager.persist(m) ;
		return "" ;
	}
	public Long checkLabAnalyzed(Long aPrescriptId,Long aWorkFunctionId,String aUsername) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select pat.id as patid,case when slo.dtype='DepartmentMedCase' then sls.id") ; 
		sql.append(" when slo.dtype='Visit' then sls.id else slo.id end as pmo") ;
		sql.append(" ,p.prescriptSpecial_id as presspec") ;
		sql.append(" ,p.prescriptCabinet_id as cabinet") ;
		sql.append(" ,p.medService_id as service") ;
		sql.append(" from prescription p ") ;
		sql.append(" left join PrescriptionList pl on pl.id=p.prescriptionlist_id left join medcase slo on slo.id=pl.medcase_id left join medcase sls on sls.id=slo.parent_id left join patient pat on pat.id=slo.patient_id where p.id=").append(aPrescriptId) ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		if (aWorkFunctionId == null) {
			List<Object> wf = theManager.createNativeQuery("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login='"+aUsername+"'").getResultList() ;
			if ( wf.isEmpty()) {
				return null ;
			}
			aWorkFunctionId = ConvertSql.parseLong(wf.get(0)) ;
		}
		System.out.println("=== DEBUG noMedCase");
		
		if (list.isEmpty()) return null ;
		Object[] objs = list.get(0) ;
		Prescription pres = theManager.find(Prescription.class, aPrescriptId) ;
		Patient pat = theManager.find(Patient.class, ConvertSql.parseLong(objs[0])) ;
		MedCase mc = theManager.find(MedCase.class,ConvertSql.parseLong(objs[1])) ;
		WorkFunction ps = theManager.find(WorkFunction.class, ConvertSql.parseLong(objs[2])) ;
		WorkFunction pc = theManager.find(WorkFunction.class, ConvertSql.parseLong(objs[3])) ;
		WorkFunction wfCur = theManager.find(WorkFunction.class, aWorkFunctionId) ;
		MedService ms = theManager.find(MedService.class, ConvertSql.parseLong(objs[4])) ;
		long date = new java.util.Date().getTime() ;
		Visit vis = new Visit() ;
		vis.setParent(mc) ;
		vis.setOrderWorkFunction(ps) ;
		vis.setWorkFunctionPlan(pc) ;
		vis.setPatient(pat) ;
		vis.setCreateDate(new java.sql.Date(date)) ;
		vis.setCreateTime(new java.sql.Time(date)) ;
		vis.setUsername(aUsername) ;
		vis.setNoActuality(true);
		ServiceMedCase smc = new ServiceMedCase() ;
		smc.setPatient(pat) ;
		smc.setMedService(ms) ;
		smc.setParent(vis) ;
		smc.setOrderWorkFunction(ps) ;
		smc.setWorkFunctionPlan(pc) ;
		smc.setWorkFunctionExecute(wfCur) ;
		smc.setCreateDate(new java.sql.Date(date)) ;
		smc.setCreateTime(new java.sql.Time(date)) ;
		smc.setUsername(aUsername) ;
		pres.setMedCase(vis) ;
		theManager.persist(vis) ;
		theManager.persist(smc) ;
		theManager.persist(pres) ;
		//theManager.createNativeQuery("update prescription set medcase_id="+vis.getId()+" where id="+aPrescriptId).executeUpdate() ;
		return  vis.getId();
	}
	public Long checkLabAnalyzed(Long aPrescriptId,String aUsername) {
		List<Object> wf = theManager.createNativeQuery("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login='"+aUsername+"'").getResultList() ;
		if ( wf.isEmpty()) {
			return null ;
		} else {
			return checkLabAnalyzed(aPrescriptId,ConvertSql.parseLong(wf.get(0)), aUsername) ;
		}
		
		
	}
	public Long createTempPrescriptList(String aName,String aComment,String aCategories,String aSecGroups) {
		PrescriptListTemplate temp = new PrescriptListTemplate() ;
		temp.setName(aName) ;
		temp.setComments(aComment) ;
		theManager.persist(temp) ;
		return temp.getId() ;
	}
	
	public void setPatientDateNumber(String aPrescriptions, String aDate, String aTime, String aUsername, Long aSpecId) throws ParseException {
		SimpleDateFormat sdfIn =new SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat sdfOut =new SimpleDateFormat("yyyy-MM-dd") ;
		aDate = sdfOut.format(sdfIn.parse(aDate));
		String[] prescriptionIds = aPrescriptions.split(",");
		String aKey = "";
		String matId = null;
		HashMap<java.lang.String, java.lang.String> aLabMap =  new HashMap<String, String>() ;
		for (int i=0;i<prescriptionIds.length;i++) {
			Long presId = Long.parseLong(prescriptionIds[i].trim());
			ServicePrescription p = theManager.find(ServicePrescription.class, presId);
//			if (p!=null &&p.getMedService().getServiceType().getCode().equals("LABSURVEY")&&aDate!=null&&!aDate.equals("")) {
			if (p!=null &&aDate!=null&&!aDate.equals("")) {
				Long aPatientId =p.getPrescriptionList().getMedCase().getPatient().getId(); 
				aKey = ""+aPatientId+"#"+aDate;
				matId = getPatientDateNumber(aLabMap, aKey, aPatientId, aDate, theManager);
				aLabMap.put(aKey, matId);
			//	p.setIntakeDate(DateFormat.parseSqlDate(aDate));
			//	p.setIntakeTime(DateFormat.parseSqlTime(aTime));
				p.setMaterialId(matId);
			//	if (aBarcode!=null&&!aBarcode.equals("")){
			//		p.setBarcodeNumber(aBarcode);
			//	}
			//	p.setIntakeUsername(aUsername);
			//	p.setIntakeSpecial(theManager.find(WorkFunction.class, aSpecId));
				theManager.persist(p);
			} 		
		}
		
	}
	
	/**
	 * 
	 * @param aLabMap - карта (Ключ = ИД пациента+дата назначения)
	 * @param aKey - Ключ = ИД пациента+дата назначения
	 * @param aPatientId - ИД пациента
	 * @param aDate - дата назначения
	 * @param aManager
	 * @return Номер дня пациента для исследования
	 */
	
	public static String getPatientDateNumber(HashMap aLabMap, String aKey, long aPatientId, String aDate, EntityManager aManager) {
		String matId = null;
		HashMap<java.lang.String, java.lang.String> labMap = (HashMap<java.lang.String, java.lang.String>) aLabMap ;
		if (!labMap.isEmpty()) { 
			matId = labMap.get(aKey);
		}
		if (matId == null || matId.equals("")) {
			String req = "select p.materialId from prescription p " +
					"left join PrescriptionList pl on pl.id=p.prescriptionList_id " +
					"left join medcase mc on mc.id=pl.medCase_id " +
					"where mc.patient_id='"+aPatientId+"' " +
							"and p.intakeDate=to_date('"+aDate+"','yyyy-mm-dd') and p.materialId is not null " +
									"and p.canceldate is null " +
									"and p.materialId!='' order by p.materialId desc ";
			System.out.println(req);
			List<String> lPl = aManager.createNativeQuery(req).getResultList();
			
			if (lPl.size()>0) {
				matId = ""+lPl.get(0) ;
			}  
			if (matId == null || matId.equals("")) {
				SequenceHelper seqHelper = ru.ecom.ejb.sequence.service.SequenceHelper.getInstance() ;
				matId=seqHelper.startUseNextValueNoCheck("Prescription#Lab#"+aDate, aManager);
			}
		}
		return matId;
	}
	
	/**
	 * Получить описание шаблона листа назначения
	 * @param aIdTemplateList - ИД листа назначения
	 * @return описание листа назначений
	 */
	
	public String getDescription(Long aIdTemplateList) {
		PrescriptListTemplate template = theManager.find(PrescriptListTemplate.class, aIdTemplateList);
		if (template==null) return "";
		StringBuffer description = new StringBuffer();
		description.append("Название шаблона: ") ; 
		description.append(template.getName());
		description.append('\n');
		description.append("Комментарии: ");
		description.append(template.getComments());
		description.append('\n');
		description.append("Владелец: ") ;
		description.append(template.getOwnerInfo()) ;
		description.append('\n');
		description.append("Дата создания:") ;
		if (template.getCreateDate()!=null) {
			description.append(DateFormat.formatToDate(template.getCreateDate())) ;
		}
		description.append('\n');
		description.append("-----------------------------");
		description.append('\n');
		description.append(getDescrPerscriptions(template));
		return description.toString();
	}

	/**
	 * Добавить все назначения в существующий лист
	 * @param aIdTemplateList - ИД шаблона листа назначений
	 * @param aIdParent - ИД листа назначений
	 * @return true - при успешном сохранении, false - при ошибке при сохранении
	 */
	public boolean savePrescriptExists(Long aIdTemplateList, Long aIdParent) {
		if (aIdTemplateList.equals(aIdParent)) throw new IllegalArgumentException("Невозможно добавить назначения. Шаблон листа назначения и текущий лист назначений должны быть разными!!!");
		PrescriptListTemplate template = theManager.find(PrescriptListTemplate.class, aIdTemplateList);
		AbstractPrescriptionList listPresc = theManager.find(AbstractPrescriptionList.class, aIdParent) ;
		addPrescription(template,listPresc,WorkerServiceBean.getWorkFunction(theContext, theManager)) ;
		return true ;
	}
	
	/** Проверка на возможность создавать направление с типом "экстренно".
	 * @param aPrescriptionListId - ИД листа назначения
	 * @return true - может быть создано назначение с типом "экстренно"
	 */
	public boolean checkMedCaseEmergency(Long aId, String idType) {
		boolean isEmergency =false ;
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss") ;
		String sqlquery = "select mc.datestart || '-' || mc.entrancetime as datetime " +
				" ,case when mc.emergency='1' then '1' when mcs.emergency='1' then '1' else null end as caseEmergency " +
				" from medCase mc " +
				" left join medcase mcs on mcs.id = mc.parent_id ";
				
		if (idType.equals("prescriptionList")) {
			sqlquery+=" left join prescriptionList pl on pl.medcase_id = mc.id " +
					" where pl.id ='"+aId+"' and (mcs.dtype='HospitalMedCase' or mc.dtype='HospitalMedCase') ";
		} else if (idType.equals("medCase")) {
			sqlquery+=" where mc.id ='"+aId+"' and (mcs.dtype='HospitalMedCase' or mc.dtype='HospitalMedCase')";
		} else {
			return false; 
		}
		List<Object[]> list = theManager.createNativeQuery(sqlquery).getResultList() ;
		if (list.size()>0) {
			Object[] obj = list.get(0);
			
			java.util.Date date;
			try {
				date = sdf.parse(obj[0].toString());
				boolean check = ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SecPolicy.isDateLessThenHour(date,2);
				if (obj[1]!=null && check) { 
					isEmergency=true;
				}
			} catch (ParseException e) {
							
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return isEmergency ;
	}
	/**
	 * Получение списка назначений из шаблона в добавление их в лист назначений. 
	 */
	
	public String getLabListFromTemplate(Long aIdTemplateList) {
		PrescriptListTemplate template = theManager.find(PrescriptListTemplate.class, aIdTemplateList);
		//System.out.println("======= getLabList, tmpl = "+template );
		StringBuilder labList = new StringBuilder();
		for (Prescription presc: template.getPrescriptions()) {
			labList.append(getPrescriptionInfo(presc));
		}
		if (template.getComments()!=null&&template.getComments().length()>0) {
			labList.append("COMMENT@").append(template.getComments()).append("#");
		}
		 
		return labList.length()>0?labList.substring(0, labList.length()-1):"";
	}

	/**
	 *  Получение списка типов назначений (ИД+название)
	 */
	public String getPrescriptionTypes(boolean isEmergency) {
		StringBuilder req = new StringBuilder();
		StringBuilder res = new StringBuilder();
		req.append("select vpt.id, vpt.name, case when vpt.isOnlyCurrentDate='1' then '1' else '0' end as onlyCurrentDate from vocprescripttype vpt ");
		if(!isEmergency) {
			req.append("where vpt.code!='EMERGENCY' ");
		}
		req.append("order by vpt.id ");
		List<Object[]> list = theManager.createNativeQuery(req.toString()).getResultList() ;
		//System.out.println("----------in getPrescriptionTypes, isBool = "+isEmergency);
		for (Object[] o: list) {
			res.append(o[0]).append(":").append(o[1]).append(":").append(o[2]).append("#");
		}
		
		return res.length()>0?res.substring(0,res.length()-1):"";
	}
	
	/**
	 * Получение данных о назначении (для функции getLabListFromTemplate)
	 * @param aPresc - ID назначения (шаблона)
	 * @return список исследований по шаблону
	 */
	private String getPrescriptionInfo (Prescription aPresc) {
		StringBuilder list = new StringBuilder();
	//	System.out.println("in getPrescriptioninfo, aPresc = "+aPresc);
		if (aPresc instanceof DrugPrescription) {
			try{
			DrugPrescription presNew = (DrugPrescription) aPresc;
			list.setLength(0);
			list.append("DRUG@");
			list.append(presNew.getDrug().getId()).append(":");
			list.append(presNew.getDrug().getName()).append("::"); //: Date 
			list.append(presNew.getMethod().getId()).append(":");
			list.append(presNew.getMethod().getName()).append(":");
			if (presNew.getFrequency()!=null){
				list.append(presNew.getFrequency()).append(":");
				list.append(presNew.getFrequencyUnit().getId()).append(":");
				list.append(presNew.getFrequencyUnit().getName()).append(":");
			} else list.append(":::");
			if (presNew.getAmount()!=null) {
				list.append(presNew.getAmount()).append(":");
				list.append(presNew.getAmountUnit().getId()).append(":");
				list.append(presNew.getAmountUnit().getName()).append(":");
			} else list.append(":::");
			if (presNew.getDuration()!=null) {
				list.append(presNew.getDuration()).append(":");
				list.append(presNew.getDurationUnit().getId()).append(":");
				list.append(presNew.getDurationUnit().getName()).append("#");
			} else list.append("::#");
			
			return list.toString() ;
			}
			catch (Exception e) {
				System.out.println("catch Drug "+e);
				e.printStackTrace();
			}
		} else if (aPresc instanceof DietPrescription) {
			try{
			DietPrescription presNew = (DietPrescription) aPresc;
			list.setLength(0);
			list.append("DIET@");
			list.append(presNew.getDiet().getId()).append(":") ;
			list.append(presNew.getDiet().getName()).append("#") ;
			return list.toString();
			}
			catch (Exception e) {
				System.out.println("catch DIET "+e);
				e.printStackTrace();
			}
		} else if (aPresc instanceof ServicePrescription) {
			try {
				ServicePrescription presNew = (ServicePrescription) aPresc;
				if (presNew.getMedService().getFinishDate()!=null) {
					System.out.println("Услуга "+presNew.getMedService().getName()+" запрещена к назначению (закрыта)");
					return "";
				}
				list.setLength(0);
				list.append("SERVICE@");
				list.append(presNew.getMedService().getServiceType().getCode()).append(":");
				list.append(presNew.getMedService().getId()).append(":");
				list.append(presNew.getMedService().getCode()).append(" ");
				list.append(presNew.getMedService().getName()).append("::"); //: aLabDate 
				list.append(presNew.getPrescriptCabinet()!=null?presNew.getPrescriptCabinet().getId():"").append(":");
				list.append(presNew.getPrescriptCabinet()!=null?presNew.getPrescriptCabinet().getName():"").append(":");
				list.append(presNew.getDepartment()!=null?presNew.getDepartment().getId():"").append(":");
				list.append(presNew.getDepartment()!=null?presNew.getDepartment().getName():"").append("#");
				return list.toString();
			} catch (Exception e) {
				System.out.println("catch Service "+e);
				e.printStackTrace();
			}
		} else if (aPresc instanceof ModePrescription)  {
			ModePrescription prescNew = (ModePrescription) aPresc;
			list.setLength(0);
			list.append("MODE@");
			list.append(prescNew.getModePrescription().getName()).append(":");
			list.append(prescNew.getModePrescription().getId()).append("#");
			return list.toString();
		} 
		System.out.println("_----------------Some shit happens!!!"+aPresc);
		return "";
	}
	/**
	 * Создаем шаблон листа назначений из существующего ЛН 
	 */
	public Long savePrescriptNew(Long aIdTemplateList, Long aIdParent) {
		return savePrescriptNew(aIdTemplateList, aIdParent,null);
	}
	
	/**
	 * Добавить все назначения в новый лист
	 * @param aIdTemplateList - ИД шаблона листа назначений
	 * @param aIdParent - ИД СМО (если СМО не указан создается шаблон !!!)
	 * @return true - при успешном сохранении, false - при ошибке при сохранении
	 */
	public Long savePrescriptNew(Long aIdTemplateList, Long aIdParent, String aName) {
		AbstractPrescriptionList template = theManager.find(AbstractPrescriptionList.class, aIdTemplateList);
	//	PrescriptListTemplate template = theManager.find(PrescriptListTemplate.class, aIdTemplateList);
	//	PrescriptListTemplate template = theManager.find(PrescriptListTemplate.class, aIdTemplateList);
		MedCase medCase = theManager.find(MedCase.class, aIdParent) ;
		WorkFunction wf = WorkerServiceBean.getWorkFunction(theContext, theManager) ;
		System.out.println("template="+template.getId());
		System.out.println("medCase="+aIdParent) ;
		
		AbstractPrescriptionList list  ;
		if (medCase!=null) {
//			System.out.println("MedCase существует ! Создается PrescriptList") ;
			 list = new PrescriptList() ;
			
			list.setMedCase(medCase) ;
			list.setCreateDate(new java.sql.Date(new Date().getTime())) ;
			list.setCreateUsername(theContext.getCallerPrincipal().toString());
			if (aName!=null&&!aName.equals("")) {
				list.setName(aName) ;
			} else {
				list.setName(template.getName()) ;
			}
			list.setComments(template.getComments());
			list.setWorkFunction(wf) ;
			theManager.persist(list) ;
			addPrescription(template,list,wf) ;
		} else  {
//			System.out.println("MedCase не существует ! Создается PrescriptListTemplate") ;
			list =  new PrescriptListTemplate() ;
			list.setCreateDate(new java.sql.Date(new Date().getTime())) ;
			list.setCreateUsername(theContext.getCallerPrincipal().toString());
			if (aName!=null&&!aName.equals("")) {
				list.setName(aName) ;
			} else {
				list.setName(template.getName()) ;
			}
			
			//System.out.println("================ CLASS PrescriptionList = "+template.getClass().toString());
			if (template.getClass().toString().equals("PrescriptListTemplate")) {
				 template = (PrescriptListTemplate) template;
			//	System.out.println("================ IF HAPPENS CLASS PrescriptionList = "+template.getClass().toString());	
				List<TemplateCategory> tempCategList = new ArrayList<TemplateCategory>() ;
				PrescriptListTemplate template2 = (PrescriptListTemplate) template;
				for (TemplateCategory tempCateg:template2.getCategories()) {
					tempCategList.add(tempCateg) ;
				}
				//list.setCategories(tempCategList);
			}
		list.setComments(template.getComments());
		list.setWorkFunction(wf);
		theManager.persist(list) ;
		theManager.flush() ;
		addPrescription(template,list,null) ;
		}
		return list.getId() ;
	}
	
	private void addPrescription(AbstractPrescriptionList aTemplate, AbstractPrescriptionList aList, WorkFunction aSpecialist) {		
		if (aTemplate!=null && aList!=null)  {
			List<Prescription> listNew = aList.getPrescriptions() ;
			if (listNew==null) listNew =new ArrayList<Prescription>() ;
			for (Prescription presc:aTemplate.getPrescriptions()) {
//				System.out.println("Назначение: "+presc.getId());
				Prescription prescNew = newPrescriptionOnTemplate(presc, aSpecialist);
//				System.out.println("создание копии назначения") ;
				
				prescNew.setPrescriptionList(aList) ;
				listNew.add(prescNew);
//				System.out.println("list...") ;
//				System.out.print("pres...") ;
				theManager.flush() ;
			}	
			aList.setPrescriptions(listNew) ;
			theManager.persist(aList) ;
		}
	}
	
	private String getDescrPerscriptions(PrescriptListTemplate aTemplate) {
		StringBuffer desc = new StringBuffer() ;
		List<Prescription> listPrescript =aTemplate.getPrescriptions() ;
		int i = 0 ;
		desc.append("Назначения: ");
		for (Prescription presc:listPrescript) {
			desc.append('\n');
			desc.append(++i);
			desc.append(". ");
			desc.append(presc.getDescriptionInfo());
		}
		return desc.toString() ;
	}
	private Prescription newPrescriptionOnTemplate(Prescription aPrescOld, WorkFunction aSpecialist) {
		if (aPrescOld instanceof DrugPrescription) {
			DrugPrescription presNew = new DrugPrescription();
			DrugPrescription presOld = (DrugPrescription)aPrescOld ;
			presNew.setAmount(presOld.getAmount());
			presNew.setAmountUnit(presOld.getAmountUnit());
			presNew.setComments(presOld.getComments());
			presNew.setDrug(presOld.getDrug());
			presNew.setDuration(presOld.getDuration());
			presNew.setDurationUnit(presOld.getDurationUnit());
			presNew.setFrequency(presOld.getFrequency());
			presNew.setFrequencyUnit(presOld.getFrequencyUnit());
			presNew.setMethod(presOld.getMethod());
			presNew.setOrderTime(presOld.getOrderTime());
			presNew.setOrderType(presOld.getOrderType());
			presNew.setPrescriptSpecial(aSpecialist) ;
			return presNew ;
		} else if (aPrescOld instanceof DietPrescription) {
			DietPrescription presNew = new DietPrescription() ;
			DietPrescription presOld = (DietPrescription)aPrescOld ;
			presNew.setDiet(presOld.getDiet()) ;
			presNew.setPrescriptSpecial(aSpecialist) ;
			return presNew ;
		} else if (aPrescOld instanceof ServicePrescription) {
			ServicePrescription presNew = new ServicePrescription();
			ServicePrescription presOld = (ServicePrescription) aPrescOld;
			presNew.setMedService(presOld.getMedService());
			presNew.setPrescriptCabinet(presOld.getPrescriptCabinet());
			presNew.setPrescriptSpecial(presOld.getPrescriptSpecial());
			presNew.setDepartment(presOld.getDepartment());
			return presNew;
		} else if (aPrescOld instanceof ModePrescription) {
			ModePrescription presNew = new ModePrescription();
			ModePrescription presOld = (ModePrescription) aPrescOld;
			presNew.setModePrescription(presOld.getModePrescription());
			return presNew;
		}
		
		return null ;
}

		
		@EJB ILocalEntityFormService 
		theEntityFormService ;
	    @PersistenceContext
	    EntityManager theManager ;
	    @Resource
		SessionContext theContext ;
		public ParsedPdfInfo getPdfInfoByBarcode(List<ParsedPdfInfo> list,
				String aBarcode) {
			// TODO Auto-generated method stub
			return null;
		}

		public void checkPdf() throws IOException, NoSuchFieldException,
				IllegalAccessException, JSONException {
			// TODO Auto-generated method stub
			
		}
		
	}	



