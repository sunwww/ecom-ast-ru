package ru.ecom.mis.web.webservice;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.tempuri.WS_MES_SERVER.wsdl.WSLocator;
import org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapPort;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.patient.PatientFond;
import ru.ecom.mis.ejb.domain.patient.PatientFondCheckData;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class FondWebService {
	private static String theAddress = "vipnet" ;
	private static String theLpu = "1" ;

	private static Object getInfoByPatientResult(HttpServletRequest aRequest, PatientForm aPatFrm, WS_MES_SERVERSoapPort soap, String result , Long patId) throws Exception {
		InputStream in = new ByteArrayInputStream(result.getBytes());
		String rz = new SAXBuilder().build(in).getRootElement().getChild("cur1").getChildText("rz") ;
		in.close();
		return getInfoByPatient(aRequest, aPatFrm,soap,rz,patId);
	}
	public static Object checkPatientBySnils(HttpServletRequest aRequest, PatientForm aPatFrm, String aSnils, Long patId)   throws Exception{
		WSLocator service = new WSLocator() ;
        service.setWS_MES_SERVERSoapPortEndpointAddress("http://"+theAddress+"/ws/WS.WSDL");
        WS_MES_SERVERSoapPort soap = service.getWS_MES_SERVERSoapPort();
        return getInfoByPatientResult(aRequest, aPatFrm,soap,(String)soap.get_RZ_from_SS(aSnils, theLpu),patId);
		
    }
	public static Object checkPatientByMedPolicy(HttpServletRequest aRequest, PatientForm aPatFrm, String aSeries, String aNumber, Long patId)   throws Exception{
		WSLocator service = new WSLocator() ;
        service.setWS_MES_SERVERSoapPortEndpointAddress("http://"+theAddress+"/ws/WS.WSDL");
        WS_MES_SERVERSoapPort soap = service.getWS_MES_SERVERSoapPort();
        return getInfoByPatientResult(aRequest, aPatFrm,soap,(String)soap.get_RZ_from_POLIS(aSeries, aNumber, theLpu),patId);
    }

	public static Object checkPatientByFioDr(HttpServletRequest aRequest, PatientForm aPatFrm, String aLastname,String aFirstname
			,String aMiddlename, String aBirthday, Long patId) throws Exception  {
		WSLocator service = new WSLocator() ;
        service.setWS_MES_SERVERSoapPortEndpointAddress("http://"+theAddress+"/ws/WS.WSDL");
        WS_MES_SERVERSoapPort soap = service.getWS_MES_SERVERSoapPort();
        return getInfoByPatientResult(aRequest, aPatFrm,soap,(String)soap.get_RZ_from_FIODR(aLastname, aFirstname
				, aMiddlename, aBirthday, theLpu),patId);
    }
	public static Object checkPatientByDocument(HttpServletRequest aRequest, PatientForm aPatFrm, String aType, String aSeries,String aNumber, Long patId) throws Exception  {
		WSLocator service = new WSLocator() ;
		service.setWS_MES_SERVERSoapPortEndpointAddress("http://"+theAddress+"/ws/WS.WSDL");
		WS_MES_SERVERSoapPort soap = service.getWS_MES_SERVERSoapPort();
		return getInfoByPatientResult(aRequest, aPatFrm,soap,(String)soap.get_RZ_from_DOCS(aType, aSeries, aNumber, theLpu), patId);
	}
	public static Object checkPatientByCommonNumber(HttpServletRequest aRequest, PatientForm aPatFrm, String aCommonNumber, Long patId) throws Exception  {
		WSLocator service = new WSLocator() ;
        service.setWS_MES_SERVERSoapPortEndpointAddress("http://"+theAddress+"/ws/WS.WSDL");
        WS_MES_SERVERSoapPort soap = service.getWS_MES_SERVERSoapPort();
        return getInfoByPatient(aRequest, aPatFrm,soap,aCommonNumber, patId);
    }

	public static String checkAllPatientsByFond(String updPatient, String updDocument, String updPolicy, String updAttachment, String aType, String aPatientList, HttpServletRequest aRequest) {
		// Создаем список пациентов
		if (aType==null) {return "";}
        StringBuilder patSql = new StringBuilder().append("select p.id, p.lastname, p.firstname, p.middlename, p.birthday ");
		if ("1".equals(aType)) {
			patSql.append(" from patient p" +
					" where (p.noactuality is null or p.noactuality='0') and p.deathdate is null");
		} else if ("2".equals(aType)) {
			patSql.append("from lpuattachedbydepartment att " +
					" left join patient p on p.id=att.patient_id" +
					" where att.dateto is null and (p.noactuality is null or p.noactuality='0') and p.deathdate is null");
		} else if ("3".equals(aType)) {
			patSql.append(" from patient p where p.id in (").append(aPatientList).append(") and (p.noactuality is null or p.noactuality='0') and p.deathdate is null");
		}
		boolean updatePatient, updatePolicy, updateDocument , updateAttachment;
		updatePatient = updPatient!=null&&(updPatient.equals("1")||updPatient.equalsIgnoreCase("true")||updPatient.equalsIgnoreCase("on"));
		updateDocument = updDocument!=null&&(updDocument.equals("1")||updDocument.equalsIgnoreCase("true")||updDocument.equalsIgnoreCase("on"));
		updatePolicy = updPolicy!=null&&(updPolicy.equals("1")||updPolicy.equalsIgnoreCase("true")||updPolicy.equalsIgnoreCase("on"));
		updateAttachment = updAttachment!=null&&(updAttachment.equals("1")||updAttachment.equalsIgnoreCase("true")||updAttachment.equalsIgnoreCase("on"));
		try {
			String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
			IWebQueryService serviceWQS = Injection.find(aRequest).getService(IWebQueryService.class) ;
			IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
			Collection<WebQueryResult> pats = serviceWQS.executeNativeSql(patSql.toString());
			if (!pats.isEmpty()) {
				PatientFondCheckData pfc = service.getNewPFCheckData(updatePatient, updateDocument, updatePolicy, updateAttachment);
				if ("2".equals(aType)) {
					pfc.setComment(pfc.getComment()+" Проверка только прикрепленного населения");
				} else if ("1".equals(aType)) {
					pfc.setComment(pfc.getComment()+" Проверка всей базы пациентов");
				} else if ("3".equals(aType)) {
					pfc.setComment(pfc.getComment()+" Выборочная проверка пациентов");
				}
				int i=0;
				WSLocator serviceLocator = new WSLocator() ;
				serviceLocator.setWS_MES_SERVERSoapPortEndpointAddress("http://"+theAddress+"/ws/WS.WSDL");
				WS_MES_SERVERSoapPort aSoap = serviceLocator.getWS_MES_SERVERSoapPort();
				for (WebQueryResult pat: pats) {
					i++;
					String pid = pat.get1().toString(); String lastname = pat.get2().toString(); String firstname = pat.get3().toString();
					String middlename = pat.get4().toString(); String birthday = pat.get5().toString();
					String resultRZ;
					resultRZ = (String)aSoap.get_RZ_from_FIODR(lastname, firstname
							, middlename, birthday, theLpu);
					String aRz = "";
					InputStream in = new ByteArrayInputStream((resultRZ).getBytes());
					Document doc = new SAXBuilder().build(in);
					Element root;
					Element cur1;
					Element rze;
					try {
						root = doc.getRootElement();
						cur1 = root.getChild("cur1") ;
						rze = cur1.getChild("rz") ;
						aRz = rze.getText() ;
					} catch (NullPointerException ignored) {

					}
					in.close() ;
					if (!aRz.equals("")) {
						String result = updateXml((String)aSoap.get_FIODR_from_RZ(aRz, theLpu)) ;

						String snils = null;
						String attachedDate = null; String attachedType = null; String attachedLpu = null;
						String doctorSnils=null; String codeDepartment=null;
						String dateDeath = null;
						in = new ByteArrayInputStream(result.getBytes());
						doc = new SAXBuilder().build(in);
						root = doc.getRootElement();
						@SuppressWarnings("unchecked")
						List<Element> list_cur = root.getChildren("cur1");
						for (Element e:list_cur) {
							//F:I:O:DR:RZ:DEAD:SNILS:DATEPRIK:SP_PRIK:LPU:SSD:KODPODR
							lastname = e.getChildText("f"); firstname = e.getChildText("i"); middlename =e.getChildText("o");
							birthday = upDate(e.getChildText("dr")); snils=e.getChildText("ss");attachedDate = upDate(e.getChildText("date_prik"));
							attachedType=e.getChildText("sp_prik");attachedLpu=e.getChildText("lpu");
							doctorSnils=e.getChildText("ssd"); codeDepartment=e.getChildText("kodpodr");
							dateDeath = e.getChildText("datedead");
						}

						in.close() ;
						result = updateXml((String)aSoap.get_POLIS_from_RZ(aRz, theLpu)) ;
						in = new ByteArrayInputStream(result.getBytes());
						doc = new SAXBuilder().build(in);
						root = doc.getRootElement();
						list_cur = root.getChildren("cur1");
						String companyCode = null, policySeries = null, policyNumber = null, policyDateFrom = null, policyDateTo = null;
						try {
							for (Element el:list_cur) {
								//System.out.println(result);
								String[] pol = el.getChildText("sn_pol").split(" ") ;
								String serPol;
								String numPol;
								String sk = el.getChildText("sk") ;
								String dpp = upDate(el.getChildText("datapp")) ;
								String dpe = upDate(el.getChildText("datape")) ;
								String ddosr = upDate(el.getChildText("d_dosrochno")) ;
								if (pol.length>2) {
									serPol = pol[0]+" "+pol[1] ;
									numPol = pol[2] ;
								} else if (pol.length>1){
									serPol = pol[0] ;
									numPol = pol[1] ;
								} else {
									if (pol[0].length()>10) {
										serPol = pol[0].substring(0,2)+" "+pol[0].substring(2,4) ;
										numPol = pol[0].substring(4) ;
									} else {
										serPol = pol[0].substring(0,3) ;
										numPol = pol[0].substring(3) ;
									}
								}

								if ("1".equals(el.getChildText("pz_actual"))) {
									companyCode=sk; policySeries=serPol;policyNumber=numPol;policyDateFrom=dpp; policyDateTo= ddosr;
								}
							}
							in.close() ;
						} catch(Exception e) {
							e.printStackTrace() ;
						}
						result = updateXml((String)aSoap.get_DOCS_from_RZ(aRz, theLpu)) ;
						in = new ByteArrayInputStream(result.getBytes());
						doc = new SAXBuilder().build(in);
						root = doc.getRootElement();
						list_cur.clear() ;

						list_cur = root.getChildren("cur1");
						String documentType = null, documentSeries = null, documentNumber = null ;
						String documentDateIssued = null; String documentWhomIssued = null;
						Date maxDate = null;
						for (Element el:list_cur) {
							Date currentDocDate = DateFormat.parseDate(el.getChildText("doc_d"), "yyyy-MM-dd");
							if (maxDate==null||currentDocDate.getTime()>maxDate.getTime()) {
								maxDate = currentDocDate;
								documentType=el.getChildText("doc_t"); documentSeries=el.getChildText("doc_s"); documentNumber=el.getChildText("doc_n") ;
								documentDateIssued=upDate(el.getChildText("doc_d")); documentWhomIssued=el.getChildText("doc_v");

							}
						}
						in.close() ;

						result = updateXml((String)aSoap.get_ADRES_from_RZ(aRz, theLpu) ) ;
						in = new ByteArrayInputStream(result.getBytes());
						doc = new SAXBuilder().build(in);
						root = doc.getRootElement();
						list_cur = root.getChildren("cur1");
						String kladr = null, house = null, houseBuilding = null, flat = null , okato = null, street = null;
						for (Element el:list_cur) {
							String hn = el.getChildText("house") ;
							String hb = el.getChildText("section") ;
							String fn = el.getChildText("apartment") ;
							String kl = el.getChildText("street_gni") ;
							street = el.getChildText("street");
							okato = el.getChildText("region");
							kladr=kl; house=hn; houseBuilding=hb; flat =fn;
							break;

						}
						in.close() ;
						try {
							long patFondId = service.insertCheckFondData(lastname, firstname, middlename, birthday, snils
									, aRz
									, policySeries, policyNumber, policyDateFrom, policyDateTo
									, username, PatientFond.STATUS_CHECK_TYPE_AUTOMATIC
									, companyCode, "", "", "", pid
									, documentType, documentSeries, documentNumber
									, kladr, house, houseBuilding, flat, attachedLpu, attachedDate, attachedType, dateDeath
									, documentDateIssued, documentWhomIssued, doctorSnils, codeDepartment, pfc, street, okato);
							service.updateDataByFondAutomatic(patFondId, pfc.getId(), updatePatient, updateDocument
									,updatePolicy, updateAttachment);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						try {
							service.insertPatientNotFound(Long.valueOf(pid), pfc.getId());
						} catch (Exception ignored) {

						}
					}
				}
				pfc.setFinishDate(new java.sql.Date(System.currentTimeMillis()));
				return "Проверено "+i+" записей";
			}
			return "нет данных" ;
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
	}
	private static String getInfoByPatient(HttpServletRequest aRequest, PatientForm aPatFrm, WS_MES_SERVERSoapPort aSoap,String aRz, Long patId) throws JDOMException, IOException, NamingException, ParseException {
		if (!aRz.equals("")) {

			IWebQueryService serviceWQS = Injection.find(aRequest).getService(IWebQueryService.class) ;
			String defaultLpu =null;
			Collection<WebQueryResult> list = serviceWQS.executeNativeSql("select sc.keyvalue from SoftConfig sc where sc.key='DEFAULT_LPU_OMCCODE'") ;
			if (!list.isEmpty()) {
				defaultLpu = list.iterator().next().get1().toString();
			}
			
			StringBuilder sb = new StringBuilder() ;
        	String result =  updateXml((String)aSoap.get_FIODR_from_RZ(aRz, theLpu)) ;
        	IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
    		
        	String lastname = null, firstname = null, middlename = null, birthday = null, snils = null;
        	String attachedDate = null; String attachedType = null; String attachedLpu = null;
        	String doctorSnils=null; String codeDepartment=null;
        	String dateDeath = null;
			String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
        	InputStream in = new ByteArrayInputStream(result.getBytes());
        	Document doc = new SAXBuilder().build(in);
        	Element root = doc.getRootElement();
        	@SuppressWarnings("unchecked")
        	List<Element> list_cur = root.getChildren("cur1");
            sb.append("<h2>Информация о пациенте</h2><table border=1 width=100%>") ;
            sb.append("<tr>");
            sb.append("<th></th>");
            sb.append("<th>").append("RZ").append("</th>") ;
            sb.append("<th>").append("Фамилия").append("</th>") ;
            sb.append("<th>").append("Имя").append("</th>") ;
            sb.append("<th>").append("Отчество").append("</th>") ;
            sb.append("<th>").append("Год рождения").append("</th>") ;
            sb.append("<th>").append("СНИЛС").append("</th>") ;
            sb.append("<th>").append("Умер?").append("</th>") ;
            sb.append("<th>").append("Дата смерти").append("</th>") ;
            sb.append("<th>").append("ЛПУ").append("</th>") ;
            sb.append("<th>").append("Тип прикрепления").append("</th>") ;
            sb.append("<th>").append("Дата прикрепления").append("</th>") ;
            sb.append("</tr>") ;
            if (aPatFrm!=null&&aPatFrm.getSnils()==null) aPatFrm.setSnils("") ;
            boolean isStart = true ;
            for (Element e:list_cur) {

            	sb.append("<tr>") ;
            	String f = e.getChildText("f") ;
            	String i = e.getChildText("i") ;
            	String o = e.getChildText("o") ;
            	String dr = upDate(e.getChildText("dr")) ;
            	 dateDeath=upDate(e.getChildText("datadead")) ;
            	String ss =e.getChildText("ss") ;
            	String attDate = upDate(e.getChildText("date_prik"));
            	String attType = e.getChildText("sp_prik");
            	String attLpu = e.getChildText("lpu");
            	doctorSnils = e.getChildText("ssd");
            	sb.append("<td>").append("<input  onclick=\"patientcheck('patient')\" type='radio'")
            	.append(isStart?" checked='true'":"") 
            	.append(" name='fondFiodr' id='fondFiodr' value='")
    			.append(f).append("#").append(i).append("#")
    			.append(o).append("#").append(dr).append("#")
    			.append(ss!=null&&!ss.toLowerCase().trim().equals("null")?ss:"").append("#").append(aRz).append("#")
    			.append(dateDeath).append("#")
    			.append(attLpu).append("#").append(attType).append("#").append(attDate).append("#").append(doctorSnils)
    			.append("'/>").append("</td>") ;
           	
            	sb.append("<td").append(">").append(aRz).append("</td>") ;
            	sb.append("<td").append(aPatFrm!=null?(aPatFrm.getLastname().equals(f)?"":" bgcolor='yellow'"):"").append(">").append(f).append("</td>") ;
            	sb.append("<td").append(aPatFrm!=null?(aPatFrm.getFirstname().equals(i)?"":" bgcolor='yellow'"):"").append(">").append(i).append("</td>") ;
            	sb.append("<td").append(aPatFrm!=null?(aPatFrm.getMiddlename().equals(o!=null?o:"")?"":" bgcolor='yellow'"):"").append(">").append(o).append("</td>") ;
            	sb.append("<td").append(aPatFrm!=null?(aPatFrm.getBirthday().equals(dr)?"":" bgcolor='yellow'"):"").append(">").append(dr).append("</td>") ;
            	sb.append("<td").append(aPatFrm!=null?(aPatFrm.getSnils().equals(ss!=null&&!ss.toLowerCase().trim().equals("null")?ss:"")?"":" bgcolor='yellow'"):"").append(">").append(ss!=null&&!ss.toLowerCase().trim().equals("null")?ss:"").append("</td>") ;
            	sb.append("<td").append(">").append(e.getChildText("_dead")).append("</td>") ;
            	sb.append("<td").append(">").append(dateDeath).append("</td>") ;
            	sb.append("<td").append(defaultLpu!=null&&(!defaultLpu.equals(attLpu))?" bgcolor='yellow'":"").append(">").append(attLpu).append("</td>") ;
            	sb.append("<td").append(">").append(attType).append("</td>") ;
            	sb.append("<td").append(">").append(attDate).append("</td>") ;
             	sb.append("</tr>") ;
            	if (isStart) {
            		isStart=false ;
            		lastname = f ; firstname = i ; middlename = o; birthday = dr ;snils=ss;
            		doctorSnils=e.getChildText("ssd"); codeDepartment=e.getChildText("kodpodr");
            		attachedLpu = attLpu; attachedDate = attDate; attachedType = attType;
            	}
            }
            sb.append("</table>") ;
            
        	in.close() ;
        	result = (String)aSoap.get_POLIS_from_RZ(aRz, theLpu) ;
        	
        	result = updateXml(result) ;
        	in = new ByteArrayInputStream(result.getBytes());
        	doc = new SAXBuilder().build(in);
        	root = doc.getRootElement();
        	list_cur.clear() ;
        	list_cur = root.getChildren("cur1");
        	String companyCode = null, policySeries = null, policyNumber = null, policyDateFrom = null, policyDateTo = null;
        	sb.append("<h2>Список полисов</h2><table border=1 width=100%>") ;
        	sb.append("<tr>");
        	sb.append("<th>").append("</th>") ;
        	sb.append("<th>").append("</th>") ;
        	sb.append("<th>").append("СК").append("</th>") ;
        	sb.append("<th>").append("Серия").append("</th>") ;
        	sb.append("<th>").append("Номер").append("</th>") ;
        	sb.append("<th>").append("Дата выдачи").append("</th>") ;
        	sb.append("<th>").append("Дата начала").append("</th>") ;
        	sb.append("<th>").append("Дата оконч. посл. продления").append("</th>") ;
        	sb.append("<th>").append("Дата оконч. действия (досрочно)").append("</th>") ;
        	sb.append("</tr>") ;
        	try {
        	for (Element el:list_cur) {
        		sb.append("<tr>") ;
        		
        		String[] pol = el.getChildText("sn_pol").split(" ") ;
        		String serPol;
        		String numPol;
        		String sk = el.getChildText("sk") ;
        		String dpp = upDate(el.getChildText("datapp")) ;
        		String dpe = upDate(el.getChildText("datape")) ;
        		String ddosr = upDate(el.getChildText("d_dosrochno")) ;
        		if (pol.length>2) {
        			serPol = pol[0]+" "+pol[1] ;
        			numPol = pol[2] ;
        		} else if (pol.length>1){
        			serPol = pol[0] ;
        			numPol = pol[1] ;
        		} else {
        			if (pol[0].length()>10) {
        				serPol = pol[0].substring(0,2)+" "+pol[0].substring(2,4) ;
        				numPol = pol[0].substring(4) ;
        			} else {
        				serPol = pol[0].substring(0,3) ;
        				numPol = pol[0].substring(3) ;
        			}
        		}
        		String current = el.getChildText("pz_actual") ;
        		String ac = "" ;

            	sb.append("<td>").append("<input type='checkbox'  onclick=\"patientcheck('policy')\" name='fondPolicy' id='fondPolicy' ");
            	
            	if (current.equals("1")) {
            		String datEnd = ddosr ;
            		companyCode=sk; policySeries=serPol;policyNumber=numPol;policyDateFrom=dpp; policyDateTo=datEnd;
                	
            		if (!datEnd.equals("")) {
            			try {
            				java.sql.Date dat = DateFormat.parseSqlDate(datEnd) ;
            				java.util.Date curDat = new java.util.Date() ;
            				if (curDat.getTime()>dat.getTime()) {
            					datEnd="31.12.2014" ;
            				}
            			} catch(Exception e) {
            				datEnd = "" ;
            			}
            			
            		}
            		sb.append(" checked='true' value='").append(sk).append("#")
            		.append(serPol).append("#").append(numPol).append("#")
            		.append(dpp).append("#").append(datEnd).append("#") ;
            		ac = " bgColor='#CFC'  ";
            		
            	} else {
            		sb.append(" value='").append(sk).append("#")
            		.append(serPol).append("#").append(numPol).append("#")
            		.append(dpp).append("#").append(!ddosr.equals("") ? ddosr : dpe).append("#") ;
            	}
    			sb.append(aRz).append("#").append(current).append("#")
    				.append("'/>").append("</td>") ;
        		sb.append("<td").append(ac).append(">").append(el.getChildText("pz_actual")).append("</td>");
        		sb.append("<td").append(ac).append(">").append(sk).append("</td>");
        		sb.append("<td").append(ac).append(">").append(serPol).append("</td>");
        		sb.append("<td").append(ac).append(">").append(numPol).append("</td>");
        		sb.append("<td").append(ac).append(">").append(upDate(el.getChildText("datap"))).append("</td>");
        		sb.append("<td").append(ac).append(">").append(dpp).append("</td>");
        		sb.append("<td").append(ac).append(">").append(dpe).append("</td>");
        		sb.append("<td").append(ac).append(">").append(ddosr).append("</td>");
        		sb.append("</tr>") ;
        	}
        	sb.append("</table>") ;
        	in.close() ;
        	}catch(Exception e) {
        		e.printStackTrace() ;
        	}
        	result = updateXml((String)aSoap.get_DOCS_from_RZ(aRz, theLpu)) ;
        	in = new ByteArrayInputStream(result.getBytes());
            doc = new SAXBuilder().build(in);
            root = doc.getRootElement();

			list_cur = root.getChildren("cur1");
			String documentType = null, documentSeries = null, documentNumber = null ;
			isStart=true ;
            sb.append("<h2>Список документов</h2><table border=1 width=100%>") ;
            sb.append("<tr>");
            sb.append("<th>").append("</th>") ;
            sb.append("<th>").append("Тип").append("</th>") ;
            sb.append("<th>").append("Серия").append("</th>") ;
            sb.append("<th>").append("Номер").append("</th>") ;
            sb.append("<th>").append("Дата выдачи").append("</th>") ;
            sb.append("<th>").append("Кем выдан").append("</th>") ;
            sb.append("</tr>") ;
            for (Element el:list_cur) {
            	sb.append("<tr>") ;
        		String ac = "" ;
        		String dt = el.getChildText("doc_t");
        		String ds=el.getChildText("doc_s") ;
        		String dn=el.getChildText("doc_n") ;
        		String dd = upDate(el.getChildText("doc_d")) ;
        		String dv = el.getChildText("doc_v") ;

        		sb.append("<td>").append("<input type='radio' ")
            	.append("name='fondDocument' id='fondDocument' value='")
    			.append(dt).append("#").append(ds).append("#")
    			.append(dn).append("#").append(dd).append("#")
    			.append(dv).append("#").append(aRz).append("#")
    			.append("' onclick=\"patientcheck('document')\"/>").append("</td>") ;
        		sb.append("<td").append(ac).append(">").append(dt).append("</td>");
        		sb.append("<td").append(ac).append(">").append(ds).append("</td>");
        		sb.append("<td").append(ac).append(">").append(dn).append("</td>");
        		sb.append("<td").append(ac).append(">").append(dd).append("</td>");
        		sb.append("<td").append(ac).append(">").append(dv).append("</td>");
            	sb.append("</tr>") ;
        		if (isStart) {
        			documentType=dt; documentSeries=ds; documentNumber=dn ;
        			isStart=false ;
        		}
            }
            sb.append("</table>") ;
            in.close() ;
            
        	result = updateXml((String)aSoap.get_ADRES_from_RZ(aRz, theLpu) ) ;
        	in = new ByteArrayInputStream(result.getBytes());
            doc = new SAXBuilder().build(in);
            root = doc.getRootElement();
			list_cur = root.getChildren("cur1");
            sb.append("<h2>Список адресов</h2><table border=1 width=100%>") ;
            String kladr = null, house = null, houseBuilding = null, flat = null ;
            isStart=true ;
            sb.append("<tr>") ;
            sb.append("<th></th>") ;
            sb.append("<th>").append("КЛАДР").append("</th>") ;
            sb.append("<th>").append("Индекс").append("</th>") ;
            sb.append("<th>").append("Регион/ОКАТО").append("</th>") ;
            sb.append("<th>").append("Район").append("</th>") ;
            sb.append("<th>").append("Город").append("</th>") ;
            sb.append("<th>").append("Улица").append("</th>") ;
            sb.append("<th>").append("Дом").append("</th>") ;
            sb.append("<th>").append("Корп").append("</th>") ;
            sb.append("<th>").append("Кв").append("</th>") ;
            sb.append("</tr>") ;
            for (Element el:list_cur) {
            	sb.append("<tr>") ;
        		String ac = "" ;
        		String hn = el.getChildText("house").replace('\\','/') ;
        		String hb = el.getChildText("section").replace('\\','/') ;
        		String fn = el.getChildText("apartment").replace('\\','/') ;
        		String kl = el.getChildText("street_gni") ;
        		String r = el.getChildText("rayon") ; 
        		String sity = el.getChildText("sity").replace('\\','/') ;
        		String street = el.getChildText("street").replace('\\','/');
        		String streetT = el.getChildText("street_t").replace('\\','/');
        		String provance = el.getChildText("province") ;
        		String index = el.getChildText("ssity") ;
        		String okato = el.getChildText("region");
        		sb.append("<td>").append("<input  onclick=\"patientcheck('address')\" type='radio' ")
            	.append("name='fondAdr' id='fondAdr' value='")
        			.append(kl).append("#").append(hn).append("#")
        			.append(hb).append("#").append(fn).append("#").append(r).append("#")
        			.append(sity).append("#").append(street).append("#").append(streetT).append("#")
        			.append(provance).append("#")
        			.append(okato).append("#")
        			.append("'/>").append("</td>") ;
        		sb.append("<td").append(aPatFrm!=null?(aPatFrm.getAddressInfo().equals(kl)?"":" bgcolor='yellow'"):"").append(">").append(kl).append("</td>");
        		sb.append("<td").append(ac).append(">").append(index).append("</td>");
        		sb.append("<td").append(ac).append(">").append(provance).append((okato!=null&&!okato.equals(""))?"/"+okato:"").append("</td>");
        		sb.append("<td").append(ac).append(">").append(r).append("</td>");
        		sb.append("<td").append(ac).append(">").append(sity).append("</td>");
        		sb.append("<td").append(ac).append(">").append(streetT).append(" ")
        		.append(street).append("</td>");
        		sb.append("<td").append(aPatFrm!=null?(aPatFrm.getHouseNumber().equals(hn)?"":" bgcolor='yellow'"):"").append(">").append(hn).append("</td>");
        		sb.append("<td").append(aPatFrm!=null?(aPatFrm.getHouseBuilding().equals(hb)?"":" bgcolor='yellow'"):"").append(">").append(hb).append("</td>");
        		sb.append("<td").append(aPatFrm!=null?(aPatFrm.getFlatNumber().equals(fn)?"":" bgcolor='yellow'"):"").append(">").append(fn).append("</td>");
            	sb.append("</tr>") ;
            	if (isStart) {
        			kladr=kl; house=hn; houseBuilding=hb; flat =fn;
        			isStart=false ;
        		}
            }
            sb.append("</table>") ; 
            in.close() ;
            service.insertCheckFondData(lastname, firstname, middlename, birthday, snils
            		, aRz
            		, policySeries, policyNumber, policyDateFrom, policyDateTo
            		, username, PatientFond.STATUS_CHECK_TYPE_MANUAL 
            		, companyCode, "", "", "",String.valueOf(patId)
            		, documentType, documentSeries, documentNumber
            		, kladr, house, houseBuilding, flat, attachedLpu, attachedDate, attachedType, dateDeath,"","",doctorSnils,codeDepartment
            		,null,null,null);
            return sb.toString() ;
        }
		
		return "нет данных" ;
	}
	private static String upDate(String aDate) {
		if (aDate!=null && !aDate.equals("")) {
			String[] drs = aDate.substring(0,10).split("-") ;
			return drs[2]+"."+drs[1]+"."+drs[0] ;
		} else {
			return "" ;
		}
	}
	private static String updateXml(String result) {
		int ind = result.indexOf("?>") ;
    	result = result.substring(ind+2) ;
    	return result ;
	}

}
