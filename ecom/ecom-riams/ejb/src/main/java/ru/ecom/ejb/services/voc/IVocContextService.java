package ru.ecom.ejb.services.voc;

import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import java.util.Collection;

public interface IVocContextService {
	String getNameById(String aId, String aVocName, VocAdditional aAdditional, VocContext aContext ) throws VocServiceException ;

    Collection<VocValue> findVocValueByQuery(String aVocName,String aQuery,int aCount, VocAdditional aAdditional, VocContext aContext ) throws VocServiceException ;

    Collection<VocValue> findVocValuePrevious(String aVocName,String aId,int aCount,VocAdditional aAdditional, VocContext aContext ) throws VocServiceException ;

    Collection<VocValue> findVocValueNext(String aVocName, String aId,int aCount,VocAdditional aAdditional, VocContext aContext ) ;
}
