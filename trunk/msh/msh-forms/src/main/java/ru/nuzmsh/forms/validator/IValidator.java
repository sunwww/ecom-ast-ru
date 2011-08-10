package ru.nuzmsh.forms.validator;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ESinev
 *         Date: 03.11.2005
 *         Time: 10:25:35
 */
public interface IValidator {

    void validate(final Object aValue, final Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException ;
}
