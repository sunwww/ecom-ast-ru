package ru.nuzmsh.forms.validator.validators;

import java.lang.annotation.Annotation;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;

public class IntegerStringValidator  implements IValidator{

	public void validate(Object aValue, Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
		if(aValue!=null && aValue instanceof String) {
            String str = (String) aValue ;
            str = str.replace(',', '.') ;
            if(str!=null && !str.trim().equals("")) {
            	Pattern integReg = Pattern.compile("[\\d]{1,}[\\.]{0,1}[\\d]{0,}$") ;
        		//Pattern integReg = Pattern.compile("([0-9]{0,})|([0-9]{0,}[\\.]{1}[0-9]{0,})") ;
        		Matcher m = integReg.matcher(str);
    			//throw new ValidateException("Неправильно введено число: "+str+". Форма ввода числа: NNN.NN. Например: 111.11 или 111.1 или 111"+"----"+m +"----"+ m.find()+"----"+new Random().nextInt(15));
        		if (!m.find()) {
        			throw new ValidateException("Неправильно введено число: "+str+". Форма ввода числа: NNN.NN. Например: 111.11 или 111.1 или 111");
        		}
            }
        }
		
	}

}
