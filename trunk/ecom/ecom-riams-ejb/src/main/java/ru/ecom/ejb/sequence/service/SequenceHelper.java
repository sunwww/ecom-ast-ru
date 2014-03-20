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
	
	public String startUseNextValue(String aTable, String aField, EntityManager aManager) {
		synchronized(SYNC) {
			SequenceInfo info = getSequenceInfo(aTable.toLowerCase(), aManager) ;
			String value = null;
			String addValue = addValue(aTable) ;
			String mayByNextValue = nextValue(addValue, info.getNextValue());
			//String table = aKey ;
			//String field = aField ;
			
			for(int i=0; i<1000; i++) {
				
				if(checkExists(aTable,aField, mayByNextValue, aManager)) {
					
				} else {
					value = mayByNextValue ;
					break ;
				}
				mayByNextValue = nextValue(addValue, mayByNextValue);
			}
			if(value==null) {
				throw new IllegalStateException("Ошибка получение следу") ;
			}
			info.setNextValue(addValue.equals("")?value:value.replace(addValue, ""));
			
			return value ;
		}
	}
	public String startUseNextValueNoCheck(String aTable, String aField, EntityManager aManager) {
		synchronized(SYNC) {
			SequenceInfo info = getSequenceInfo(aTable.toLowerCase(), aManager) ;
			String value = null;
			String addValue = addValue(aTable) ;
			String mayByNextValue = nextValue(addValue, info.getNextValue());
			
			
			value = mayByNextValue ;
			mayByNextValue = nextValue(addValue, mayByNextValue);
		
			if(value==null) {
				throw new IllegalStateException("Ошибка получение следу") ;
			}
			info.setNextValue(addValue.equals("")?value:value.replace(addValue, ""));
			
			return value ;
		}
	}
	
	private boolean checkExists(String aTable, String aField, String mayByNextValue, EntityManager aManager) {
		List<Medcard> list = aManager.createQuery("from "+aTable+" where "+aField+"=:number")
			.setParameter("number", mayByNextValue).getResultList();
		return list.size()!=0;
	}

	private String nextValue(String aKey, String aOldValue) {
		System.out.println("oldValue="+aOldValue) ;
		if (!aKey.equals("")) {
			aOldValue = aOldValue.replaceFirst(aKey, "") ;
		}
		long l = Long.parseLong(aOldValue)+1;
		String key = aKey+String.valueOf(l) ;
		return String.valueOf(key) ;
	}
	private String addValue(String aKey) {
		return (aKey.equals("Patient"))? "Н":"" ;
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
