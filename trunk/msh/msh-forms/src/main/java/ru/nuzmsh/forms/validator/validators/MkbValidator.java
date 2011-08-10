package ru.nuzmsh.forms.validator.validators;

import java.lang.annotation.Annotation;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.mis.ejb.service.validator.IMkbValidatorService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;

public class MkbValidator implements IValidator {

	public void validate(Object aValue, Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
		if(aAnnotation instanceof Mkb) {
        	if (aValue instanceof Number && ((Number)aValue).intValue()>0) {
	            Mkb mkb = (Mkb) aAnnotation ;
	            String clazz= mkb.clazz();
	            String field= mkb.field();
	            try {
					IMkbValidatorService service =  Injection.find(aRequest).getService(IMkbValidatorService.class);
					Boolean check ;
					try {
						check = service.validatePoint(((Number)aValue).toString(), clazz, field);
					} catch (Exception e) {
						check = true ;
						System.out.println(e) ;
					}
					if (check!=null && check==false)  throw new ValidateException("Запрет на ввод без уточнения диагноза") ;
				} catch (NamingException e) {
					throw new ValidateException("Не возможно подключить сервис") ;
				}
        	}
        }
		
	}

    
}
