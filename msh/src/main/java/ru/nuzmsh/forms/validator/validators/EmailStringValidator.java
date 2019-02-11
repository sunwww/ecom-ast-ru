package ru.nuzmsh.forms.validator.validators;

import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;

public class EmailStringValidator implements IValidator {
	public void validate(Object aValue, Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
		if(aValue!=null && aValue instanceof String) {
            String str = (String) aValue ;
            str = str.replace(',', '.') ;
            if(str!=null && !str.trim().equals("")) {
        		Pattern integReg = Pattern.compile("[a-zA-Z]{1}[a-zA-Z\\d\u002E\u005F]+@([a-zA-Z]+\u002E){1,2}((net)|(com)|(org)(ru))") ;
        		Matcher m = integReg.matcher(str);
        		
        		if (!m.find()) {
        			throw new ValidateException("Неправильно введено число: "+str+". Форма ввода числа: NNN.NN. Например: 111.11 или 111.1 или 111");
        		}
            }
        }
		
	}
}
