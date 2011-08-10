package ru.nuzmsh.forms.validator.transforms;

import ru.nuzmsh.forms.validator.ITransform;
import org.apache.log4j.Logger;

/**
 * Замена запятых на точку
 */
public class DoTimeStringTransform implements ITransform {

    private final static Logger LOG = Logger.getLogger(DoTimeStringTransform.class) ;

    public Object transform(Object aObject) {
        //LOG.debug("aObject = " + aObject);
        //System.out.println("aObject = " + aObject);
        if(aObject!=null && aObject instanceof String) {
            return ((String) aObject).replace(',', '.') ;
        }
        return null;
    }
}
