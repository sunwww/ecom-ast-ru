package ru.ecom.mis.ejb.service.lpu;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.form.lpu.interceptors.MisLpuDynamicSecurity;
import ru.nuzmsh.util.voc.VocValue;

public class LpuAllValueVoc implements IAllValue {

	public void destroy() {
		
	}

	
	//@SuppressWarnings("unchecked")
	public Collection<VocValue> listAll(AllValueContext aContext) {
		List<MisLpu> lpus = aContext.getEntityManager().createQuery("from MisLpu where parent_id is null order by name").getResultList() ;
		LinkedList<VocValue> ret = new LinkedList<VocValue>() ;
		
		InterceptorContext context = new InterceptorContext(aContext.getEntityManager(), aContext.getSessionContext()) ;
		
		for(MisLpu lpu : lpus) {
			 try {
				 theSecurity.checkParent("View", lpu.getId(), context) ;
				 //aContext.getEntityManager().refresh(lpu);
				 add(ret, lpu, "",aContext.getEntityManager()) ;
			 } catch (IllegalStateException e) {
				 
			 }
		}
		return ret;
	}
	
	private static void add(List<VocValue> aValues, MisLpu aLpu, String aAppend
			, EntityManager aManager) {
		String name = aAppend + aLpu.getName() ;
		//aManager.refresh(aLpu);
		aValues.add(new VocValue(String.valueOf(aLpu.getId()), name)) ;
		
		for(MisLpu lpu : aLpu.getSubdivisions()) {
			
			add(aValues, lpu, ".    "+aAppend,aManager) ;
			//System.out.println("lpu=     ."+aAppend+lpu.getId()+"--"+lpu.getName()) ;
		}
	}

	private final MisLpuDynamicSecurity theSecurity = new MisLpuDynamicSecurity() ;
}
