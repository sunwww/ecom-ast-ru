package ru.ecom.mis.ejb.form.birth;

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
public class ConfinedInspectionForm extends InspectionForm {
	/** Общее состояние */
	@Comment("Общее состояние")
	@Persist @Required
	public Long getCondition() {return theCondition;}
	public void setCondition(Long aCondition) {theCondition = aCondition;}

	
	/** Состояние молочных желез */
	@Comment("Состояние молочных желез")
	@Persist 
	public String getMammariesCondition() {return theMammariesCondition;}
	public void setMammariesCondition(String aMammariesCondition) {theMammariesCondition = aMammariesCondition;}


	/** Высота матки */
	@Comment("Высота матки")
	@Persist
	public Integer getUterusHeight() {return theUterusHeight;}
	public void setUterusHeight(Integer aUterusHeight) {theUterusHeight = aUterusHeight;}

	
	/** Лохии */
	@Comment("Лохии")
	@Persist
	public String getLochia() {return theLochia;}
	public void setLochia(String aLochia) {theLochia = aLochia;}

	
	/** Функции мочевого пузыря */
	@Comment("Функции мочевого пузыря")
	@Persist
	public String getUrinaryBladderFunctions() {return theUrinaryBladderFunctions;}
	public void setUrinaryBladderFunctions(String aUrinaryBladderFunctions) {theUrinaryBladderFunctions = aUrinaryBladderFunctions;}

	
	/** Функции кишечника */
	@Comment("Функции кишечника")
	@Persist
	public String getIntestinesFunctions() {return theIntestinesFunctions;}
	public void setIntestinesFunctions(String aIntestinesFunctions) {theIntestinesFunctions = aIntestinesFunctions;}

	
	/** Назначения */
	@Comment("Назначения")
	@Persist
	public String getPrescriptions() {return thePrescriptions;}
	public void setPrescriptions(String aPrescriptions) {thePrescriptions = aPrescriptions;}

	/** Общее состояние */
	private Long theCondition;
	/** Состояние молочных желез */
	private String theMammariesCondition;
	/** Высота матки */
	private Integer theUterusHeight;
	/** Лохии */
	private String theLochia;
	/** Функции мочевого пузыря */
	private String theUrinaryBladderFunctions;
	/** Функции кишечника */
	private String theIntestinesFunctions;
	/** Назначения */
	private String thePrescriptions;

}
