package ru.ecom.diary.ejb.form.protocol.parameter.user;

import ru.ecom.diary.ejb.domain.protocol.parameter.user.UserValue;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= UserValue.class)
@Comment("Значение справочника")
@WebTrail(comment = "Значение", nameProperties= "name", view="entityParentView-diary_userValue.do")
@EntityFormSecurityPrefix("/Policy/Diary/User/Domain/Value")
@Parent(property="domain", parentForm=UserDomainForm.class)
public class UserValueForm extends IdEntityForm {
	/** Значение */
	@Comment("Значение")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aValue) {theName = aValue;}

	/** Пользовательский справочник */
	@Comment("Пользовательский справочник")
	@Persist @Required
	public Long getDomain() {return theDomain;}
	public void setDomain(Long aDomain) {theDomain = aDomain;	}

	/** Пользовательский справочник */
	private Long theDomain;
	/** Значение */
	private String theName;

}
