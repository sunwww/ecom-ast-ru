package ru.nuzmsh.forms.validator.validators;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;

import java.lang.annotation.Annotation;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * Проверка: все символы, кроме латинских
 */
public class VInputNonLatValidator implements IValidator {

    public void validate(final Object aValue, final Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
        String str ;
        if(aValue!=null) {
            if(aValue instanceof String) {
                str = (String) aValue ;
            } else {
                str = aValue.toString() ;
            }
            int len = str.length() ;
            for(int i=0; i<len; i++) {
                char ch = str.charAt(i) ;
                if(theHash.get(ch)!=null) {
                    throw new ValidateException("Не допускается ввод латинских символов: "+ch);
                }
            }
        }

    }


    private final HashMap<Character,Character> theHash = new HashMap<Character, Character>();

    public VInputNonLatValidator() {
        for(char i='a'; i<='z'; i++) {
            theHash.put(i, i) ;
        }
        for(char i='A'; i<='Z'; i++) {
            theHash.put(i, i) ;
        }
    }
}
