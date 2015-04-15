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
            		if (!isRigthSnils(str)) {
            			throw new ValidateException("Неправильно введен СНИЛС. Не сходится контрольная сумма");
            		}
            		
                }
            }
	}
	public boolean isRigthSnils (String aSnils) {
				String currentSnils = aSnils.replace("-", "").replace(" ", "");
				int snilsCN = Integer.valueOf(currentSnils.substring(currentSnils.length()-2));
				System.out.println(snilsCN);
				if (currentSnils.length()!=11) {
					return false;
				} 
				int sum = 0;
				int controlNumber = 0;
				for (int i=0;i<9;i++) {
					sum+=Integer.valueOf(currentSnils.substring(i, i+1))*(9-i);
				}
				if (sum>101) {
					sum=sum%101;			
				}
				if (sum<100) {
					controlNumber=sum;
				} 
				if (snilsCN==controlNumber) {
					System.out.println("==isRightSnilsValidator, СНИЛС верный! "+currentSnils);
					return true;	
				} else {
					System.out.println("==isRightSnilsValidator, Неправильный СНИЛС!"+currentSnils);
					return false;
				}
	}
}
