package ru.ecom.mis.web.action.util;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;

public class HelpForm extends BaseValidatorForm {
	/** Код */
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Текст */
	public String getContextText() {return theContextText;}
	public void setContextText(String aContext) {theContextText = aContext;}

	/** Следующая страница */
	@Comment("Следующая страница")
	public String getNextUrl() {return theNextUrl;}
	public void setNextUrl(String aNext) {theNextUrl = aNext;}

	/** Следующая страница */
	private String theNextUrl;
	/** Текст */
	private String theContextText;
	/** Код */
	private String theCode;
}
