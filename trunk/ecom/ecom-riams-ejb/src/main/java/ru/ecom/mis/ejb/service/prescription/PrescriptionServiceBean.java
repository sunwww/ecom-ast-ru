package ru.ecom.mis.ejb.service.prescription;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.prescription.AbstractPrescriptionList;
import ru.ecom.mis.ejb.domain.prescription.DietPrescription;
import ru.ecom.mis.ejb.domain.prescription.DrugPrescription;
import ru.ecom.mis.ejb.domain.prescription.PrescriptList;
import ru.ecom.mis.ejb.domain.prescription.PrescriptListTemplate;
import ru.ecom.mis.ejb.domain.prescription.Prescription;
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
	

	public boolean checkMedCase(Long aPrescriptionListId) {
		//var date = new java.sql.Date() ;
		boolean isEmergency =false ;
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss") ;
		String sqlquery = "select mcs.datestart || '-' || mcs.entrancetime as datetime, case when mc.emergency='1' then '1' else null end, mc.dtype from prescriptionList pl " +
				"left join medcase mc on pl.medcase_id = mc.id " +
				"left join medcase mcs on mcs.id = mc.parent_id " +
				"where pl.id ='"+aPrescriptionListId+"' and mcs.dtype='HospitalMedCase' " ;

		List<Object[]> list = theManager.createNativeQuery(sqlquery).getResultList() ;
		if (list.size()>0) {
			Object[] obj = list.get(0);
			
			java.util.Date date;
			try {
				date = sdf.parse(obj[0].toString());
				boolean check = ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SecPolicy.isDateLessThenHour(date,2);
				if (obj[1]!=null && check) { // Если экстренно
					isEmergency=true;
				}
			} catch (ParseException e) {
				System.out.println ("Error parsing date. Obj= "+obj);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			
		}
		return isEmergency ;
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
			DietPrescription presOld = new DietPrescription() ;
			presNew.setDiet(presOld.getDiet()) ;
			presNew.setPrescriptSpecial(aSpecialist) ;
			return presNew ;
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



