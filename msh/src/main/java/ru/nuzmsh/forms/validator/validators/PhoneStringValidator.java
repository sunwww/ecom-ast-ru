package ru.nuzmsh.forms.validator.validators;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneStringValidator implements IValidator {
	public void validate(Object aValue, Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
		if(aValue instanceof String) {
            String str = (String) aValue ;
            if(!str.trim().equals("")) {
        		Pattern integReg = Pattern.compile("[a-zA-Zа-яА-Я]") ;
        		Matcher m = integReg.matcher(str);
        		
        		if (m.find()) {
        			throw new ValidateException("В номере телефона не должно быть букв. ");
        		}
            }
        }
		
	}
}