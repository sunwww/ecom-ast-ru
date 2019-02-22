package ru.ecom.diary.ejb.service.template;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.diary.ejb.domain.DischargeEpicrisis;
import ru.ecom.diary.ejb.domain.protocol.parameter.FormInputProtocol;
import ru.ecom.diary.ejb.domain.protocol.parameter.Parameter;
import ru.ecom.diary.ejb.domain.protocol.parameter.user.UserValue;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.domain.licence.DischargeDocument;
import ru.ecom.mis.ejb.domain.medcase.*;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.PatientExternalServiceAccount;
import ru.ecom.mis.ejb.domain.prescription.AdminChangeJournal;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.HospitalMedCaseViewInterceptor;
import ru.ecom.poly.ejb.domain.protocol.Protocol;
import ru.nuzmsh.util.StringUtil;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 22.12.2006
 * Time: 12:26:02
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@Remote(ITemplateProtocolService.class)
public class TemplateProtocolServiceBean implements ITemplateProtocolService {
	private static final Logger LOG = Logger.getLogger(TemplateProtocolServiceBean.class);

	@Deprecated
	public void sendSms(String aPhone, String aMessage)  {
		LOG.warn("Кто-то пытался послать СМС: "+aPhone+" : "+aMessage );
		/*try {
			JSONObject json = new JSONObject();
			json.put("phonenumber", aPhone);
			json.put("message", aMessage);
			EjbEcomConfig config = EjbEcomConfig.getInstance();
			String address = config.get("ru.amokb.patientcabinetaddress", null);
			makePOSTRequest(json.toString(), address, "SendSms", null, null, null);
			LOG.debug("message sent");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Ошибка при отправке СМС "+aMessage);
		}*/
	}
	public String makePOSTRequest (String data, String address,String aMethod, Map<String,String> params, Long aObjectId , EntityManager aManager) {
		//LOG.info("create connection, address = "+address+",method = "+aMethod+" , data="+data);
		try {
			if (address==null) {
				return "";
			}
			HttpURLConnection connection = null;
			try {
				URL url = new URL(address+"/"+aMethod);
				connection = (HttpURLConnection) url.openConnection();
				if (params!=null&&!params.isEmpty()) {
					for (Map.Entry<String,String> par: params.entrySet()) {
					//	LOG.info("send HTTP request. Key = "+par.getKey()+"<< value = "+par.getValue());
						connection.setRequestProperty(par.getKey(),par.getValue());
					}
				}
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setRequestProperty("Accept", "application/json");
				connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				connection.setRequestProperty("Content-Type", "application/json");
				OutputStream writer = connection.getOutputStream();
				writer.write(data.getBytes("UTF-8"));
				writer.flush();
				writer.close();
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder response = new StringBuilder();
				String s ;
				while ((s = in.readLine()) != null) {
					response.append(s);
				}
				in.close();
				connection.disconnect();
				return response.toString();

			} catch (ConnectException e) {
				LOG.error("Ошибка соединения с сервисом. "+e);
			} catch (Exception e) {
				if (connection!=null) {connection.disconnect();}
				LOG.error("in thread happens exception"+e);
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	private void makeHttpPostRequest(String data, String address,String aMethod, Map<String,String> params, Long aObjectId , EntityManager aManager){
			if (address==null) {
				return;
			}
			String response = makePOSTRequest(data, address, aMethod, params, aObjectId, aManager);
			if (response.length()>0) {
				//LOG.info("Получили ответ, вот он"+response);
				if (aMethod.equals("SetRegisterPatient")) {
					setAccountExternalCode(aObjectId,aManager,response);
				} else if (aMethod.equals("SetBlockPatient")){
					setAccountDateTo(aObjectId,aManager,new java.sql.Date(new java.util.Date().getTime()));
				}
			}
	}
	public void setAccountDateTo (Long aPatientExternalServiceAccountId, EntityManager aManager, Date aDateTo) {
		changeAccountInformation (aPatientExternalServiceAccountId, aManager, null,aDateTo);
	}
	public void setAccountExternalCode(Long aPatientExternalServiceAccountId, EntityManager aManager, String aExternalCode) {
		changeAccountInformation (aPatientExternalServiceAccountId, aManager, aExternalCode,null);
	}
	public void changeAccountInformation (Long aPatientExternalServiceAccountId, EntityManager aManager, String aExternalCode, Date aDateTo) {
		try {
			List<PatientExternalServiceAccount> list = aManager.createQuery("from PatientExternalServiceAccount where id=:id").setParameter("id",aPatientExternalServiceAccountId).getResultList();
			if (!list.isEmpty()) {
				PatientExternalServiceAccount p = list.get(0);
				if (p==null) return;
				if (aExternalCode!=null) {p.setExternalCode(aExternalCode);}
				if (aDateTo!=null) {p.setDateTo(aDateTo);}
				aManager.persist(p);
			}
		} catch (Exception e) {
			LOG.error("changeInformation exception: "+e);
			e.printStackTrace();
		}
	}

	/**
	 * Регистрация пациента в личном кабинете (а также прекращение регистрации)
	 * @param aPatientExternalServiceAccountId - ИД согласия
	 * @param aManager
	 */
	public void registerPatientExternalResource(Long aPatientExternalServiceAccountId, EntityManager aManager, String aUsername) {
		try {
			if (aManager==null) {
                aManager=theManager;
            }
            String address = getExternalServiceAddress();
			if (address==null) {return;}

			PatientExternalServiceAccount pesa = aManager.find(PatientExternalServiceAccount.class, aPatientExternalServiceAccountId);
			Patient pat = pesa.getPatient();
			JSONObject root = new JSONObject();
			Map<String,String> params = new LinkedHashMap<>();
			String function  = "";
			String logType = "EXTERNAL_RESOURCE_";
			if (pesa.getDateTo()!=null) {
				logType+="BLOCK_PATIENT";
				LOG.info("Отзываем согласие пациента. uid = "+pesa.getExternalCode());
				//params.put("uid",pesa.getExternalCode());
				//params.put("blockUser",aUsername);
				root.put("uid",pesa.getExternalCode());
				root.put("blockUser",aUsername);
				function="SetBlockPatient";
			} else {
				logType+="REGISTER_PATIENT";
				Date birthDate = pat.getBirthday();
				Calendar cal = new GregorianCalendar();
				cal.setTime(birthDate);
				cal.set(Calendar.MONTH,0);
				cal.set(Calendar.DATE,1);
				birthDate = new java.sql.Date(cal.getTimeInMillis());
				function = "SetRegisterPatient";
				root.put("firstname", pat.getFirstname());
				root.put("birthday",birthDate);
				root.put("phonenumber",pesa.getPhoneNumber());
				root.put("email",pesa.getEmail());
				root.put("regUser", aUsername);
			}

			AdminChangeJournal changeLog = new AdminChangeJournal();
			changeLog.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
			changeLog.setCreateTime(new java.sql.Time(new java.util.Date().getTime()));
			changeLog.setCreateUsername(aUsername);
			changeLog.setCType(logType);
			changeLog.setAnnulRecord(root.toString());
			aManager.persist(changeLog);
			makeHttpPostRequest(root.toString(),address,function, params, aPatientExternalServiceAccountId, aManager);
		} catch (JSONException e) {
			e.printStackTrace();
		}


	}

	/**
	 *
	 * @return Возвращает адрес сервера с REST сервисом
	 */
	public String getExternalServiceAddress() {
	EjbEcomConfig config = EjbEcomConfig.getInstance() ;
	String address =config.get("ru.amokb.patientcabinetaddress", null) ;
	if (address==null) {LOG.error("В конфигурационном файле нет параметра ru.amokb.patientcabinetaddress, ");}
	return address;
}
	/**
	 * Отправляет результаты визита в личный сервис пациента
	 * @param aProtocolId - ИД дневника
	 * @param aMedCaseId - ИД случая мед. обслуживания
	 * @param aRecord - Такст записи
	 * @param aManager - EntityManager
	 */

	@Deprecated
	public void sendProtocolToExternalResource(Long aProtocolId, Long aMedCaseId, String aRecord, EntityManager aManager) {
		LOG.warn("sendProtocolToExternalResource deprecated "+aProtocolId +" "+aMedCaseId);
		/*
		if (aManager==null) {aManager=theManager;}
		Protocol p = aProtocolId!=null?aManager.find(Protocol.class,aProtocolId):null;
		MedCase mc = p!=null?p.getMedCase():aManager.find(MedCase.class, aMedCaseId);
		//LOG.info("=== Protocol p = "+p);
		sendProtocolToExternalResource(p, mc,aRecord, aManager);
		*/
	}

	/**
	 * Выгружаем полностью истории лечения пациента (поликлиника, лаб. исследования, диагностика, выписки из стационара)
	 * @param aPatientExternalAccountId
	 * @param aManager
	 * @throws JSONException
	 */
	@Deprecated
	public void sendPatientMedicalHistoryToExternalResource(Long aPatientExternalAccountId, EntityManager aManager) { // Делаем разовую выгрузку всей информации по случаям лечения пациента.
		LOG.warn("sendPatientMedicalHistoryToExternalResource deprecated");
		if (1==1) return;
		if (aManager==null) {aManager=theManager;}
		String address = getExternalServiceAddress();
		if (address==null) {return;}
		String lpuCode= "AMOKB"; //TODO - переделать для работы в разных ЛПУ
		PatientExternalServiceAccount pesa = aManager.find(PatientExternalServiceAccount.class,aPatientExternalAccountId);
		Long aPatientId = pesa.getPatient().getId();
		JSONObject root = new JSONObject();
		JSONArray services = new JSONArray();
		String serviceType = "", medcaseDate = "", medcaseTime = "", executor = "", record = "";
		//List<Object[]> diaryList = aManager.createNativeQuery("select d.id as f1_did, mc.id f2_mid, mc.dtype from diary d left join medcase mc on mc.id=d.medcase_id " +
		//		"left join medcase par on par.id=mc.parent_id left join patient pat on pat.id=COALESCE(mc.patient_id,par.patient_id) where d.dtype='Protocol' and mc.dtype='Visit' and pat.id =:pat").setParameter("pat", aPatientId).getResultList();
		List<Object[]> list = aManager.createNativeQuery("select dd.id as f1_did, mc.id f2_mid" +
				", case when vst.code='LABSURVEY' then 'LABSURVEY' when vst.code='SERVICE' then 'SERVICE' else 'VISIT' end as f3_dtype" +
				",prot.id f4_prot,list(''||ms.id) as f5_msIds" +
				" from patient pat" +
				" left join medcase spo on spo.patient_id=pat.id" +
				" left join medcase mc on mc.parent_id=spo.id" +
				" left join diary prot on prot.medcase_id = mc.id and prot.dtype='Protocol'" +
				" left join document dd on dd.medcase_id=mc.id and dd.dtype='DischargeDocument'" +
				" left join medcase smc on smc.parent_id=mc.id and smc.dtype='ServiceMedCase'" +
				" left join medservice ms on ms.id=smc.medservice_id" +
				" left join vocservicetype vst on vst.id=ms.servicetype_id" +
				" where mc.dtype='Visit' and pat.id =:pat AND mc.datestart is not null " +
				" group by dd.id, mc.id, mc.dtype, vst.code, prot.id" +
				" order by mc.datestart desc, mc.timeexecute desc")
				.setParameter("pat", aPatientId).getResultList();
		if (!list.isEmpty()) { //Выписка из амбулаторной карты (прием к врачу)
			LOG.info("Найдено "+list.size()+" выписок по пациенту "+aPatientId);
			//Выгружаем дневники пол-ки, диагностика, лаборатория
			for (Object[] rec: list) {
				Visit vis = aManager.find(Visit.class, Long.valueOf(rec[1].toString()));
				String externalCaseId = null;

				JSONObject service = new JSONObject();
				serviceType=rec[2].toString();
				if (serviceType.equals("VISIT")) { //визит к врачу (не диагностика), выгружаем только выписки из амб. карты (dischargeDocument)
					if (rec[0]!=null&&!rec[0].equals("")){
						DischargeDocument dd =aManager.find(DischargeDocument.class,Long.valueOf(rec[0].toString()));
						record = dd.getHistory();
						externalCaseId = lpuCode+"#"+serviceType+"#"+(record!=null?record.hashCode():0);
					} else {
						continue;
					}
				} else if (serviceType.equals("LABSURVEY")||serviceType.equals("SERVICE")) { //Диагностика или лаб. исследование
					if (rec[3]!=null&&!rec[3].equals("")) {
						Protocol protocol = aManager.find(Protocol.class,Long.valueOf(rec[3].toString()));
						record = protocol.getRecord();
						externalCaseId = lpuCode+"#"+serviceType+"#"+rec[3].toString();
					} else {
						LOG.warn("У диагностичекой услуги/визита "+rec[1]+" нет протокола, запись не выгружаем");
					}
				} else {
					LOG.error("Неизвестный тип услуги");
					continue;
				}
				medcaseDate = "" +  vis.getDateStart();
				medcaseTime = "" + vis.getTimeExecute();
				executor = vis.getWorkFunctionExecute().getWorkFunction().getName() + " " + vis.getWorkFunctionExecute().getWorkerInfo();

				String[] medServicesIds = (rec[4]!=null&&!rec[4].toString().equals(""))?rec[4].toString().split(", "):null;
				if (medServicesIds!=null) {
					for (int i=0;i<medServicesIds.length;i++) {
						Long msId = Long.valueOf(medServicesIds[i].trim());
						MedService medService = aManager.find(MedService.class,msId);
						service.put("medservicecode", medService.getCode());
						service.put("medservicename", medService.getName());
					}
				}

				//Заполнили все данные, начинаем формирование json
				service.put("caseid",externalCaseId);
				service.put("patientcode", pesa.getExternalCode());
				service.put("lpucodecode", lpuCode);
				service.put("recordtype", serviceType);
				service.put("recorddatestart", medcaseDate);
				service.put("recordtimeexecute", medcaseTime);
				service.put("recordexecutor", executor);
				service.put("recordtext", record);
				services.put(service);




			}
		}
		//Начинаем искать госпитализации пациента
		list = aManager.createNativeQuery("select h.id as f1, h.id as f2 from medcase h where h.dtype='HospitalMedCase' and h.patient_id=:pat and h.dischargetime is not null and h.deniedhospitalizating_id is NULL ").setParameter("pat",aPatientId).getResultList();
		if (!list.isEmpty()) {
			serviceType = "DISCHARGE";
			for (Object [] hosps: list){
				JSONObject service = new JSONObject();
				HospitalMedCase hosp = aManager.find(HospitalMedCase.class,Long.valueOf(hosps[0].toString()));
				record = HospitalMedCaseViewInterceptor.getDischargeEpicrisis(hosp.getId(), aManager);
				medcaseDate = "" + hosp.getDateFinish();
				medcaseTime = "" + hosp.getDischargeTime();
				executor="";
				List<Object[]> listDep = aManager.createNativeQuery("select d.name as depname,to_char(dmc.dateStart,'DD.MM.YYYY') as dateStart,COALESCE(to_char(dmc.dateFinish,'DD.MM.YYYY'),to_char(dmc.transferDate,'DD.MM.YYYY'),'____.____.______г.') as dateFinish"
						+ ", coalesce(vwfd.code||' ','')||vwf.name||' '||p.lastname||' '|| p.firstname ||' '||p.middlename as worker"
						+ ",d.name as dname,d.id as did"
						+ ", coalesce(wf.code,'') as worker"
						+ ", case when d.IsNoOmc='1' then '1' else null end as IsNoOmc"
						+ " from MedCase dmc "
						+ " left join MisLpu d on d.id=dmc.department_id "
						+ " left join WorkFunction wf on wf.id=dmc.ownerFunction_id "
						+ " left join VocWorkFunction vwf on wf.workFunction_id=vwf.id "
						+ " left join VocAcademicDegree vwfd on wf.degrees_id=vwfd.id "
						+ " left join Worker w on w.id=wf.worker_id "
						+ " left join Patient p on p.id=w.person_id "
						+ " where dmc.parent_id='" + hosp.getId() + "' and dmc.DTYPE='DepartmentMedCase' order by dmc.dateStart,dmc.entranceTime ").getResultList();

				for (int i = 0; i < listDep.size(); i++) {
					Object[] dep = listDep.get(i);
					if (hosp.getResult() != null && (hosp.getResult().getCode().equals("11") || hosp.getResult().getCode().equals("15"))) {
						if (dep[7] == null) {
							executor = dep[3].toString();
						}
					} else {
						executor = dep[3].toString();
					}
				}
				service.put("caseid",lpuCode+"#"+serviceType+"#"+hosps[0].toString());
				service.put("patientcode", pesa.getExternalCode());
				service.put("lpucodecode", lpuCode);
				service.put("recordtype", serviceType);
				service.put("recorddatestart", medcaseDate);
				service.put("recordtimeexecute", medcaseTime);
				service.put("recordexecutor", executor);
				service.put("recordtext", record);
				services.put(service);
			}
		}
		root.put("services",services);
		makeHttpPostRequest(root.toString(), address,"SetStatement",null, null, aManager);

	}
	//public  void sendProtocolToExternalResource(Protocol d, MedCase mc, String aRecord, EntityManager aManager) {		//sendProtocolToExternalResource(d,mc.);	}
	public  void sendProtocolToExternalResource(Protocol d, MedCase mc, String aRecord, EntityManager aManager) {
		try {
			if (mc == null) {
				if (d==null) {
					LOG.error("Ни медкейса, ни дневника!! Что выгружать? Выходим!");
					return;
				}
				mc = d.getMedCase();
			}
			Patient pat = mc.getPatient();
			if (pat != null) {
				List<PatientExternalServiceAccount> pesa = aManager.createQuery(" from PatientExternalServiceAccount where patient = :pat and dateTo is null").setParameter("pat", pat).getResultList();
				if (pesa.isEmpty()) {
					return;
				} //Нет согласия - выходим
				String address = getExternalServiceAddress();
				if (address==null) {return;}
				String lpuCode = "AMOKB";
				String serviceType = "", medcaseDate = "", medcaseTime = "", executor = "";
				String patientCode=pesa.get(0).getExternalCode();
				String externalCaseId = null;
				String calendartimeId = "";
				JSONObject root = new JSONObject();
				JSONArray services = new JSONArray();
				JSONObject service = new JSONObject();
				//Если это дневник к визиту к врачу лаборатории, считаем, что это лабораторная услуга
				//Если это дневник к визиту к врачу диагн. отделения, считаем, что это диагностиеческая услуга

				if (mc instanceof HospitalMedCase) { //Если стационар - выгружаем выписной эпикриз
					if (d==null ||d instanceof DischargeEpicrisis) {
						//LOG.info("1=getEpicrisis 0");
						//recordText = HospitalMedCaseViewInterceptor.getDischargeEpicrisis(mc.getId(), aManager);
						//LOG.info("1=getEpicrisis 1 " + aRecord);
						HospitalMedCase hosp = (HospitalMedCase) mc;
						serviceType = "DISCHARGE";
						externalCaseId = lpuCode+"#"+serviceType+"#"+hosp.getId();
						medcaseDate = "" + hosp.getDateFinish();
						medcaseTime = "" + hosp.getDischargeTime();
						List<Object[]> list = aManager.createNativeQuery("select d.name as depname,to_char(dmc.dateStart,'DD.MM.YYYY') as dateStart,COALESCE(to_char(dmc.dateFinish,'DD.MM.YYYY'),to_char(dmc.transferDate,'DD.MM.YYYY'),'____.____.______г.') as dateFinish"
								+ ", coalesce(vwfd.code||' ','')||vwf.name||' '||p.lastname||' '|| p.firstname ||' '||p.middlename as worker"
								+ ",d.name as dname,d.id as did"
								+ ", coalesce(wf.code,'') as worker"
								+ ", case when d.IsNoOmc='1' then '1' else null end as IsNoOmc"
								+ " from MedCase dmc "
								+ " left join MisLpu d on d.id=dmc.department_id "
								+ " left join WorkFunction wf on wf.id=dmc.ownerFunction_id "
								+ " left join VocWorkFunction vwf on wf.workFunction_id=vwf.id "
								+ " left join VocAcademicDegree vwfd on wf.degrees_id=vwfd.id "
								+ " left join Worker w on w.id=wf.worker_id "
								+ " left join Patient p on p.id=w.person_id "
								+ " where dmc.parent_id='" + hosp.getId() + "' and dmc.DTYPE='DepartmentMedCase' order by dmc.dateStart,dmc.entranceTime ").getResultList();

						for (int i = 0; i < list.size(); i++) {
							Object[] dep = list.get(i);
							if (hosp.getResult() != null && (hosp.getResult().getCode().equals("11") || hosp.getResult().getCode().equals("15"))) {
								if (dep[7] == null) {
									executor = dep[3].toString();
								}
							} else {
								executor = dep[3].toString();
							}
						}
					} else {
							return;
						}
				} else if (mc instanceof DepartmentMedCase) { //Дневники специалистов в отделении не трогаем
					return;
				} else if (mc instanceof Visit) { //Дневники визитов не выгружаем. Выгружаем только документ "Выписка из амбулаторной карты"
					Visit vis = (Visit) mc;
					//DischargeDocument doc = aManager.createQuery("from DischargeDocument where medCase=:vis").setParameter("vis",vis).getRe;
					if (vis.getDateStart() == null) {
				//		LOG.debug("Визит " + vis.getId() + "не был оформлен, ничего не выгружаем");
						return;
					}
					serviceType = "VISIT";
					calendartimeId = ""+(vis.getTimePlan()!=null?vis.getTimePlan().getId():"");
					List<ServiceMedCase> list = aManager.createQuery("from ServiceMedCase where parent=:vis").setParameter("vis", mc).getResultList();
					if (!list.isEmpty()) {
						ServiceMedCase smc = list.get(0);
						String serviceCode = smc.getMedService().getServiceType().getCode();
						if (serviceCode != null && serviceCode.equals("LABSURVEY")) { //Помечаем случай как лаб. исследование
							serviceType = "LABSURVEY";
						} else if (serviceCode != null && serviceCode.equals("SERVICE")) { //Диагностическое исследование
							serviceType = "SERVICE";
						}
						service.put("medservicecode", smc.getMedService().getCode());
						service.put("medservicename", smc.getMedService().getName());
						externalCaseId=lpuCode+"#"+serviceType+"#"+(d!=null?d.getId():0);
					}
					if (serviceType.equals("VISIT")){
						if (d!=null) { //Если это визит, и не передается текст записи, не выгружаем, ибо поликлиничекие дневники мы выгружаем через "выписку"
							return ;
						} else {
							externalCaseId =lpuCode+"#"+serviceType+"#"+(aRecord!=null?aRecord.hashCode():0);
						}

					}
					if (d!=null) {
						medcaseDate = "" +  d.getDateRegistration();//vis.getDateStart();
						medcaseTime = "" + d.getTimeRegistration();//vis.getTimeExecute();
						executor = d.getSpecialist().getWorkFunction().getName() + " " + d.getSpecialist().getWorkerInfo();
						aRecord=d.getRecord();
					} else {
						medcaseDate = "" +vis.getDateStart();
						medcaseTime = "" +vis.getTimeExecute();
						executor = vis.getWorkFunctionExecute().getWorkFunction().getName() + " " + vis.getWorkFunctionExecute().getWorkerInfo();
					}
					if (StringUtil.isNullOrEmpty(aRecord)){
						aRecord= d!=null?d.getRecord():"";
					}
				}
				//Заполнили все данные, начинаем формирование json
				service.put("caseid", externalCaseId);
				service.put("patientcode", patientCode);
				service.put("lpucodecode", lpuCode);
				service.put("recordtype", serviceType);
				service.put("recorddatestart", medcaseDate);
				service.put("recordtimeexecute", medcaseTime);
				service.put("recordexecutor", executor);
				service.put("recordtext", aRecord);
				service.put("calendartimeid",calendartimeId);
				services.put(service); root.put("services",services);
				//LOG.info("=== jSON is ready, " + root.toString());
				makeHttpPostRequest(root.toString(), address,"SetStatement",null, mc.getId(), aManager);
				//LOG.info("return = ");
			} else if (pat == null) {
				LOG.error("Дневник = " + d.getId() + ", patient = null");
			}
		} catch (Exception e) {
			LOG.error("Exception happens "+e);
			e.printStackTrace();
		}
	}
	public static String saveParametersByProtocol(Long aSmoId,Protocol d, String aParams, String aUsername, EntityManager aManager) {
		JSONObject obj = new JSONObject(aParams) ;
		String wf = String.valueOf(obj.get("workFunction"));
		MedCase m = aManager.find(MedCase.class, aSmoId) ;
		if (m!=null && d!=null) {
			aManager.createNativeQuery("delete from FormInputProtocol where docProtocol_id=:protId").setParameter("protId",d.getId()).executeUpdate() ;
		}
		if (d == null) {
			d = new Protocol() ;
			d.setMedCase(m) ;
			aManager.persist(d) ;			
		}
		JSONArray params = obj.getJSONArray("params");
		StringBuilder sb = new StringBuilder() ;
		for (int i = 0; i < params.length(); i++) {
			JSONObject param = (JSONObject) params.get(i);
			FormInputProtocol fip = new FormInputProtocol() ;
			fip.setDocProtocol(d) ;
			Parameter p = aManager.find(Parameter.class, ConvertSql.parseLong(param.get("id"))) ;
			fip.setParameter(p) ;
			fip.setPosition(i+1L);
			String type = String.valueOf(param.get("type"));
			// 1-числовой
			// 4-числовой с плав точкой
			String value = String.valueOf(param.get("value"));
			if (type.equals("1")||type.equals("4")) {
				if (!StringUtil.isNullOrEmpty(value)) {
					fip.setValueBD(new BigDecimal(value)) ;
					fip.setValueText(value) ;
					if (sb.length()>0) sb.append("\n") ;
					sb.append(param.get("name")).append(": ") ;
					sb.append(value).append(" ") ;
					sb.append(param.get("unitname")).append(" ") ;
				}
				//пользовательский справочник
			} else if (type.equals("2")) {
				Long id = ConvertSql.parseLong(value) ;
				if (id!=null && !id.equals(Long.valueOf(0))) {
					UserValue uv = aManager.find(UserValue.class, id) ;
					fip.setValueVoc(uv) ;
					if (sb.length()>0) sb.append("\n") ;
					sb.append(param.get("name")).append(": ") ;
					sb.append(param.get("valueVoc")).append(" ") ;
					sb.append(param.get("unitname")).append(" ") ;
				}
				//пользовательский справочник (множественный выбор)
			} else if (type.equals("6")) {
				String id = String.valueOf(param.get("value")) ;
				if (id!=null && !id.trim().equals(",") && !id.trim().equals(",,") && !id.trim().equals("")) {
					fip.setValueText(String.valueOf(param.get("valueVoc"))) ;
					fip.setListValues(id) ;
					if (sb.length()>0) sb.append("\n") ;
					sb.append(param.get("name")).append(": ") ;
					sb.append(param.get("unitname")).append(" ") ;
					sb.append(fip.getValueText()) ;
				}
				//пользовательский справочник (текст)
			} else if (type.equals("7")) {
				Long id = ConvertSql.parseLong(value) ;
				if (id!=null && !id.equals(Long.valueOf(0))) {
					UserValue uv = aManager.find(UserValue.class, id) ;
					fip.setValueVoc(uv) ;
					fip.setValueText(String.valueOf(param.get("addValue"))) ;
					if (sb.length()>0) sb.append("\n") ;
					sb.append(param.get("name")).append(": ") ;
					sb.append(param.get("valueVoc")).append(" ") ;
					sb.append(param.get("unitname")).append(" ") ;
					sb.append(param.get("addValue")).append(" ") ;
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
			aManager.persist(fip) ;
		}
		d.setRecord(sb.toString()) ;
		aManager.persist(d) ;
			if (m instanceof Visit)  {
				Visit vis = (Visit) m;
				if (wf!=null && !wf.equals("0")) {
					WorkFunction wfo = aManager.find(WorkFunction.class, Long.valueOf(wf)) ;
					vis.setWorkFunctionExecute(wfo) ;
				} else {
					vis.setWorkFunctionExecute(vis.getWorkFunctionPlan()) ;
					aManager.persist(vis) ;
				}
			}
		aManager.persist(m) ;
		return ""+d.getId() ;
	}
	
	public String getTextByProtocol(long aProtocolId) {
        Protocol tempprot = theManager.find(Protocol.class, aProtocolId) ;
        if(tempprot==null) throw new IllegalArgumentException("Нет шаблона протокола с таким ИД "+aProtocolId) ;
        return tempprot.getRecord() ;		
	}
    public String getTextTemplate(long aId) {
        TemplateProtocol tempprot = theManager.find(TemplateProtocol.class, aId) ;
        if(tempprot==null) throw new IllegalArgumentException("Нет шаблона протокола с таким ИД "+aId) ;
        return tempprot.getText() ;
    }

    public String getNameVoc(String aClassif, long aId) {
        return "123123" ;
    }

    @EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext
    EntityManager theManager ;
	public Long getCountSymbolsInProtocol(long aVisit) {
		List<Protocol> list = theManager.createQuery("from Protocol where medCase_id=:idv").setParameter("idv", aVisit).setMaxResults(1).getResultList() ;
		return list.isEmpty() ? Long.valueOf(0) : Long.valueOf(list.get(0).getRecord().length());
	}
}