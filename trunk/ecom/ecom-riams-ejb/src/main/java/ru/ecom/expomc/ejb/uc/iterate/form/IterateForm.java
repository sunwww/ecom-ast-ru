package ru.ecom.expomc.ejb.uc.iterate.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.uc.iterate.domain.Iterate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= Iterate.class)
@Comment("Перебор")
@EntityFormSecurityPrefix("/Policy/Exp/FillTime")
@WebTrail(comment="Перебор", nameProperties="name", view="entityView-exp_iterate.do")
public class IterateForm extends IdEntityForm {
	
	/** Название */
	@Comment("Название")
	@Persist @Required
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Строка запроса */
	@Comment("Строка запроса")
	@Persist @Required
	public String getHqlString() {
		return theHqlString;
	}

	public void setHqlString(String aHqlString) {
		theHqlString = aHqlString;
	}

	/** Скрипт для выполнения */
	@Comment("Скрипт для выполнения")
	@Persist @Required
	public String getIterateScript() {
		return theIterateScript;
	}

	public void setIterateScript(String aIterateScript) {
		theIterateScript = aIterateScript;
	}

	/** Инициализация */
	@Comment("Инициализация")
	@Persist
	public String getInitScript() {
		return theInitScript;
	}

	public void setInitScript(String aInitScript) {
		theInitScript = aInitScript;
	}

	/** Инициализация */
	private String theInitScript;
	/** Скрипт для выполнения */
	private String theIterateScript;
	/** Строка запроса */
	private String theHqlString;
	/** Название */
	private String theName;
}
