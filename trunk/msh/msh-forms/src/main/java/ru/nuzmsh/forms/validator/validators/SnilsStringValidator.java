package ru.nuzmsh.forms.validator.validators;

import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;


public class SnilsStringValidator implements IValidator{

	public void validate(Object aValue, Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
		// TODO Auto-generated method stub
            if(aValue!=null && aValue instanceof String) {
                String str = (String) aValue ;

                if(str!=null && !str.trim().equals("")) {
            		Pattern snilsReg = Pattern.compile("[0-9]{3}[-]{1}[0-9]{3}[-]{1}[0-9]{3}[ ]{1}[0-9]{2}") ;
            		Matcher m = snilsReg.matcher(str);
            		if (!m.find()) {
            			throw new ValidateException("Неправильно введен СНИЛС. Форма ввода СНИЛС: NNN-NNN-NNN NN. Например: 111-111-111 11");
            		}
                }
            }
	}
}
