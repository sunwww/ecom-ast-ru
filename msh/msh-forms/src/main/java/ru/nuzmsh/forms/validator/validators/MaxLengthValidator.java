package ru.nuzmsh.forms.validator.validators;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

/**
 * Максимальная длина поля
 */
public class MaxLengthValidator implements IValidator {
    public void validate(final Object aValue, final Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
        if(aAnnotation instanceof MaxLength) {
            MaxLength maxLength = (MaxLength) aAnnotation ;
            if(aValue!=null && !aValue.equals("") && aValue.toString().length() > maxLength.value()) {
                throw new ValidateException("Поле должно содержать не более "
                        + maxLength.value()  +" символов.") ;
            }
        }
    }
}
