package ru.ecom.mis.ejb.service.worker;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.mis.ejb.domain.worker.GroupWorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class GroupWorkFunctionVoc implements IAllValue {
	private static final Logger LOG = Logger.getLogger(GroupWorkFunctionVoc.class);

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
		LinkedList<VocValue> ret = new LinkedList<>() ;
		StringBuilder sql = new StringBuilder();

		if (aContext.getVocAdditional()==null || aContext.getVocAdditional().getParentId()==null) {
			sql.append("from GroupWorkFunction");
			
			List<GroupWorkFunction> groups ;
			try{
				 groups = aContext.getEntityManager()
					.createQuery(sql.toString())
					.getResultList() ;
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
				return ret ;
			}
			for(GroupWorkFunction group : groups) {
				 try {
					 add(ret, group) ;
				 } catch (IllegalStateException e) {
				 	e.printStackTrace();
					 LOG.error(e.getMessage(),e);
				 }
			}
		}
		
		
		if (aContext.getVocAdditional()!=null && aContext.getVocAdditional().getParentId()!=null) {
			String parent =aContext.getVocAdditional().getParentId() ;
			Long workerId ;
			Long function ;
			try {
				int ind1 = parent.indexOf('#');
				//int ind3 = parent.indexOf(":",ind2) ;
				if (ind1<1) throw new IllegalArgumentException("") ;
				if (ind1==parent.length()) throw new IllegalArgumentException("") ;
				function = Long.valueOf(parent.substring(0,ind1)) ;
				workerId = Long.valueOf(parent.substring(ind1+1)) ;
			} catch (Exception e) {
				return ret ;
			}
			Worker worker = aContext.getEntityManager().find(Worker.class, workerId) ;
			sql.append("from GroupWorkFunction where lpu_id=:lpu and (workFunction_id=:function or  hasServiceStuff ='1')");
			//sql.append("from BedFund where lpu_id=:lpu");
			List<GroupWorkFunction> groups  ;
			try {
				 groups = (List<GroupWorkFunction>) aContext.getEntityManager()
					.createQuery(sql.toString())
					.setParameter("lpu",worker.getLpu().getId())
					.setParameter("function",function) 
					.getResultList() ;
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(),e);
				return ret ;
			}
			for(GroupWorkFunction group : groups) {
				 try {
					 add(ret, group) ;
				 } catch (IllegalStateException e) {
					 LOG.error(e.getMessage(),e);
				 }
			}
		}
		return ret;
	}
	
	private static void add(List<VocValue> aValues, GroupWorkFunction aGroup) {
		aValues.add(new VocValue(String.valueOf(aGroup.getId()), aGroup.getGroupName())) ;
	}
}