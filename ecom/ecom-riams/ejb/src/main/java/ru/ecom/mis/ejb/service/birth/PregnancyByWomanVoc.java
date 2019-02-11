package ru.ecom.mis.ejb.service.birth;

import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.mis.ejb.domain.birth.Pregnancy;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Справочник по беременностям
 * @author stkacheva
 *
 */
public class PregnancyByWomanVoc  implements IAllValue {
    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, AllValueContext aContext) throws VocServiceException {
    	String ret = null;
        if (aId != null) {
            for (VocValue value : listAll(aContext)) {
                if (aId.equals(value.getId())) {
                    ret = value.getName();
                }
            }
        }
        return ret;
    }
	public void destroy() {
		
	}

	@SuppressWarnings("unchecked")
	public Collection<VocValue> listAll(AllValueContext aContext) {
		LinkedList<VocValue> ret = new LinkedList<VocValue>() ;
		List<Pregnancy> pregs ;
		if (aContext.getVocAdditional()!=null  
				&&aContext.getVocAdditional().getParentId()!=null
				&&!aContext.getVocAdditional().getParentId().equals("")) {	
			Long idPatient =ConvertSql.parseLong(aContext.getEntityManager()
					.createNativeQuery("select patient_id from MedCase where id=:medCase_id")
					.setParameter("medCase_id", aContext.getVocAdditional().getParentId())
					.getSingleResult()) ;
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
		for (Pregnancy preg:pregs) {
			VocValue voc = new VocValue(String.valueOf(preg.getId()), preg.getInformation()) ;
			ret.add(voc) ;
		}
		
		return ret ;
	}
	
	

}
