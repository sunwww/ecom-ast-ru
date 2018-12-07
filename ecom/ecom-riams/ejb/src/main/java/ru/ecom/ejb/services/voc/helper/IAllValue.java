package ru.ecom.ejb.services.voc.helper;

import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import java.util.Collection;

/**
 * @author esinev 18.08.2006 1:10:49
 */
public interface IAllValue {

    Collection<VocValue> listAll(AllValueContext aContext) ;
    
    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, AllValueContext aContext) throws VocServiceException ;
    void destroy() ;
    //public Collection<VocValue> findVocValueByQuery(String aVocName, String aQuery, int aCount, VocAdditional aAdditional, AllValueContext aContext) throws VocServiceException ;
}
