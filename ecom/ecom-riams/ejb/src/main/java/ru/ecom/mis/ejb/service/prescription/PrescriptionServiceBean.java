package ru.ecom.mis.ejb.service.prescription;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.ecom.diary.ejb.domain.protocol.parameter.FormInputProtocol;
import ru.ecom.diary.ejb.domain.protocol.parameter.Parameter;
import ru.ecom.diary.ejb.domain.protocol.parameter.user.UserValue;
import ru.ecom.diary.ejb.service.protocol.ParsedPdfInfo;
import ru.ecom.diary.ejb.service.protocol.ParsedPdfInfoResult;
import ru.ecom.ejb.sequence.service.SequenceHelper;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.live.domain.CustomMessage;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.*;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType;
import ru.ecom.mis.ejb.domain.prescription.*;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.service.worker.WorkerServiceBean;
import ru.ecom.poly.ejb.domain.protocol.Protocol;
import ru.ecom.poly.ejb.domain.protocol.RoughDraft;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Stateless
@Remote(IPrescriptionService.class)
public class PrescriptionServiceBean implements IPrescriptionService {
	private static final Logger LOG = Logger.getLogger(PrescriptionServiceBean.class);

	public void checkXmlFiles() throws ParserConfigurationException, SAXException, IOException {

		String homeDirectory  =  getDir("jboss.lab.xmldir","/opt/lab");
		String xmlDirectory = homeDirectory + "/xml/";
        String xmlArchDirectory = homeDirectory + "/archive/";
        
        File[] fileList = getFiles(xmlDirectory);
        
        if (fileList.length>0) {
        	LOG.debug("Найдено "+fileList.length+" файлов");
        	
        	for (File file: fileList) {
	        	String fileName = file.getName();
	        	String[] expansions = fileName.split("\\.");
	            if(expansions[1].equals("xml")) {
	        		List<ParsedPdfInfo> l = readXML(xmlDirectory+fileName);
					for (ParsedPdfInfo parsedPdfInfo:l) {
						setDefaultDiary(parsedPdfInfo);
					}
	        		moveFile(xmlDirectory,xmlArchDirectory,fileName);
	        	}
        	}
	    } else {
	    	LOG.warn("Файлов не найдено, заканчиваем...");
	    }
	}
	
	private static List<ParsedPdfInfo> readXML(String namefile) throws ParserConfigurationException, SAXException, IOException {
		ParsedPdfInfo parsedPdfInfo ;
		List<ParsedPdfInfoResult> parsedPdfInfoResults = new ArrayList<>();
        List<ParsedPdfInfo>parsedPdfInfos = new ArrayList<>();
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        DocumentBuilder builder = f.newDocumentBuilder();
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
	     	try {
	         File dir = new File(path);
	         return dir.listFiles(new FilenameFilter() {
				 public boolean accept(File dir, String name) {
					 return name.toLowerCase().endsWith(".xml");
				 }});
	     	} catch(Exception e) {
	     		LOG.error("Директория не обнаружена. Проверьте правильность.",e);
	     		return new File[0];
	     	}
	     }
	    
		//Кажется, нигде не используем
	  /*  public static boolean checkIsExist(String filePath, boolean resultExist) {
	        File f = new File(filePath);
	        return  f.exists() && !f.isDirectory();
	    }
	    */
	    private static void moveFile(String pdfDirectory, String archDirectory, String fileName) {
	        try {
	            final File myFile = new File(pdfDirectory + fileName );
	            if (myFile.renameTo(new File(archDirectory + fileName))) {
	                LOG.info("Файл "+ fileName + " успешно перенесен из директории " + pdfDirectory + " в директорию " + archDirectory);
	            } else {
					LOG.warn("Файл не был перенесен!");
	            }
	        } catch (Exception e) {
	            LOG.error("ОШибка переноса файла",e);
	        }
	    }

	public String setDefaultDiary(ParsedPdfInfo parsedPdfInfo) {
		if (parsedPdfInfo==null) {
			LOG.error("NO_RESULT");
			return "";
		}
		String barcode = parsedPdfInfo.getBarcode();
		if (!StringUtil.isNullOrEmpty(barcode)) {
		
		StringBuilder sb = new StringBuilder() ;
		StringBuilder err = new StringBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		
		
		StringBuilder sqlAdd= new StringBuilder();
		List<ParsedPdfInfoResult> results =parsedPdfInfo.getResults();
		for(int i=0;i<results.size();i++) {
			if(i>0){sqlAdd.append(",");}
			sqlAdd.append("'").append(results.get(i).getCode()).append("'");
		}
		
		sql.append("select pres.id as pid, ms.id as msid, max(tp.id) as templateId "+
					"from prescription pres "+
					"left join medservice ms on ms.id=pres.medservice_id "+
					"left join templateprotocol tp on tp.medservice_id=ms.id "+
					"left join parameterbyform pf on pf.template_id = tp.id "+
					"left join parameter p on p.id=pf.parameter_id "+
		        	"where pres.barcodeNumber ='").append(barcode).append("' "+
					"and p.externalcode in(").append(sqlAdd).append(") "+
					"group by pres.id,ms.id");

		List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList();

		if (!list.isEmpty()) {
			String username = "LabRobot";
			for (Object[] obj: list) {
				try {
					Long pid = Long.parseLong(obj[0].toString());
				//	String msid = res.get2().toString();
					Long templateId = Long.parseLong(obj[2].toString());
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
					", ").append(createSQLQuery(parsedPdfInfo)).append(" as p19valuetextdefault "+
					",case when uv.useByDefault='1' then uv.name else '' end as p20valueVoc "+
					"from prescription pres "+
					"left join templateprotocol tp on tp.medservice_id=pres.medservice_id "+
					"left join parameterbyform pf on pf.template_id = tp.id "+
					"left join parameter p on p.id=pf.parameter_id "+
					"left join userDomain vd on vd.id=p.valueDomain_id "+
					"left join userValue uv on uv.domain_id=vd.id and uv.useByDefault='1' "+
					"left join vocMeasureUnit vmu on vmu.id=p.measureUnit_id "+
					"where pres.id=").append(pid).append(" and pres.barcodeNumber ='").append(barcode).append("'"+
					"order by pf.position");
				
					List<Object[]> lwqr = theManager.createNativeQuery(sql.toString()).getResultList();
	
					sb.setLength(0);
					sb.append("{");
					sb.append("\"workFunction\":\"0\",") ;
					sb.append("\"workFunctionName\":\""+"\",") ;
					sb.append("\"isdoctoredit\":\"1\",") ;
					sb.append("\"params\":[") ;
					boolean firstPassed = false ;
					boolean firstError = false ;
					String[][] props = {{"0","id"},{"1","name"},{"2","shortname"}
							,{"3","type"},{"4","min"},{"5","nmin"},{"6","max"},{"7","nmax"}
							,{"8","minbd"},{"9","nminbd"},{"10","maxbd"},{"11","nmaxbd"}
							,{"12","unitid"},{"13","unitname"}
							,{"14","vocid"},{"15","vocname"},{"16","cntdecimal"}
							,{"17","idEnter"},{"18","value"},{"19","valueVoc"}
					} ;
					for(Object[] objects : lwqr) {
						StringBuilder par = new StringBuilder() ;
						par.append("{") ;
						boolean isFirtMethod = false ;
						boolean isError = false ;
						if (String.valueOf(objects[3]).equals("2")) {
							if (objects[14]==null) {
								isError = true ;
							}
						}
						try {
							for(String[] prop : props) {
								Object value = PropertyUtil.getPropertyValue(objects, prop[0]) ;
								String strValue = value!=null?(!value.toString().equals("null")?value.toString():""):"";
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
			//  log.info("==== JSSON= "+sb.toString());
			    saveLabAnalyzed(0L,pid,0L,sb.toString(),username, templateId) ;
				} catch(Exception e){
					LOG.error(e.getMessage(),e);
				} 
			}
			}

	//	log.debug("Finish setDefaultDiary");
		return sb.toString();
		
		} else {
			LOG.info("Штрих код пустой, либо это нет");
			return null;
		}
				
	}
	
	 private String createSQLQuery (ParsedPdfInfo parsedPdfInfo) {
	        StringBuilder s = new StringBuilder();
	        s.append("case");
	       List<ParsedPdfInfoResult> r = parsedPdfInfo.getResults(); 
	        for (ParsedPdfInfoResult p: r) {
	            s.append(" when p.externalcode='").append(p.getCode()).append("' then '").append(p.getValue()).append("' ");
	        }
	        s.append(" end");
	        return s.toString();
	    }
	 
	private String str(String aValue) {
    	if (aValue.contains("\"")) {
    		aValue = aValue.replaceAll("\"", "\\\\\"") ;
    	}
    	return aValue ;
    }

	public Long clonePrescription(Long aPrescriptionId, Long aMedServiceId, Long aWorkFunctionId, String aCreateUsername) {
		ServicePrescription p = theManager.find(ServicePrescription.class, aPrescriptionId);
		
		Long ret = null;
		if (p!=null) { //Дублируем лаб. назначение (для бак. лаборатории)
			WorkFunction workFunction = theManager.find(WorkFunction.class, aWorkFunctionId);
			MedService medService = theManager.find(MedService.class, aMedServiceId);
			long currentDate = new java.util.Date().getTime();
			ServicePrescription presOld = p;
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
			//проставляет отделение, чтобы в отчёте по чувствительности к антибиотикам всё разбивалось по отделениям, а не в лаборатории
			String sql = "select w.lpu_id from prescription p" +
					" left join workfunction wf on wf.id = p.intakespecial_id" +
					" left join worker w on w.id = wf.worker_id" +
					" where p.id=" + presOld.getId();
			List<Object> list = theManager.createNativeQuery(sql).getResultList() ;
			if (!list.isEmpty())
				presNew.setDepartment(theManager.find(MisLpu.class, Long.valueOf(list.get(0).toString())));
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
				if (mc instanceof HospitalMedCase) {
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
					LOG.error("==== Создание визита из назначения пошло не так. PL= "+aPrescriptionListId+" : "+aDatePlanId+" <> "+ wct.getWorkCalendarDay().getId());
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

	public String saveLabAnalyzed(Long aSmoId,Long aPrescriptId,Long aProtocolId, String aParams, String aUsername, Long aTemplateId) {
		try {
			StringBuilder infoToSend = new StringBuilder();
			Protocol d =null;
			JSONObject obj = new JSONObject(aParams) ;
			String wf = String.valueOf(obj.get("workFunction"));
			StringBuilder sql ;
			Visit m = theManager.find(Visit.class, aSmoId) ;

			if (m!=null) {
				List<Object> l = null;
				if (aProtocolId!=null && !aProtocolId.equals(0L)) {
					sql = new StringBuilder() ;
					sql.append("select id from Diary where id=").append(aProtocolId).append(" and medCase_id=").append(aSmoId) ;
					l = theManager.createNativeQuery(sql.toString()).getResultList() ;
				}
				if (l==null || l.isEmpty()) {
					sql = new StringBuilder() ;
					sql.append("select id from Diary where medCase_id=").append(aSmoId);
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
			JSONArray params = obj.getJSONArray("params");
			StringBuilder sb = new StringBuilder() ;
			//sb.append(ms).append("Забор биоматериала произведен: ").append(DateFormat.formatToDate(pres.getIntakeDate()));
			sb.append("Забор биоматериала произведен: ").append(pres.getIntakeDate()!=null? DateFormat.formatToDate(pres.getIntakeDate()) : "");
			sb.append(" ").append(pres.getIntakeTime()!=null? DateFormat.formatToTime(pres.getIntakeTime()) : "").append("\n") ;
			//sb.append("Дата передачи в лабораторию: ").append(DateFormat.formatToDate(pres.getTransferDate()));
			//sb.append(" ").append(DateFormat.formatToTime(pres.getTransferTime())).append("\n") ;
            NumberFormat numberFormat =new DecimalFormat("#.######");
			for (int i = 0; i < params.length(); i++) {
				//boolean isSave = true ;
				JSONObject param = (JSONObject) params.get(i);
				FormInputProtocol fip = new FormInputProtocol() ;
				fip.setDocProtocol(d) ;
				Parameter p = theManager.find(Parameter.class, ConvertSql.parseLong(param.get("id"))) ;
				fip.setParameter(p) ;
				fip.setPosition(i+1L) ;
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
						//Добавление отображения референтных интервалов
						Double val = ifDoubleReturn(value);
						String normaMin = param.getString("nmin");
						String normaMax = param.getString("nmax");
						Double min = ifDoubleReturn(normaMin);
						Double max = ifDoubleReturn(normaMax);
						if (val!=null && min!=null && max!=null ) { // если есть реф. интервал - пишем его в дневнике
							StringBuilder msg = new StringBuilder();
							msg.append(" (реф. инт: ").append(numberFormat.format(min)).append(" - ").append(numberFormat.format(max)).append(")");
							if (val<min || val>max) {
								msg.insert(0, val<min ? "▼" : "▲");
								String allmsg = param.get("name") + ": " +
										value + " " +
										param.get("unitname") + " " +
										msg;
								infoToSend.append(allmsg).append("<br>");
							}
							sb.append(msg);
						}
					}
					//пользовательский справочник
				} else if (type.equals("2")) {
					if (!value.equals("")){
						Long id = ConvertSql.parseLong(value) ;
						if (id!=null && !id.equals(0L)) {
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
						fip.setValueText(value) ;
						if (sb.length()>0) sb.append("\n") ;
						sb.append(param.get("name")).append(": ") ;
						sb.append(value).append(" ") ;
						sb.append(param.get("unitname")).append(" ") ;
					}
				}
				theManager.persist(fip) ;
			}
			//ФИО лабораторного техника
	/*		String fio = getRealLabTechUsername(aPrescriptId,aUsername);
			if (!fio.equals("")) {
				sb.append("\n");
				sb.append(fio);
			}*/
			d.setRecord(sb.toString()) ;
			theManager.persist(d) ;
			if (!wf.equals("0")) {
				m.setWorkFunctionExecute(theManager.find(WorkFunction.class, Long.valueOf(wf))) ;
			} else {
				m.setWorkFunctionExecute(m.getWorkFunctionPlan()) ;
				theManager.persist(m) ;
			}
			theManager.persist(m) ;
			/*if (!infoToSend.toString().isEmpty())
				sendMesgOutOfReferenceInterval(infoToSend.toString(),aPrescriptId);*/
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return "" ;
	}

	/**
	 * Получить число либо null, если невозможно преобразовать #177
	 * @param d String
	 * @return Double(d) или null
	 */
	private Double ifDoubleReturn(String d) {
			try {
				return Double.parseDouble(d);
			} catch (Exception e) {
				return null;
			}
	}

	/**
	 * Отправить сообщение лечащему врачу, что результат находится вне референсного интервала #177
	 * @param msg String
	 * @param aPrescriptId Long Prescription.id
	 */
	private void sendMesgOutOfReferenceInterval(String msg, Long aPrescriptId) {
		JSONObject obj = getOwnerfunctionUsernameAndExtraInfo(aPrescriptId);
		if (obj.has("username")) {
			CustomMessage mes = new CustomMessage() ;
			mes.setMessageTitle("Выход за границы референсного интервала в лаб. анализе") ;
			mes.setMessageText("Результат анализа пациента: " + obj.getString("patient") + ":<br>" + msg) ;
			mes.setUsername("system_message") ;
			long date = new java.util.Date().getTime() ;
			mes.setDispatchDate(new java.sql.Date(date)) ;
			mes.setDispatchTime(new Time(date)) ;
			mes.setRecipient(obj.getString("username")) ;
			mes.setMessageUrl("entityParentView-stac_slo.do?id=" + obj.get("dmcId"));
			mes.setIsEmergency(true) ;
			theManager.persist(mes) ;
		}
	}

	/**
	 * Получить имя пользователя лечащего врача (даже если выбрана другая раб. ф-я сейчас), инфо о пациенте и СЛО по назначению #177
	 * @param aPrescriptId Long Prescription.id
	 * @return JSONObject username
	 */
	private JSONObject getOwnerfunctionUsernameAndExtraInfo(Long aPrescriptId) {
		List<Object[]> loginList = theManager.createNativeQuery("select (select su.login  " +
				" from WorkFunction wfinner" +
				" left join Worker w on w.id=wfinner.worker_id" +
				" left join Worker sw on sw.person_id=w.person_id" +
				" left join WorkFunction swf on swf.worker_id=sw.id" +
				" left join vocworkfunction vwf on vwf.id=wfinner.workfunction_id" +
				" left join SecUser su on su.id=swf.secUser_id" +
				" where su.id is not null and wfinner.id=wf.id)" +
				" ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as patfio" +
				" ,dmc.id as dmcId" +
				" from medcase dmc" +
				" left join prescriptionlist pl on pl.medcase_id=dmc.id" +
				" left join prescription p on p.prescriptionlist_id=pl.id" +
				" left join workfunction wf on wf.id=dmc.ownerfunction_id" +
				" left join vocworkFunction vwf on vwf.id=wf.workFunction_id" +
				" left join SecUser su on wf.secUser_id=su.id" +
				" left join patient pat on pat.id=dmc.patient_id" +
				" where p.id=:aPrescriptId").setParameter("aPrescriptId",aPrescriptId).getResultList();
		JSONObject obj = new JSONObject();
		if (!loginList.isEmpty()) {
            Object[] oo = loginList.get(0);
				obj.put("username",oo[0])
						.put("patient",oo[1])
						.put("dmcId",oo[2]);
		}
		return obj;
	}
	public String getWorkfuntctionInfoByLabTechUsername(String aUsername) {
		List<Object> labExec = theManager.createNativeQuery("select vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename" +
				" from WorkFunction wf" +
				" left join SecUser su on wf.secUser_id=su.id" +
				" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id" +
				" left join Worker as w on w.id=wf.worker_id" +
				" left join Patient as p on p.id=w.person_id" +
				" where su.login=:login").setParameter("login",aUsername).getResultList();
		return !labExec.isEmpty() && labExec.get(0)!=null?
				String.valueOf(labExec.get(0)) : "";
	}

	//Если был брак, вернёт того, кто отбраковал.
	// Если это - бак. исследование - вернёт того, кто принял в лабораторию, иначе - того, кто выполнил
	public String getRealLabTechUsername(Long aPrescriptId,String aUsername) {
		List<Object> transferUsername = theManager.createNativeQuery("select case when p.cancelusername is not null then " +
				" p.cancelusername else case when msgr.code='Q06' then p.transferusername else null end end from medservice ms" +
				" left join medservice msgr on msgr.id=ms.parent_id" +
				" left join prescription p on p.medservice_id=ms.id" +
				" left join templateProtocol tp on tp.medservice_id=ms.id" +
				" where p.id=:aPrescriptId").setParameter("aPrescriptId",aPrescriptId).getResultList();
		if (!transferUsername.isEmpty() && transferUsername.get(0)!=null)
			aUsername=String.valueOf(transferUsername.get(0));
		return getWorkfuntctionInfoByLabTechUsername(aUsername);
	}

    /**
     * Отправить сообщение лечащему враче о выходе за граница реф. инт.
     *
     * @param aDiaryId Diary.id
     * @param aPrescriptId Prescription.id
     */
    public void sendEmergencyReferenceMsg(Long aDiaryId, Long aPrescriptId) {
        StringBuilder sql = new StringBuilder() ;
        sql.append("select p.name||': '||round(fip.valuebd,2)||' '||unit.name")
            .append("||' '||(case when fip.valuebd<prv.normamin then '▼' else '▲' end)||cast('<br>' as varchar)")
            .append(" from FormInputProtocol fip")
            .append(" left join parameter p on fip.parameter_id=p.id")
            .append(" left join diary d on d.id=fip.docprotocol_id")
            .append(" left join medcase vis on vis.id=d.medcase_id")
            .append(" left join patient pat on pat.id=vis.patient_id")
            .append(" left join parameterreferencevalue prv on prv.parameter_id=p.id and (prv.sex_id is null or prv.sex_id=pat.sex_id)")
            .append(" and cast(date_part('year',age(d.dateregistration, pat.birthday)) as int) between prv.ageFrom and prv.ageTo or (prv.ageFrom is null and prv.ageTo is null)")
            .append(" left join vocmeasureunit unit on unit.id=p.measureunit_id")
            .append(" where fip.valuebd is not null and (prv.normamin is not null and fip.valuebd<prv.normamin or prv.normamax is not null and fip.valuebd>prv.normamax)")
            .append(" and fip.docprotocol_id=").append(aDiaryId);
        List<Object> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
        for (int i=0; i<list.size(); i++) {
        	if (list.get(i)!=null) {
				String msg = list.get(i).toString();
				if (!msg.isEmpty())
					sendMesgOutOfReferenceInterval(msg, aPrescriptId);
			}
        }
    }

    public Long checkLabAnalyzed(Long aPrescriptId,Long aWorkFunctionId,String aUsername) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select pat.id as patid,case when slo.dtype='DepartmentMedCase' then sls.id") ; 
		sql.append(" when slo.dtype='Visit' then coalesce (sls.id,slo.id) else slo.id end as pmo") ;
		sql.append(" ,p.prescriptSpecial_id as presspec") ;
		sql.append(" ,p.prescriptCabinet_id as cabinet") ;
		sql.append(" ,p.medService_id as service") ;
		sql.append(" from prescription p ") ;
		sql.append(" left join PrescriptionList pl on pl.id=p.prescriptionlist_id left join medcase slo on slo.id=pl.medcase_id left join medcase sls on sls.id=slo.parent_id left join patient pat on pat.id=slo.patient_id where p.id=").append(aPrescriptId) ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		if (aWorkFunctionId == null) {
			List<Object> wf = theManager.createNativeQuery("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login=:login").setParameter("login",aUsername).getResultList() ;
			if ( wf.isEmpty()) {
				return null ;
			}
			aWorkFunctionId = ConvertSql.parseLong(wf.get(0)) ;
		}

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
		List<Object> wf = theManager.createNativeQuery("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login=:login").setParameter("login",aUsername).getResultList() ;
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
		String aKey;// = "";
		String matId ;// = null;
		HashMap<String, String> aLabMap =  new HashMap<>() ;
		for (String prescriptionId: prescriptionIds) {
			Long presId = Long.parseLong(prescriptionId.trim());
			ServicePrescription p = theManager.find(ServicePrescription.class, presId);
//			if (p!=null &&p.getMedService().getServiceType().getCode().equals("LABSURVEY")&&aDate!=null&&!aDate.equals("")) {
			if (p != null && !aDate.equals("")) {
				long aPatientId =p.getPrescriptionList().getMedCase().getPatient().getId();
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
	
	public static String getPatientDateNumber(Map aLabMap, String aKey, long aPatientId, String aDate, EntityManager aManager) {
		String matId = null;
		Map<String, String> labMap = aLabMap ;
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
			List<String> lPl = aManager.createNativeQuery(req).getResultList();
			
			if (!lPl.isEmpty()) {
				matId = ""+lPl.get(0) ;
			}  
			if (matId == null || matId.equals("")) {
				SequenceHelper seqHelper = SequenceHelper.getInstance() ;
				matId=seqHelper.startUseNextValueNoCheck("Prescription#Lab#"+aDate,"", aManager);
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
		StringBuilder description = new StringBuilder();
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
	 * @param aId - ИД листа назначения либо СМО
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
		if (!list.isEmpty()) {
			Object[] obj = list.get(0);
			
			java.util.Date date;
			try {
				date = sdf.parse(obj[0].toString());
				boolean check = ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SecPolicy.isDateLessThenHour(date,2);
				if (obj[1]!=null && check) { 
					isEmergency=true;
				}
			} catch (ParseException e) {
				LOG.error(e);
			}	
		}
		return isEmergency ;
	}
	/**
	 * Получение списка назначений из шаблона в добавление их в лист назначений. 
	 */
	
	public String getLabListFromTemplate(Long aIdTemplateList) {
		PrescriptListTemplate template = theManager.find(PrescriptListTemplate.class, aIdTemplateList);
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
		if (aPresc instanceof DrugPrescription) {
			try{
			DrugPrescription presNew = (DrugPrescription) aPresc;
			list.setLength(0);
			list.append("DRUG@");
			list.append(presNew.getDrug().getId()).append(":");
			//list.append(presNew.getDrug().getName()).append("::"); //: Date
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
			/*if (presNew.getDuration()!=null) {
				list.append(presNew.getDuration()).append(":");
				list.append(presNew.getDurationUnit().getId()).append(":");
				list.append(presNew.getDurationUnit().getName()).append("#");
			} else list.append("::#");*/
			
			return list.toString() ;
			}
			catch (Exception e) {
				LOG.error("catch Drug ",e);
			}
		} else if (aPresc instanceof DietPrescription) {
			try{
				DietPrescription presNew = (DietPrescription) aPresc;
				list.setLength(0);
				list.append("DIET@");
				list.append(presNew.getDiet()!=null ? presNew.getDiet().getId() : "").append(":") ;
				list.append(presNew.getDiet()!=null ? presNew.getDiet().getName() : "").append("#") ;
				return list.toString();
			}
			catch (Exception e) {
				LOG.error("catch DIET ",e);
			}
		} else if (aPresc instanceof ServicePrescription) {
			try {
				ServicePrescription presNew = (ServicePrescription) aPresc;
				if (presNew.getMedService().getFinishDate()!=null) {
				//	log.error("Услуга "+presNew.getMedService().getName()+" запрещена к назначению (закрыта)");
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
				LOG.error("catch Service ",e);
			}
		} else if (aPresc instanceof ModePrescription)  {
			ModePrescription prescNew = (ModePrescription) aPresc;
			list.setLength(0);
			list.append("MODE@");
			list.append(prescNew.getModePrescription().getName()).append(":");
			list.append(prescNew.getModePrescription().getId()).append("#");
			return list.toString();
		} 
		LOG.error("_----------------Some shit happens!!!"+aPresc);
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
		MedCase medCase = aIdParent!=null ? theManager.find(MedCase.class, aIdParent) : null;
		WorkFunction wf = WorkerServiceBean.getWorkFunction(theContext, theManager) ;

		AbstractPrescriptionList list  ;
		if (medCase!=null) {
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
			list =  new PrescriptListTemplate() ;
			list.setCreateDate(new java.sql.Date(new Date().getTime())) ;
			list.setCreateUsername(theContext.getCallerPrincipal().toString());
			if (aName!=null&&!aName.equals("")) {
				list.setName(aName) ;
			} else {
				list.setName(template.getName()) ;
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
			if (listNew==null) listNew =new ArrayList<>() ;
			for (Prescription presc:aTemplate.getPrescriptions()) {
				Prescription prescNew = newPrescriptionOnTemplate(presc, aSpecialist);

				prescNew.setPrescriptionList(aList) ;
				listNew.add(prescNew);
				theManager.flush() ;
			}	
			aList.setPrescriptions(listNew) ;
			theManager.persist(aList) ;
		}
	}
	
	private String getDescrPerscriptions(PrescriptListTemplate aTemplate) {
		StringBuilder desc = new StringBuilder() ;
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
			//presNew.setDuration(presOld.getDuration());
			//presNew.setDurationUnit(presOld.getDurationUnit());
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
		throw new IllegalStateException("Невозможно создать шаблон назначения по текущему назначению, неизвестен тип назначения!");
		//return null ;
}

		
		@EJB ILocalEntityFormService 
		theEntityFormService ;
	    @PersistenceContext
	    EntityManager theManager ;
	    @Resource
		SessionContext theContext ;

	}	



