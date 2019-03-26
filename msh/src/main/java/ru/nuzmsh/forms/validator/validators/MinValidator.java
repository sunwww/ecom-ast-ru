package ru.nuzmsh.forms.validator.validators;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

public class MinValidator  implements IValidator {
	public void validate(final Object aValue, final Annotation aAnnotation, HttpServletRequest aRequest)
							throws ValidateException {
		if(aAnnotation instanceof Min && aValue!=null && !aValue.toString().trim().equals("")&& !aValue.toString().equals("0")) {
            Min min = (Min) aAnnotation ;
            int minValue = Integer.parseInt(min.value()) ;
            int value = Integer.parseInt(aValue.toString()) ;
            if(value<minValue ) {
                throw new ValidateException("Поле должно не меньше "+aValue+" - "
                        + min.value()  +".") ;
            }
        }
    }
		
}

