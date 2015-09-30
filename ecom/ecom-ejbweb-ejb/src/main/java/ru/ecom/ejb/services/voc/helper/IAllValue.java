package ru.ecom.ejb.services.voc.helper;

import java.util.Collection;

import ru.ecom.ejb.services.voc.VocContext;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

/**
 * @author esinev 18.08.2006 1:10:49
 */
public interface IAllValue {

    Collection<VocValue> listAll(AllValueContext aContext) ;
    
    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, AllValueContext aContext) throws VocServiceException ;
    void destroy() ;
}
