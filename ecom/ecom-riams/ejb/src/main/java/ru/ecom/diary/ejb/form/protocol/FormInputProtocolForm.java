package ru.ecom.diary.ejb.form.protocol;

import ru.ecom.diary.ejb.domain.protocol.parameter.FormInputProtocol;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz= FormInputProtocol.class)
@Comment("Форма ввода для шаблона протокола")
@WebTrail(comment = "Форма ввода для шаблона протокола", nameProperties= "id", view="diary_templateView.do")
@EntityFormSecurityPrefix("/Policy/Diary/Template/FormInputProtocol")
@Parent(property="protocol", parentForm=TemplateProtocolForm.class)
public class FormInputProtocolForm extends IdEntityForm {
	/** Шаблон протокола */
	@Comment("Шаблон протокола")
	@Persist
	public Long getProtocol() {return theProtocol;}
	public void setProtocol(Long aProtocol) {theProtocol = aProtocol;}

	/** Список параметров формы */
	@Comment("Список параметров формы")
	public String getParameters() {return theParameters;	}
	public void setParameters(String aParameters) {theParameters = aParameters;}

	/** Название */
	@Comment("Название")
	@Persist
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Название */
	private String theName;
	/** Список параметров формы */
	private String theParameters;
	/** Шаблон протокола */
	private Long theProtocol;
}
