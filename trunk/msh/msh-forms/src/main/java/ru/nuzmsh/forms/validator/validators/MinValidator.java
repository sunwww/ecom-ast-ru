package ru.nuzmsh.forms.validator.validators;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;

public class MinValidator  implements IValidator {
	public void validate(final Object aValue, final Annotation aAnnotation, HttpServletRequest aRequest)
							throws ValidateException {
		if(aAnnotation instanceof Min && aValue!=null && !aValue.toString().trim().equals("")&& !aValue.toString().equals("0")) {
            Min min = (Min) aAnnotation ;
            int minValue = Integer.parseInt(min.value()) ;
            int value = Integer.parseInt(aValue.toString()) ;
            if(aValue!=null && value<minValue ) {
                throw new ValidateException("Поле должно не меньше "+aValue+" - "
                        + min.value()  +".") ;
            }
        }
    }
		
}

