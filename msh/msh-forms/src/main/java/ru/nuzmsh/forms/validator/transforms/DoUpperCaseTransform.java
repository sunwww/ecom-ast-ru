package ru.nuzmsh.forms.validator.transforms;

import ru.nuzmsh.forms.validator.ITransform;

/**
 * Преобразует в верхний регистр
 */
public class DoUpperCaseTransform implements ITransform {

    public Object transform(Object aObject) {
        if(aObject !=null && aObject instanceof String) {
            String str = (String) aObject ;
            return str.toUpperCase() ;
        }
        return null;
    }
}
