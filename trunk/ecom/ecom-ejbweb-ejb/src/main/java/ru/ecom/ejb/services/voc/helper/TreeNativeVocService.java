package ru.ecom.ejb.services.voc.helper;

import java.util.Collection;

import ru.ecom.ejb.services.voc.IVocContextService;
import ru.ecom.ejb.services.voc.IVocServiceManagement;
import ru.ecom.ejb.services.voc.VocContext;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

public class TreeNativeVocService implements IVocContextService {
	public TreeNativeVocService(String aTreeField, String aFrom, String [] aNames, String aJoin
			, String aQueried, String aParent, String aOrder) {
		theTreeField = aTreeField ;
		theFrom = aFrom ;
		theNames = aNames ;
		theJoin = aJoin ;
		theQueried = aQueried ;
		theParent = aParent ;
		theOrder = aOrder ;
		
	}
	public String getFrom() {
		return theFrom ;
	}
	private final String theTreeField ;
	private final String theFrom ;
	private final String[] theNames ;
	private final String theJoin ;
	private final String theQueried ;
	private final String theParent ;
	private final String theOrder ;
	public Collection<VocValue> findVocValueByQuery(String aVocName, String aQuery, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	public Collection<VocValue> findVocValueNext(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
		// TODO Auto-generated method stub
		return null;
	}
	public Collection<VocValue> findVocValuePrevious(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	public String getNameById(String aId, String aVocName, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
