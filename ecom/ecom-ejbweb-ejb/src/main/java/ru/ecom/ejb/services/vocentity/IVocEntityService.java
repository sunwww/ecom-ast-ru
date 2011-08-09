package ru.ecom.ejb.services.vocentity;

import java.util.Collection;

public interface IVocEntityService {

	Object setVocEntityValue(String aClassname, String aId, String aProperty, String aValue) ;
	
	Collection<VocEntityInfo> listVocEntities() ;
	
	VocEntityInfo getVocEntityInfo(String aClassname) ;
	
	String loadJsonValues(String aClassname, int aFrom, int aCount, String aOrderBy, boolean aAscending) ;
	
	void removeVocEntity(String aClassname, String aId) ;
	
}
