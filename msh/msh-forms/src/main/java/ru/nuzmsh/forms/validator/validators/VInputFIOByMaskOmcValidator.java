package ru.nuzmsh.forms.validator.validators;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;

public class VInputFIOByMaskOmcValidator implements IValidator {

    public void validate(final Object aValue, final Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
        String str ;
        if(aValue!=null) {
            if(aValue instanceof String) {
                str = (String) aValue ;
            } else {
                str = aValue.toString() ;
            }
            int len = str.length() ;
            int cnt = 0;
            for(int i=0; i<len; i++) {
                char ch = str.charAt(i) ;
                if (ch=='-' || ch==' ')  {
                	++cnt ;  
                } 
            }
            if (cnt == len)  throw new ValidateException("Можно ставить любую одну букву, кроме пробела или символа \"-\". <u>Например:</u> \"Х\"");
            if ((len>1) && str.charAt(0)=='-') throw new ValidateException("Строку необходимо начинать с любой символа, кроме символа \"-\"") ; 
            if (cnt>1)  throw new ValidateException("Разрешен ввод только одного пробела или символа \"-\". <u>Например:</u> \"САН-МАРК\", \"САН МАРК\"");
            if (str.endsWith("-") ||str.endsWith(" ") ) throw new ValidateException("Строку можно заканчивать любым символом, кроме пробела или символа \"-\"");
            if (str.indexOf(".")!=-1) throw new ValidateException("Запрещен ввод знаков препинания, кроме \"-\" и пробела. <u>Например:</u> \"САН-МАРК\", \"САН МАРК\"");
            if (str.indexOf("@")!=-1) throw new ValidateException("Запрещен ввод знаков препинания, кроме \"-\" и пробела. <u>Например:</u> \"САН-МАРК\", \"САН МАРК\"");
            if (str.indexOf("#")!=-1) throw new ValidateException("Запрещен ввод знаков препинания, кроме \"-\" и пробела. <u>Например:</u> \"САН-МАРК\", \"САН МАРК\"");
            if (str.indexOf("$")!=-1) throw new ValidateException("Запрещен ввод знаков препинания, кроме \"-\" и пробела. <u>Например:</u> \"САН-МАРК\", \"САН МАРК\"");
            if (str.indexOf("%")!=-1) throw new ValidateException("Запрещен ввод знаков препинания, кроме \"-\" и пробела. <u>Например:</u> \"САН-МАРК\", \"САН МАРК\"");
            if (str.indexOf("^")!=-1) throw new ValidateException("Запрещен ввод знаков препинания, кроме \"-\" и пробела. <u>Например:</u> \"САН-МАРК\", \"САН МАРК\"");
            if (str.indexOf(":")!=-1) throw new ValidateException("Запрещен ввод знаков препинания, кроме \"-\" и пробела. <u>Например:</u> \"САН-МАРК\", \"САН МАРК\"");
            if (str.indexOf(";")!=-1) throw new ValidateException("Запрещен ввод знаков препинания, кроме \"-\" и пробела. <u>Например:</u> \"САН-МАРК\", \"САН МАРК\"");
            if (str.indexOf("'")!=-1) throw new ValidateException("Запрещен ввод знаков препинания, кроме \"-\" и пробела. <u>Например:</u> \"САН-МАРК\", \"САН МАРК\"");
            if (str.indexOf("+")!=-1) throw new ValidateException("Запрещен ввод знаков препинания, кроме \"-\" и пробела. <u>Например:</u> \"САН-МАРК\", \"САН МАРК\"");
        }

    }

}
