package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.ConfinedInspection;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= ConfinedInspection.class)
@Comment("Осмотр родильницы")
@WebTrail(comment = "Осмотр родильницы", nameProperties= "id", view="entitySubclassView-preg_inspection.do" ,list = "entityParentList-preg_inspection.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Inspection/Confined")
@Setter
public class ConfinedInspectionForm extends InspectionForm {
	/** Общее состояние */
	@Comment("Общее состояние")
	@Persist @Required
	public Long getCondition() {return condition;}

	
	/** Состояние молочных желез */
	@Comment("Состояние молочных желез")
	@Persist 
	public String getMammariesCondition() {return mammariesCondition;}


	/** Высота матки */
	@Comment("Высота матки")
	@Persist
	public Integer getUterusHeight() {return uterusHeight;}

	
	/** Лохии */
	@Comment("Лохии")
	@Persist
	public String getLochia() {return lochia;}

	
	/** Функции мочевого пузыря */
	@Comment("Функции мочевого пузыря")
	@Persist
	public String getUrinaryBladderFunctions() {return urinaryBladderFunctions;}

	
	/** Функции кишечника */
	@Comment("Функции кишечника")
	@Persist
	public String getIntestinesFunctions() {return intestinesFunctions;}

	
	/** Назначения */
	@Comment("Назначения")
	@Persist
	public String getPrescriptions() {return prescriptions;}

	/** Общее состояние */
	private Long condition;
	/** Состояние молочных желез */
	private String mammariesCondition;
	/** Высота матки */
	private Integer uterusHeight;
	/** Лохии */
	private String lochia;
	/** Функции мочевого пузыря */
	private String urinaryBladderFunctions;
	/** Функции кишечника */
	private String intestinesFunctions;
	/** Назначения */
	private String prescriptions;

}
