package ru.ecom.diary.ejb.form.protocol.parameter.user;

import lombok.Getter;
import lombok.Setter;
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
@Setter
public class UserValueForm extends IdEntityForm {
	
	/** Использовать значение по умолчанию */
	@Comment("Использовать значение по умолчанию")
	@Persist
	public Boolean getUseByDefault() {return useByDefault;}
	/** Использовать значение по умолчанию */
	private Boolean useByDefault;
	
	/** Значение */
	@Comment("Значение")
	@Persist @Required
	public String getName() {return name;}

	/** Пользовательский справочник */
	@Comment("Пользовательский справочник")
	@Persist @Required
	public Long getDomain() {return domain;}

	/** Пользовательский справочник */
	private Long domain;
	/** Значение */
	private String name;
	
	/** Кол-во баллов */
	@Comment("Кол-во баллов")
	@Persist
	public String getCntBall() {return cntBall;}

	/** Кол-во баллов */
	private String cntBall;
	
	/** Сообщение при выборе значения */
	@Comment("Сообщение при выборе значения")
	@Persist
	public String getComment() {return comment;}
	/** Сообщение при выборе значения */
	private String comment;

}
