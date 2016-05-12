package ru.ecom.mis.ejb.service.lpu;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.services.voc.VocContext;
import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.form.lpu.interceptors.MisLpuDynamicSecurity;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

public class LpuAllValueVoc implements IAllValue {

	public void destroy() {
		
	}
	public String getNameById(String aId, String aVocName, VocAdditional aAdditional, AllValueContext aContext) throws VocServiceException {
        String ret = null;
        if (aId!=null && !aId.equals("") && !aId.equals("0")) {
        	List<Object[]> lpus = aContext.getEntityManager().createNativeQuery("select id,name from MisLpu where id="+aId+" order by name").getResultList() ;
    		if (lpus.size()>0) ret = ""+lpus.get(0)[1] ;
        }
        return ret;
    }

	
	//@SuppressWarnings("unchecked")
	public Collection<VocValue> listAll(AllValueContext aContext) {
		//List<MisLpu> lpus = aContext.getEntityManager().createQuery("from MisLpu where parent_id is null order by name").getResultList() ;
		List<Object[]> lpus = aContext.getEntityManager().createNativeQuery("select id,name from MisLpu where parent_id is null order by name").getResultList() ;
		LinkedList<VocValue> ret = new LinkedList<VocValue>() ;
		
		InterceptorContext context = new InterceptorContext(aContext.getEntityManager(), aContext.getSessionContext()) ;
		
		for(Object[] lpu : lpus) {
			 try {
				 Long id = ConvertSql.parseLong(lpu[0]) ;
				 theSecurity.checkParent("View", id, context) ;
				 //aContext.getEntityManager().refresh(lpu);
				 add(ret, id, ""+lpu[1], "",aContext.getEntityManager()) ;
			 } catch (IllegalStateException e) {
				 
			 }
		}
		return ret;
	}
	
	private static void add(List<VocValue> aValues, Long aLpuId, String aLpuName, String aAppend
			, EntityManager aManager) {
		String name = aAppend + aLpuName ;
		//aManager.refresh(aLpu);
		aValues.add(new VocValue(String.valueOf(aLpuId), name)) ;
		
		List<Object[]> lpus = aManager.createNativeQuery("select id,name from MisLpu where parent_id='"+aLpuId+"' order by name").getResultList() ;
		for(Object[] lpu : lpus) {
			Long id = ConvertSql.parseLong(lpu[0]) ;
			add(aValues, id, ""+lpu[1], ".    "+aAppend,aManager) ;
			//System.out.println("lpu=     ."+aAppend+lpu.getId()+"--"+lpu.getName()) ;
		}
	}

	private final MisLpuDynamicSecurity theSecurity = new MisLpuDynamicSecurity() ;
}
