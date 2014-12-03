package ru.ecom.mis.ejb.service.prescription;

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

import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.ejb.sequence.service.SequenceHelper;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.prescription.AbstractPrescriptionList;
import ru.ecom.mis.ejb.domain.prescription.DietPrescription;
import ru.ecom.mis.ejb.domain.prescription.DrugPrescription;
import ru.ecom.mis.ejb.domain.prescription.PrescriptList;
import ru.ecom.mis.ejb.domain.prescription.PrescriptListTemplate;
import ru.ecom.mis.ejb.domain.prescription.Prescription;
import ru.ecom.mis.ejb.domain.prescription.ServicePrescription;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.service.worker.WorkerServiceBean;
import ru.nuzmsh.util.format.DateFormat;
/**
 * Сервис для работы с назначениями
 * @author STkacheva
 */
@Stateless
@Remote(IPrescriptionService.class)
public class PrescriptionServiceBean implements IPrescriptionService {
	
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
		String sqlquery = "select mcs.datestart || '-' || mcs.entrancetime as datetime, " +
				"case when mcs.emergency='1' then '1' else null end " +
				" from medCase mc " +
				"left join medcase mcs on mcs.id = mc.parent_id ";
				
		if (idType.equals("prescriptionList")) {
			sqlquery+="left join prescriptionList pl on pl.medcase_id = mc.id " +
					"where pl.id ='"+aId+"' and mcs.dtype='HospitalMedCase' ";
		} else if (idType.equals("medCase")) {
			sqlquery+="where mc.id ='"+aId+"' and mcs.dtype='HospitalMedCase' ";
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
		StringBuilder labList = new StringBuilder();
		for (Prescription presc: template.getPrescriptions()) {
			labList.append(getPrescriptionInfo(presc));
		}
		if (template.getComments().length()>0) {
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
			req.append("where vpt.code!='EMEGRENCY' ");
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
	private String getPrescriptionInfo (Prescription aPresc)
	{
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
		}
		if (aPresc instanceof DietPrescription) {
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
		}
		if (aPresc instanceof ServicePrescription) {
			try{
			ServicePrescription presNew = (ServicePrescription) aPresc;
			list.setLength(0);
			list.append("SERVICE@");
			list.append(presNew.getMedService().getServiceType().getCode()).append(":");
			list.append(presNew.getMedService().getId()).append(":");
			list.append(presNew.getMedService().getCode()).append(" ");
			list.append(presNew.getMedService().getName()).append("::"); //: aLabDate 
			list.append(presNew.getPrescriptCabinet()!=null?presNew.getPrescriptCabinet().getId():"").append(":");
			list.append(presNew.getPrescriptCabinet()!=null?presNew.getPrescriptCabinet().getName():"").append("#");
			return list.toString();
			}
			catch (Exception e) {
				System.out.println("catch Service "+e);
				e.printStackTrace();
			}
		}
		System.out.println("_----------------Some shit happens!!!"+aPresc);
		return "";
	}
	/**
	 * Добавить все назначения в новый лист
	 * @param aIdTemplateList - ИД шаблона листа назначений
	 * @param aIdParent - ИД СМО (если СМО не указан создается шаблон !!!)
	 * @return true - при успешном сохранении, false - при ошибке при сохранении
	 */
	public boolean savePrescriptNew(Long aIdTemplateList, Long aIdParent) {
		PrescriptListTemplate template = theManager.find(PrescriptListTemplate.class, aIdTemplateList);
		MedCase medCase = theManager.find(MedCase.class, aIdParent) ;
		WorkFunction wf = WorkerServiceBean.getWorkFunction(theContext, theManager) ;
//		System.out.println("template="+aIdTemplateList);
//		System.out.println("medCase="+aIdParent) ;
		if (medCase!=null) {
//			System.out.println("MedCase существует ! Создается PrescriptList") ;
			PrescriptList list = new PrescriptList() ;
			
			list.setMedCase(medCase) ;
			list.setCreateDate(new java.sql.Date(new Date().getTime())) ;
			list.setCreateUsername(theContext.getCallerPrincipal().toString());
			list.setName(template.getName()) ;
			list.setComments(template.getComments());
			list.setWorkFunction(wf) ;
			theManager.persist(list) ;
			addPrescription(template,list,wf) ;
		} else  {
//			System.out.println("MedCase не существует ! Создается PrescriptListTemplate") ;
			PrescriptListTemplate list = new PrescriptListTemplate() ;
			list.setCreateDate(new java.sql.Date(new Date().getTime())) ;
			list.setCreateUsername(theContext.getCallerPrincipal().toString());
			list.setName(template.getName()) ;
			List<TemplateCategory> tempCategList = new ArrayList<TemplateCategory>() ;
			for (TemplateCategory tempCateg:template.getCategories()) {
				tempCategList.add(tempCateg) ;
			}
			list.setCategories(tempCategList);
			list.setComments(template.getComments());
			list.setWorkFunction(wf);
			theManager.persist(list) ;
			theManager.flush() ;
//			System.out.println(list.getId()) ;
			addPrescription(template,list,null) ;
		}
		return true ;
	}
	
	private void addPrescription(AbstractPrescriptionList aTemplate, AbstractPrescriptionList aList, WorkFunction aSpecialist) {		
		if (aTemplate!=null && aList!=null)  {
			List<Prescription> listNew = aList.getPrescriptions() ;
			if (listNew==null) listNew =new ArrayList<Prescription>() ;
			for (Prescription presc:aTemplate.getPrescriptions()) {
				System.out.println("Назначение: "+presc.getId());
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
		}
		if (aPrescOld instanceof DietPrescription) {
			DietPrescription presNew = new DietPrescription() ;
			DietPrescription presOld = (DietPrescription)aPrescOld ;
			presNew.setDiet(presOld.getDiet()) ;
			presNew.setPrescriptSpecial(aSpecialist) ;
			return presNew ;
		}
		if (aPrescOld instanceof ServicePrescription) {
			ServicePrescription presNew = new ServicePrescription();
			ServicePrescription presOld = (ServicePrescription) aPrescOld;
			presNew.setMedService(presOld.getMedService());
			presNew.setPrescriptCabinet(presOld.getPrescriptCabinet());
			presNew.setPrescriptSpecial(presOld.getPrescriptSpecial());
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



