package ru.ecom.mis.ejb.service.birth;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.mis.ejb.domain.birth.Pregnancy;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.service.worker.WorkerServiceBean;
import ru.nuzmsh.util.voc.VocValue;

/**
 * Справочник по беременностям
 * @author stkacheva
 *
 */
public class PregnancyByWomanVoc  implements IAllValue {

	public void destroy() {
		
	}

	@SuppressWarnings("unchecked")
	public Collection<VocValue> listAll(AllValueContext aContext) {
		LinkedList<VocValue> ret = new LinkedList<VocValue>() ;
		List<Pregnancy> pregs ;
		if (aContext.getVocAdditional()!=null  
				&&aContext.getVocAdditional().getParentId()!=null
				&&!aContext.getVocAdditional().getParentId().equals("")) {	
			System.out.println("Поиск по medCaseId="+ aContext.getVocAdditional().getParentId()) ;
			Long idPatient =WorkerServiceBean.parseLong(aContext.getEntityManager()
					.createNativeQuery("select patient_id from MedCase where id=:medCase_id")
					.setParameter("medCase_id", aContext.getVocAdditional().getParentId())
					.getSingleResult()) ;
			System.out.println("Поиск по пациенту="+ idPatient) ;
			//MedCase ms = aContext.getEntityManager().find(MedCase.class, Long.valueOf(aContext.getVocAdditional().getParentId())) ;
			pregs = aContext.getEntityManager()
				.createQuery("from Pregnancy where patient_id=:patId order by orderNumber")
				.setParameter("patId", idPatient)
				.getResultList() ;
		} else {
			pregs = aContext.getEntityManager()
				.createQuery("from Pregnancy order by patient_id")
				.getResultList() ;
		}
		System.out.println("Поиск завершен. Обработка данных...") ;
		for (Pregnancy preg:pregs) {
			VocValue voc = new VocValue(String.valueOf(preg.getId()), preg.getInformation()) ;
			ret.add(voc) ;
		}
		
		return ret ;
	}
	
	

}
