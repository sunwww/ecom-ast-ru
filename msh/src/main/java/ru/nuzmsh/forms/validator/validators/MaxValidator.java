package ru.nuzmsh.forms.validator.validators;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

public class MaxValidator implements IValidator {
	public void validate(final Object aValue, final Annotation aAnnotation, HttpServletRequest aRequest)
		throws ValidateException {
		if(aAnnotation instanceof Max && aValue!=null && !aValue.toString().equals("")&& !aValue.toString().equals("0")) {
			Max max = (Max) aAnnotation ;
			int maxValue = Integer.parseInt(max.value()) ;
			int value = Integer.parseInt(aValue.toString()) ;
			if(value>maxValue ) {
				throw new ValidateException("Поле должно не больше "
						+ max.value()  +".") ;
			}
		}
	}
}