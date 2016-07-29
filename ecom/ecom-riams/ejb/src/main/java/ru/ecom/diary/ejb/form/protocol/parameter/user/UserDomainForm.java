package ru.ecom.diary.ejb.form.protocol.parameter.user;

import ru.ecom.diary.ejb.domain.protocol.parameter.user.UserDomain;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= UserDomain.class)
@Comment("Пользовательский справочник")
@WebTrail(comment = "Пользовательский справочник", nameProperties= "name", view="entityView-diary_userDomain.do")
@EntityFormSecurityPrefix("/Policy/Diary/User/Domain")
public class UserDomainForm extends IdEntityForm{
	
	/** Код */
	@Comment("Код")
	@Persist 
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}
	/** Код */
	private String theCode;
	
	/** Название */
	@Comment("Название")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	/** Название */
	private String theName;
}
