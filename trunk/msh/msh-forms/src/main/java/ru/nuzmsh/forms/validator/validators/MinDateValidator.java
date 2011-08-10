package ru.nuzmsh.forms.validator.validators;

import ru.nuzmsh.forms.validator.IValidator;
import ru.nuzmsh.forms.validator.ValidateException;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

/**
 * Минимальная дата
 */
public class MinDateValidator implements IValidator {
	public void validate(final Object aValue, final Annotation aAnnotation, HttpServletRequest aRequest)
			throws ValidateException {
		if (aValue instanceof String) {
			String str = (String) aValue;
			if (!StringUtil.isNullOrEmpty(str)) {
				try {
					Date inputedDate = DateFormat.parseDate((String) aValue);
					MinDate minDate = (MinDate) aAnnotation;
					if (StringUtil.isNullOrEmpty(minDate.value())) {
						throw new ValidateException(
								"Нет минимальной даты в аннотации");
					}
					Date annotationDate = DateFormat.parseDate(minDate.value());
					if (inputedDate.getTime() < annotationDate.getTime()) {
						throw new ValidateException(new StringBuilder().append(
								"Дата должна быть не менее ").append(
								minDate.value()).toString());
					}
				} catch (ParseException e) {
					throw new ValidateException("Неправильно введена дата");
				}
			}
		}
	}

}
