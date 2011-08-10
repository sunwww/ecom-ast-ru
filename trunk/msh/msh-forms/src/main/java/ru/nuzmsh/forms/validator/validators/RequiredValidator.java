package ru.nuzmsh.forms.validator.validators;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;
import ru.nuzmsh.util.StringUtil;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ESinev
 *         Date: 03.11.2005
 *         Time: 10:27:45
 */
public class RequiredValidator implements IValidator {

    public void validate(final Object aValue, final Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
        //System.out.println("----------"+aValue.getClass());
    	if(aValue==null) {
            te() ;
        } else if(aValue instanceof String && StringUtil.isNullOrEmpty((String) aValue)) {
            te() ;
        } else if(aValue instanceof Number && ((Number)aValue).doubleValue()==0) {
        	//System.out.println("number") ;
        	te() ;
        }  else if("".equals(aValue.toString().trim())) {
            te() ;
        }
    }

    private static void te() throws ValidateException {
        throw new ValidateException("Поле является обязательным") ;
    }
}
