package ru.nuzmsh.util.voc;

import java.util.Collection;

/**
 * Работа со справочниками
 */
public interface IVocService {

    String getNameById(String aId, String aVocName, VocAdditional aAdditional ) throws VocServiceException ;

    Collection<VocValue> findVocValueByQuery(String aVocName,String aQuery,int aCount, VocAdditional aAdditional ) throws VocServiceException ;

    Collection<VocValue> findVocValuePrevious(String aVocName,String aId,int aCount,VocAdditional aAdditional ) throws VocServiceException ;

    Collection<VocValue> findVocValueNext(String aVocName, String aId,int aCount,VocAdditional aAdditional ) ;

}
