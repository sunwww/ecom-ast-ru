package ru.ecom.ejb.services.voc.helper;

import ru.ecom.ejb.services.voc.IVocContextService;
import ru.ecom.ejb.services.voc.VocContext;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import java.util.Collection;

public class TreeNativeVocService implements IVocContextService {
	public TreeNativeVocService(String aTreeField, String aFrom, String [] aNames, String aJoin
			, String aQueried, String aParent, String aOrder) {
		treeField = aTreeField ;
		from = aFrom ;
		names = aNames ;
		join = aJoin ;
		queried = aQueried ;
		parent = aParent ;
		order = aOrder ;
		
	}
	public String getFrom() {
		return from ;
	}
	private final String treeField ;
	private final String from ;
	private final String[] names ;
	private final String join ;
	private final String queried ;
	private final String parent ;
	private final String order ;
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
