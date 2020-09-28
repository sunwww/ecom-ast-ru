package ru.ecom.ejb.sequence.service;

import ru.ecom.ejb.sequence.domain.SequenceInfo;
import ru.ecom.poly.ejb.domain.Medcard;

import javax.persistence.EntityManager;
import java.util.List;

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
	public String startUseNextValueNoCheck(String aTable, EntityManager aManager) {
		
			SequenceInfo info = getSequenceInfo(aTable.toLowerCase(), aManager) ;
			String value ;
			String addValue = addValue(aTable) ;
			String mayByNextValue = nextValue(addValue, info.getNextValue());
			value = mayByNextValue ;
			nextValue(addValue, mayByNextValue);
			info.setNextValue(addValue.equals("") ? value : value.replace(addValue, ""));
			
			return value ;
		
	}
	
	public String startUseNextValueNoCheck(String aTable, String aField, EntityManager aManager) {
		synchronized(SYNC) {
			SequenceInfo info = getSequenceInfo(aTable.toLowerCase(), aManager) ;
			String value ;
			String addValue = addValue(aTable) ;
			String mayByNextValue = nextValue(addValue, info.getNextValue());
			value = mayByNextValue ;
			nextValue(addValue, mayByNextValue);
			info.setNextValue(addValue.equals("") ? value : value.replace(addValue, ""));
			aManager.persist(info) ;
			return value ;
		}
	}
	
	private boolean checkExists(String aTable, String aField, String mayByNextValue, EntityManager aManager) {
		List<Medcard> list = aManager.createQuery("from "+aTable+" where "+aField+"=:number")
			.setParameter("number", mayByNextValue).getResultList();
		return !list.isEmpty();
	}

	private String nextValue(String aKey, String aOldValue) {
		if (!aKey.equals("")) {
			aOldValue = aOldValue.replaceFirst(aKey, "") ;
		}
		long l = Long.parseLong(aOldValue)+1;
		return aKey+l ;
	}
	private String addValue(String aKey) {
		return (aKey.equals("Patient"))? "Н":"" ;
	}
	
	private SequenceInfo getSequenceInfo(String aKey, EntityManager aManager) {
		List<SequenceInfo> list = aManager.createQuery("from SequenceInfo where uniqueName=:key")
			.setParameter("key", aKey).getResultList();
		if(!list.isEmpty()) {
			return list.iterator().next();
		} else {
			//aManager.createNativeQuery("insert into SequenceInfo (uniqueName, nextValue) values (:key, '1')",SequenceInfo.class).setParameter("key", aKey);
			SequenceInfo info = new SequenceInfo() ;
			info.setUniqueName(aKey) ;
			info.setNextValue("0");
			aManager.persist(info) ;
			
			return info ;
			//throw new IllegalStateException(aKey+" ошибка  = "+list.size()) ;
		}
		
	}
}
