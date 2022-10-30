package ru.nuzmsh.forms.validator.validators;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

public class MkbValidator implements IValidator {

	public void validate(Object aValue, Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
	}


}
