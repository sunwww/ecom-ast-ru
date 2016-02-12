package ru.ecom.mis.ejb.service.prescription;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.ecom.diary.ejb.domain.Diary;
import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.diary.ejb.domain.protocol.parameter.FormInputProtocol;
import ru.ecom.diary.ejb.domain.protocol.parameter.Parameter;
import ru.ecom.diary.ejb.domain.protocol.parameter.user.UserValue;
import ru.ecom.ejb.sequence.service.SequenceHelper;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.expomc.ejb.services.registry.ExcelTemplateAllValueVoc;
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
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDay;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.service.worker.WorkerServiceBean;
import ru.ecom.poly.ejb.domain.protocol.Protocol;
import ru.ecom.poly.ejb.domain.protocol.RoughDraft;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;
/**
 * Сервис для работы с назначениями
 * @author STkacheva
 */
@Stateless
@Remote(IPrescriptionService.class)
public class PrescriptionServiceBean implements IPrescriptionService {

	public String createNewDirectionFromPrescription(Long aPrescriptionListId, Long aWorkFunctionPlanId, Long aDatePlanId, Long aTimePlanId, Long aMedServiceId, String aUsername, Long aOrderWorkFunction) {
		MedService sms = theManager.find(MedService.class, aMedServiceId);
	if (sms!=null) {
		long date = new java.util.Date().getTime() ;
		Visit vis = new Visit();
	PrescriptList pl = theManager.find(PrescriptList.class, aPrescriptionListId);
	Patient pat = pl.getMedCase().getPatient();
	WorkFunction wfp = theManager.find(WorkFunction.class, aWorkFunctionPlanId);
	WorkFunction wfo = theManager.find(WorkFunction.class, aOrderWorkFunction);
	WorkCalendarTime wct = theManager.find(WorkCalendarTime.class, aTimePlanId);
	VocServiceStream  vss = (VocServiceStream) theManager.createQuery("from VocServiceStream where code=:code").setParameter("code", "HOSPITAL").getSingleResult();
	VocWorkPlaceType wpt = (VocWorkPlaceType) theManager.createQuery("from VocWorkPlaceType where code=:code").setParameter("code", "POLYCLINIC").getSingleResult();
	 
	
	
	vis.setWorkPlaceType(wpt) ;
	vis.setServiceStream(vss);
	vis.setPatient(pat);
	vis.setCreateDate(new java.sql.Date(date));
	vis.setCreateTime(new java.sql.Time(date));
	vis.setDatePlan(wct.getWorkCalendarDay());
	vis.setNoActuality(false);
	vis.setTimePlan(wct);
	vis.setWorkFunctionPlan(wfp);
	vis.setOrderWorkFunction(wfo);
	vis.setUsername(aUsername);
	theManager.persist(vis);
	
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
	public String saveLabAnalyzed(Long aSmoId,Long aPrescriptId,Long aProtocolId, String aParams, String aUsername) throws JSONException {
		Protocol d =null;
		//if (aProtocolId!=null )) {
		JSONObject obj = new JSONObject(aParams) ;
		String wf = String.valueOf(obj.get("workFunction"));
		System.out.print("workfunction================"+wf);
		StringBuilder sql = new StringBuilder() ;
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
			theManager.persist(d) ;
		}
		//}
		
		JSONArray params = obj.getJSONArray("params");
		StringBuilder sb = new StringBuilder() ;
		for (int i = 0; i < params.length(); i++) {
			boolean isSave = true ;
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
				}
				//пользовательский справочник
			} else if (type.equals("2")) {
				Long id = ConvertSql.parseLong(value) ;
				if (id!=null && !id.equals(Long.valueOf(0))) {
					UserValue uv = theManager.find(UserValue.class, id) ;
					fip.setValueVoc(uv) ;
					if (sb.length()>0) sb.append("\n") ;
					sb.append(param.get("name")).append(": ") ;
					sb.append(param.get("valueVoc")).append(" ") ;
					sb.append(param.get("unitname")).append(" ") ;
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
							"and p.planStartDate=to_date('"+aDate+"','yyyy-mm-dd') and p.materialId is not null " +
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
		String sqlquery = "select case when mc.dtype='HospitalMedCase' then mc.datestart || '-' || mc.entrancetime " +
				" else mcs.datestart || '-' || mcs.entrancetime end as datetime, " +
				" case when mc.dtype='HospitalMedCase' then case when mc.emergency='1' then '1' else null end else " +
				" case when mc.emergency='1' then '1' else null end end " +
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
		System.out.println("======= getLabList, tmpl = "+template );
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
		req.append("select vpt.id, vpt.name from vocprescripttype vpt ");
		if(!isEmergency) {
			req.append("where vpt.code!='EMERGENCY' ");
		}
		req.append("order by vpt.id ");
		List<Object[]> list = theManager.createNativeQuery(req.toString()).getResultList() ;
		System.out.println("----------in getPrescriptionTypes, isBool = "+isEmergency);
		if (list.size()>0) {
			for (int i=0;i<list.size();i++) {
				Object[] obj = list.get(i);
				res.append(obj[0]).append(":").append(obj[1]).append("#");
			}
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
			
			System.out.println("================ CLASS PrescriptionList = "+template.getClass().toString());
			if (template.getClass().toString().equals("PrescriptListTemplate")) {
				 template = (PrescriptListTemplate) template;
				System.out.println("================ IF HAPPENS CLASS PrescriptionList = "+template.getClass().toString());	
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
		
	}	



