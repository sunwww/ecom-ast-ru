package ru.nuzmsh.forms.validator.validators;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;
import ru.nuzmsh.util.format.DateFormat;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ESinev
 *         Date: 03.11.2005
 *         Time: 11:30:23
 */
public class DateStringValidator implements IValidator {

    public void validate(final Object aValue, final Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
       
            if(aValue!=null && aValue instanceof String) {
                String str = (String) aValue ;
                str = str.replace(',', '.') ;
                if(!"".equals(str.trim())) {
                	Pattern dateReg = Pattern.compile("([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-1]{1})[.]{1}([0]{1}[1-9]{1}|[1]{1}[012]{1})[.]{1}[0-9]{4}") ;
            		Matcher m = dateReg.matcher(str);
            		if (!m.find()) {
            			throw new ValidateException("Неправильно введена дата. Форма ввода даты: ДД.ММ.ГГГГ. Например: 31.12.2004");
            		}
            		 try {
            			 DateFormat.parseDate(str);
        	        } catch (Exception e) {
        	            throw new ValidateException(e.getMessage());
        	        }
                }
            }
    }
}
