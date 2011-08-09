package ru.ecom.mis.ejb.form.diet;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.diet.MealWorkerHealthControl;

/**
 * Контроль здоровья работающих с пищей (УФ 2-лп)
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz = MealWorkerHealthControl.class)
@Comment("Контроль здоровья работающих с пищей  (УФ 2-лп)")
@WebTrail(comment = "Контроль здоровья работающих с пищей (УФ 2-лп)", nameProperties= "id", view="entityView-diet_mealWorkerHealthControl.do")
@Parent(property="diet", parentForm=DietForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/InvalidFood/MealWorkerHealthControl")
public class MealWorkerHealthControlForm extends IdEntityForm{
	
	
	/** Диета */
	@Comment("Диета")
	@Persist
	public Long getDiet() {
		return theDiet;
	}

	public void setDiet(Long aDiet) {
		theDiet = aDiet;
	}

	/** Диета */
	private Long theDiet;
	
	/** Информация о диетологе */
	@Comment("Информация о диетологе")
	@Persist
	public String getDietitianInfo() {
		return theDietitianInfo;
	}

	public void setDietitianInfo(String aDietitianInfo) {
		
	}
	
	/** Информация о диетологе */	
	private String theDietitianInfo;
	
	/** Диетолог */
	@Comment("Диетолог")
	@Persist
	public Long getDietitian() {
		return theDietitian;
	}

	public void setDietitian(Long aDietitian) {
		theDietitian = aDietitian;
	}

	/** Диетолог */
	private Long theDietitian;
	
	/** Диагноз нетрудоспособности по уходу */
	@Comment("Диагноз нетрудоспособности по уходу")
	@Persist
	public String getNursingDisabilityDiagnosis() {
		return theNursingDisabilityDiagnosis;
	}

	public void setNursingDisabilityDiagnosis(String aNursingDisabilityDiagnosis) {
		theNursingDisabilityDiagnosis = aNursingDisabilityDiagnosis;
	}

	/** Диагноз нетрудоспособности по уходу */
	private String theNursingDisabilityDiagnosis;
	
	/** Отсутствие гнойных заболеваний */
	@Comment("Отсутствие гнойных заболеваний")
	@Persist
	public Boolean getPurulentDiseaseAbsence() {
		return thePurulentDiseaseAbsence;
	}

	public void setPurulentDiseaseAbsence(Boolean aPurulentDiseaseAbsence) {
		thePurulentDiseaseAbsence = aPurulentDiseaseAbsence;
	}

	/** Отсутствие гнойных заболеваний */
	private Boolean thePurulentDiseaseAbsence;
	
	/** Отсутствие острых кишечных заболеваний */
	@Comment("Отсутствие острых кишечных заболеваний")
	@Persist
	public Boolean getAcuteIntestinalDiseaseAbsence() {
		return theAcuteIntestinalDiseaseAbsence;
	}

	public void setAcuteIntestinalDiseaseAbsence(Boolean aAcuteIntestinalDiseaseAbsence) {
		theAcuteIntestinalDiseaseAbsence = aAcuteIntestinalDiseaseAbsence;
	}

	/** Отсутствие острых кишечных заболеваний */
	private Boolean theAcuteIntestinalDiseaseAbsence;
	
	
	/** Информация о контроллируемом сотруднике */
	@Comment("Информация о контроллируемом сотруднике")
	@Persist
	public String getControllingWorkerInfo() {
		return  theControllingWorkerInfo;
	}

	public void setControllingWorkerInfo(String aControllingWorkerInfo) {
		
	}
	/** Информация о контроллируемом сотруднике */	
	private String theControllingWorkerInfo;

	
	/** Контролируемый сотрудник */
	@Comment("Контролируемый сотрудник")
	@Persist
	public Long getControllingWorker() {
		return theControllingWorker;
	}

	public void setControllingWorker(Long aControllingWorker) {
		theControllingWorker = aControllingWorker;
	}

	/** Контролируемый сотрудник */
	private Long theControllingWorker;
	
	/** Дата контроля */
	@Comment("Дата контроля")
	@Persist
	@DateString
	@DoDateString
	public String getControlDate() {
		return theControlDate;
	}

	public void setControlDate(String aControlDate) {
		theControlDate = aControlDate;
	}

	/** Дата контроля */
	private String theControlDate;

}
