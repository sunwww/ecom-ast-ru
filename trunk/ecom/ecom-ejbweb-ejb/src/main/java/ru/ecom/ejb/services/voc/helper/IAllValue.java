package ru.ecom.ejb.services.voc.helper;

import java.util.Collection;

import ru.nuzmsh.util.voc.VocValue;

/**
 * @author esinev 18.08.2006 1:10:49
 */
public interface IAllValue {

    Collection<VocValue> listAll(AllValueContext aContext) ;

    void destroy() ;
}
