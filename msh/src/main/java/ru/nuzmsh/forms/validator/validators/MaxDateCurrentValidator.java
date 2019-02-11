package ru.nuzmsh.forms.validator.validators;

import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

public class MaxDateCurrentValidator  implements IValidator{

	public void validate(Object aValue, Annotation aAnnotation, HttpServletRequest aRequest) throws ValidateException {
		if (aValue instanceof String) {
			String str = (String) aValue;
			if (!StringUtil.isNullOrEmpty(str)) {
				try {
					Date inputedDate = DateFormat.parseDate((String) aValue);
					Date maxDate = new Date();
					if (inputedDate.getTime() > maxDate.getTime()) {
						throw new ValidateException(new StringBuilder().append(
								"Дата должна быть не больше текущей").toString());
					}
				} catch (ParseException e) {
					throw new ValidateException("Неправильно введена дата");
				}
			}
		}
	}
}