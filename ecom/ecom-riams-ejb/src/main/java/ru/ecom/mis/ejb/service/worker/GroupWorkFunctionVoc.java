package ru.ecom.mis.ejb.service.worker;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.mis.ejb.domain.worker.GroupWorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.util.voc.VocValue;

public class GroupWorkFunctionVoc implements IAllValue {
	
	public void destroy() {
		
	}

	
	@SuppressWarnings("unchecked")
	public Collection<VocValue> listAll(AllValueContext aContext) {
		LinkedList<VocValue> ret = new LinkedList<VocValue>() ;
		StringBuilder sql = new StringBuilder();
		
		System.out.println("group=") ;
		
		
		if (aContext.getVocAdditional()==null || aContext.getVocAdditional()!=null && aContext.getVocAdditional().getParentId()==null) {
			//System.out.println("parent lpuAndDate="+aContext.getVocAdditional().getParentId()) ;
			sql.append("from GroupWorkFunction");
			
			System.out.println("groupsql="+sql) ;
			List<GroupWorkFunction> groups = null ;
			try{
				 groups = aContext.getEntityManager()
					.createQuery(sql.toString())
					.getResultList() ;
			} catch (Exception e) {
				System.out.println("Error sql:="+e.getMessage()) ;
				return ret ;
			}
			System.out.println("size="+groups.size()) ;
			for(GroupWorkFunction group : groups) {
				 try {
					 add(ret, group) ;
				 } catch (IllegalStateException e) {
					 System.out.println(e.getMessage()) ;
				 }
			}
			////////////////?????????????????????/
		}
		
		
		if (aContext.getVocAdditional()!=null && aContext.getVocAdditional().getParentId()!=null) {
			String parent =aContext.getVocAdditional().getParentId() ;
			Long workerId ;
			Long function ;
			try {
				System.out.println("parent="+parent) ;
				int ind1 = parent.indexOf("#");
				//int ind3 = parent.indexOf(":",ind2) ;
				System.out.println("ind1="+ind1);
				//System.out.println("ind3="+ind3);
				System.out.println("parent lpuAndDate="+parent) ;
				if (ind1<1) throw new IllegalArgumentException("") ;
				if (ind1==parent.length()) throw new IllegalArgumentException("") ;
				function = Long.valueOf(parent.substring(0,ind1)) ;
				System.out.println("parent worker="+function) ;
				workerId = Long.valueOf(parent.substring(ind1+1)) ;
				System.out.println("parent function="+workerId) ;
				
			} catch (Exception e) {
				return ret ;
			}
			Worker worker = aContext.getEntityManager().find(Worker.class, workerId) ;
			sql.append("from GroupWorkFunction where lpu_id=:lpu and workFunction_id=:function");
			//sql.append("from BedFund where lpu_id=:lpu");
			System.out.println(sql.toString()) ;
			List<GroupWorkFunction> groups = null ;
			try{
				 groups = (List<GroupWorkFunction>) aContext.getEntityManager()
					.createQuery(sql.toString())
					.setParameter("lpu",worker.getLpu().getId())
					.setParameter("function",function) 
					.getResultList() ;
			} catch (Exception e) {
				System.out.println("Error sql:="+e.getMessage()) ;
				return ret ;
			}
			System.out.println("size="+groups.size()) ;
			for(GroupWorkFunction group : groups) {
				 try {
					 add(ret, group) ;
				 } catch (IllegalStateException e) {
					 System.out.println(e.getMessage()) ;
				 }
			}
		}
		return ret;
	}
	
	private static void add(List<VocValue> aValues, GroupWorkFunction aGroup) {
		StringBuilder name = new StringBuilder() ;
		name.append(aGroup.getGroupName()) ;
		aValues.add(new VocValue(String.valueOf(aGroup.getId()), name.toString())) ;
	}
}