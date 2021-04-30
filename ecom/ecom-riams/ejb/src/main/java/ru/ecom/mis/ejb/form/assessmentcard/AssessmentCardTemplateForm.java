package ru.ecom.mis.ejb.form.assessmentcard;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.assessmentcard.AssessmentCardTemplate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= AssessmentCardTemplate.class)
@Comment("Вид карты оценки")
@WebTrail(comment = "Вид карты оценки", nameProperties= "name", view="entityView-mis_assessmentCardTemplate.do" ,list = "entityList-mis_assessmentCardTemplate.do")
@EntityFormSecurityPrefix("/Policy/Mis/AssessmentCard")
@Setter
public class AssessmentCardTemplateForm extends IdEntityForm{
	
	/** Название */
	@Comment("Название")
	@Required @Persist
	public String getName() {return name;}
	/** Название */
	private String name;
	
	
	/** Группировать параметры по группам */
	@Comment("Группировать параметры по группам")
	@Persist
	public Boolean	getIsGroupParameters() {return isGroupParameters;}
	/** Группировать параметры по группам */
	private Boolean isGroupParameters;	

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {return createDate;}

	/** Пользователь создавший запись */
	@Comment("Пользователь создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}

	/** Пользователь создавший запись */
	private String createUsername;
	/** Дата создания */
	private String createDate;

}
