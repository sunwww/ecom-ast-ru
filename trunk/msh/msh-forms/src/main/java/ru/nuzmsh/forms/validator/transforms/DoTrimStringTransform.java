package ru.nuzmsh.forms.validator.transforms;

import ru.nuzmsh.forms.validator.ITransform;

/**
 * @author ESinev
 *         Date: 03.11.2005
 *         Time: 11:26:35
 */
public class DoTrimStringTransform implements ITransform {

    public Object transform(Object aObject) {
        if(aObject!=null && aObject instanceof String) {
            return ((String) aObject).trim() ;
        }
        return null;
    }
}
