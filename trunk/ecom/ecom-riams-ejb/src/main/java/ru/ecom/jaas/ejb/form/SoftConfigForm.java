package ru.ecom.jaas.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.jaas.ejb.domain.SoftConfig;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Настройка приложения
 * @author stkacheva
 *
 */
@EntityForm
@EntityFormPersistance(clazz= SoftConfig.class)
@Comment("Настройка приложения")
@EntityFormSecurityPrefix("/Policy/Config/Soft")
@WebTrail(comment="Настройка приложения. Ключ", nameProperties="key", view="entityView-sec_softConfig.do")
public class SoftConfigForm extends IdEntityForm{
	/** Ключ */
	@Comment("Ключ")
	@Persist @Required
	public String getKey() {return theKey;}
	public void setKey(String aKey) {theKey = aKey;}

	/** Значение */
	@Comment("Значение")
	@Persist @Required
	public String getKeyValue() {return theKeyValue;}
	public void setKeyValue(String aValue) {theKeyValue = aValue;}

	/** Описание */
	@Comment("Описание")
	@Persist 
	public String getDescription() {return theDescription;}
	public void setDescription(String aDescription) {theDescription = aDescription;}

	
	/** Описание */
	private String theDescription;
	/** Значение */
	private String theKeyValue;
	/** Ключ */
	private String theKey;
}
