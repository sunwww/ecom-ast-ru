package ru.ecom.diary.ejb.form.protocol;

import lombok.Getter;
import lombok.Setter;
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
@Setter
public class FormInputProtocolForm extends IdEntityForm {
	/** Шаблон протокола */
	@Comment("Шаблон протокола")
	@Persist
	public Long getProtocol() {return protocol;}

	/** Список параметров формы */
	@Comment("Список параметров формы")
	public String getParameters() {return parameters;	}

	/** Название */
	@Comment("Название")
	@Persist
	public String getName() {return name;}

	/** Название */
	private String name;
	/** Список параметров формы */
	private String parameters;
	/** Шаблон протокола */
	private Long protocol;
}
