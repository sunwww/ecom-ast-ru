package ru.ecom.ejb.sequence.service;

import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.sequence.domain.SequenceInfo;
import ru.ecom.poly.ejb.domain.Medcard;

public class SequenceHelper {

	private static final SequenceHelper INSANCE = new SequenceHelper() ;
	
	private final String SYNC = "ru.ecom.ejb.sequence.service.SequenceHelper.SYNC" ;
	
	private SequenceHelper() {}
	
	public static SequenceHelper getInstance() {
		return INSANCE;
	}
	
	public String startUseNextValue(String aKey, EntityManager aManager) {
		synchronized(SYNC) {
			SequenceInfo info = getSequenceInfo(aKey, aManager) ;
			String value = null;
			String mayByNextValue = nextValue(info.getNextValue());
			for(int i=0; i<1000; i++) {
				
				if(checkExists(mayByNextValue, aManager)) {
					
				} else {
					value = mayByNextValue ;
					break ;
				}
				mayByNextValue = nextValue(mayByNextValue);
			}
			if(value==null) {
				throw new IllegalStateException("Ошибка получение следу") ;
			}
			info.setNextValue(value);
			return value ;
		}
	}
	
	private boolean checkExists(String mayByNextValue, EntityManager aManager) {
		List<Medcard> list = aManager.createQuery("from Medcard where number=:number")
			.setParameter("number", mayByNextValue).getResultList();
		return list.size()!=0;
	}

	private String nextValue(String aOldValue) {
		long l = Long.parseLong(aOldValue);
		return String.valueOf(l+1) ;
	}
	
	private SequenceInfo getSequenceInfo(String aKey, EntityManager aManager) {
		List<SequenceInfo> list = aManager.createQuery("from SequenceInfo where uniqueName=:key")
			.setParameter("key", aKey).getResultList();
		if(list.size()!=0) {
			return list.iterator().next();
		} else {
			//aManager.createNativeQuery("insert into SequenceInfo (uniqueName, nextValue) values (:key, '1')",SequenceInfo.class).setParameter("key", aKey);
			SequenceInfo info = new SequenceInfo() ;
			info.setUniqueName(aKey) ;
			info.setNextValue("0");
			aManager.persist(info) ;
			
			return getSequenceInfo(aKey, aManager) ;
			//throw new IllegalStateException(aKey+" ошибка  = "+list.size()) ;
		}
		
	}
	public static void main(String args[]) {
		SequenceHelper s = getInstance() ;
		// 
		// startUseNextValue() ;
		// restartUseNextValue(old, new) ;
		// acceptNextValue() ;
		// rollbackNextValue() ;
		
	}
}
