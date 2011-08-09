package ru.ecom.mis.ejb.form.medcase.transfusion;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.BloodTransfusion;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= BloodTransfusion.class)
@Comment("Переливание крови")
@WebTrail(comment = "Переливание крови", nameProperties= "id", view="entityParentView-trans_blood.do",list = "entityParentList-trans_transfusion.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Transfusion/Blood")
public class BloodTransfusionForm extends TransfusionForm{
	/** Препарат крови */
	@Comment("Препарат крови")
	@Persist @Required
	public Long getBloodPreparation() {return theBloodPreparation;}
	public void setBloodPreparation(Long aBloodPreparation) {theBloodPreparation = aBloodPreparation;}

	/** Группа крови пациента */
	@Comment("Группа крови пациента")
	@Persist @Required
	public Long getPatientBloodGroup() {return thePatientBloodGroup;}
	public void setPatientBloodGroup(Long aPatientBloodGroup) {thePatientBloodGroup = aPatientBloodGroup;}
	
	/** Резус-фактор пациента */
	@Comment("Резус-фактор пациента")
	@Persist @Required
	public Long getPatientRhesusFactor() {return thePatientRhesusFactor;}
	public void setPatientRhesusFactor(Long aPatientRhesusFactor) {thePatientRhesusFactor = aPatientRhesusFactor;}
	
	/** Группа крови препарата */
	@Comment("Группа крови препарата")
	@Persist @Required
	public Long getPreparationBloodGroup() {return thePreparationBloodGroup;}
	public void setPreparationBloodGroup(Long aPreparationBloodGroup) {thePreparationBloodGroup = aPreparationBloodGroup;}

	/** Резус-фактор препарата */
	@Comment("Резус-фактор препарата")
	@Persist @Required
	public Long getPreparationRhesusFactor() {return thePreparationRhesusFactor;}
	public void setPreparationRhesusFactor(Long aPreparationRhesusFactor) {thePreparationRhesusFactor = aPreparationRhesusFactor;}

	/** Донор */
	@Comment("Донор")
	@Persist
	public String getDonor() {return theDonor;}
	public void setDonor(String aDonor) {theDonor = aDonor;}

	/** Донор */
	private String theDonor;
	/** Препарат крови */
	private Long theBloodPreparation;
	/** Группа крови пациента */
	private Long thePatientBloodGroup;
	/** Резус-фактор пациента */
	private Long thePatientRhesusFactor;
	/** Группа крови препарата */
	private Long thePreparationBloodGroup;
	/** Резус-фактор препарата */
	private Long thePreparationRhesusFactor;
}
