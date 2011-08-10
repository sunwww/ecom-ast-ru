package ru.nuzmsh.forms.validator.validators;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;
import ru.nuzmsh.util.format.DateFormat;

import java.text.ParseException;
import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ESinev
 *         Date: 03.11.2005
 *         Time: 11:30:23
 */
public class TimeStringValidator implements IValidator {

    public void validate(final Object aValue, final Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
        try {
            if(aValue!=null && aValue instanceof String) {
                String str = (String) aValue ;
                if(!"".equals(str.trim())) {
                    DateFormat.parseTime(str);
                }
            }
        } catch (ParseException e) {
            throw new ValidateException("Неправильно введено время");
        }
    }
}
