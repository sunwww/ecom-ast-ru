package ru.nuzmsh.forms.validator.transforms;

import ru.nuzmsh.forms.validator.ITransform;

public class DoIntegerStringTransform implements ITransform {
    public Object transform(Object aObject) {
        if(aObject!=null && aObject instanceof String) {
            return ((String) aObject).replace(',', '.') ;
        }
        return null;
    }
}
