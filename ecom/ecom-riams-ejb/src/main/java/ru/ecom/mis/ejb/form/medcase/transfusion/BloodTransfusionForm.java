package ru.ecom.mis.ejb.form.medcase.transfusion;

import javax.persistence.OneToOne;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.BloodTransfusion;
import ru.ecom.mis.ejb.domain.patient.voc.VocYesNo;
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
	
	/** Проверка группы крови пациента */
	@Comment("Проверка группы крови пациента")
	@Persist @Required
	public Long getPatBloodGroupCheck() {return thePatBloodGroupCheck;}
	public void setPatBloodGroupCheck(Long aPatBloodGroupCheck) {thePatBloodGroupCheck = aPatBloodGroupCheck;}

	/** Проверка группы крови препарата */
	@Comment("Проверка группы крови препарата")
	@Persist @Required
	public Long getPrepBloodGroupCheck() {return thePrepBloodGroupCheck;}
	public void setPrepBloodGroupCheck(Long aPrepBloodGroupCheck) {thePrepBloodGroupCheck = aPrepBloodGroupCheck;}

	/** Проверка группы крови препарата */
	private Long thePrepBloodGroupCheck;
	/** Проверка группы крови пациента */
	private Long thePatBloodGroupCheck;
	
	/** Макроскопическая оценка крови */
	@Comment("Макроскопическая оценка крови")
	@OneToOne
	public Long getMacroBall() {return theMacroBall;}
	public void setMacroBall(Long aMacroBall) {theMacroBall = aMacroBall;}

	/** Макроскопическая оценка крови */
	private Long theMacroBall;
	
	/** Наблюдение сразу после переливания */
	@Comment("Наблюдение сразу после переливания")
	public TransfusionMonitoringForm getMonitorForm0() {return theMonitorForm0;}
	public void setMonitorForm0(TransfusionMonitoringForm aMonitorForm0) {theMonitorForm0 = aMonitorForm0;}

	/** Наблюдение через 1 час */
	@Comment("Наблюдение через 1 час")
	public TransfusionMonitoringForm getMonitorForm1() {return theMonitorForm1;}
	public void setMonitorForm1(TransfusionMonitoringForm aMonitorForm1) {theMonitorForm1 = aMonitorForm1;}

	/** Наблюдение через 2 часа */
	@Comment("Наблюдение через 2 часа")
	public TransfusionMonitoringForm getMonitorForm2() {return theMonitorForm2;}
	public void setMonitorForm2(TransfusionMonitoringForm aMonitorForm2) {theMonitorForm2 = aMonitorForm2;}

	/** Наблюдение через 2 часа */
	private TransfusionMonitoringForm theMonitorForm2 = new TransfusionMonitoringForm();
	/** Наблюдение через 1 час */
	private TransfusionMonitoringForm theMonitorForm1 = new TransfusionMonitoringForm();
	/** Наблюдение сразу после переливания */
	private TransfusionMonitoringForm theMonitorForm0 = new TransfusionMonitoringForm();
	
	/** Реактив 1 */
	@Comment("Реактив 1")
	public TransfusionReagentForm getReagentForm1() {return theReagentForm1;}
	public void setReagentForm1(TransfusionReagentForm aReagentForm1) {theReagentForm1 = aReagentForm1;}

	/** Реактив 2 */
	@Comment("Реактив 2")
	public TransfusionReagentForm getReagentForm2() {return theReagentForm2;}
	public void setReagentForm2(TransfusionReagentForm aReagentForm2) {theReagentForm2 = aReagentForm2;}

	/** Реактив 2 */
	private TransfusionReagentForm theReagentForm2 = new TransfusionReagentForm();
	/** Реактив 1 */
	private TransfusionReagentForm theReagentForm1 = new TransfusionReagentForm();
}
