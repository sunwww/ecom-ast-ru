package ru.nuzmsh.forms.validator.validators;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

/**
 * Минимальная длина поля
 */
public class MinLengthValidator implements IValidator {
    public void validate(final Object aValue, final Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
        if(aAnnotation instanceof MinLength) {
            MinLength minLength = (MinLength) aAnnotation ;
            if(aValue!=null && !aValue.equals("")&& aValue.toString().length() < minLength.value()) {
                throw new ValidateException("Поле должно содержать не менее "
                        + minLength.value()  +" символов.") ;
            }
            // todo

        }
    }

}
