package ru.nuzmsh.forms.validator.transforms;

import ru.nuzmsh.forms.validator.ITransform;

/**
 * Замена запятых на точку
 */
public class DoTimeStringTransform implements ITransform {


    public Object transform(Object aObject) {
        //LOG.debug("aObject = " + aObject);
        //System.out.println("aObject = " + aObject);
        if(aObject instanceof String) {
            return ((String) aObject).replace(',', '.') ;
        }
        return null;
    }
}
