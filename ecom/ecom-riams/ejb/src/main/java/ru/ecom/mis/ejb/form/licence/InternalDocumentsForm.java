package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.licence.InternalDocuments;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = InternalDocuments.class)
@Comment("Внут. документы")
@WebTrail(comment = "Внут. документы", nameProperties = "id",list="entityParentList-doc_internal.do", view = "entityParentView-pres_prescriptList.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Document/Internal")
public class InternalDocumentsForm extends DocumentForm{
	/** Обоснование */
	@Comment("Обоснование")
	@Persist
	public String getHistory() {return theHistory;}
	public void setHistory(String aHistory) {theHistory = aHistory;}
	private String theHistory;

	/** Рекомендации */
	@Comment("Рекомендации")
	@Persist
	public String getRecommendations() {return theRecommendations;}
	public void setRecommendations(String aRecommendations) {theRecommendations = aRecommendations;}
	private String theRecommendations;

	/** Куда направлен */
	@Comment("Куда направлен")
	@Persist
	public Long getSentToLpu() {return theSentToLpu;}
	public void setSentToLpu(Long aSentToLpu) {theSentToLpu = aSentToLpu;}
	private Long theSentToLpu;

	/** Диагноз */
	@Comment("Диагноз")
	@Persist
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}
	private String theDiagnosis;

	/** Код диагноза */
	@Comment("Код диагноза")
	@Persist
	public Long getIdc10() {return theIdc10;}
	public void setIdc10(Long aIdc10) {theIdc10 = aIdc10;}
	private Long theIdc10;

	/** Цель биологического исследования */
	@Comment("Цель биологического исследования")
	@Persist
	public Long getObjectBiologAnalysis() {
		return theObjectBiologAnalysis;
	}
	public void setObjectBiologAnalysis(Long aObjectAnalysis) {
		theObjectBiologAnalysis = aObjectAnalysis;
	}
	private Long theObjectBiologAnalysis;

	/** Исследование */
	@Comment("Исследование")
	@Persist
	public Long getBiologAnalysis() {
		return theBiologAnalysis;
	}
	public void setBiologAnalysis(Long aBiologAnalysis) {
		theBiologAnalysis = aBiologAnalysis;
	}
	private Long theBiologAnalysis;

	/** Материал для микробилогического исследования */
	@Comment("Материал для микробилогического исследования")
	@Persist
	public Long getMaterialBiologAnalysis() {
		return theMaterialBiologAnalysis;
	}
	public void setMaterialBiologAnalysis(Long aMaterialBiologAnalysis) {theMaterialBiologAnalysis = aMaterialBiologAnalysis;}
	private Long theMaterialBiologAnalysis;
	
	/** Услуги */
	@Comment("Услуги")
	@Persist
	public String getServicies() {return theServicies;}
	public void setServicies(String aServicies) {theServicies = aServicies;}
	private String theServicies;

	/** Отделение */
	@Comment("Отделение")
	@Persist
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
	private Long theDepartment;

	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist
	public Long getBedType() {return theBedType;}
	public void setBedType(Long aBedType) {theBedType = aBedType;}
	private Long theBedType;

	
	/** Телефон */
	@Comment("Телефон")
	@Persist
	public String getPhonePatient() {return thePhonePatient;}
	public void setPhonePatient(String aPhonePatient) {thePhonePatient = aPhonePatient;}
	private String thePhonePatient;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}
	private Long theServiceStream;

	/** Планируемая дата с */
	@Comment("Планируемая дата с")
	@Persist @DateString @DoDateString
	public String getPlanDateFrom() {return thePlanDateFrom;}
	public void setPlanDateFrom(String aPlanDateFrom) {thePlanDateFrom = aPlanDateFrom;}
	private String thePlanDateFrom;

	/** Планируемая дата по */
	@Comment("Планируемая дата по")
	@Persist @DateString @DoDateString
	public String getPlanDateTo() {return thePlanDateTo;}
	public void setPlanDateTo(String aPlanDateTo) {thePlanDateTo = aPlanDateTo;}
	private String thePlanDateTo;

	/** Планируется операция? */
	@Comment("Планируется операция?")
	@Persist
	public Boolean getIsPlanOperation() {return theIsPlanOperation;}
	public void setIsPlanOperation(Boolean aIsPlanOperation) {theIsPlanOperation = aIsPlanOperation;}
	private Boolean theIsPlanOperation;
	
	/** Тип коек */
	@Comment("Тип коек")
	@Persist
	public Long getBedSubType() {return theBedSubType;}
	public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}
	private Long theBedSubType;

	/** Группа здоровья */
	@Comment("Группа здоровья")
	@Persist
	public Long getHealthGroup() {return theHealthGroup;}
	public void setHealthGroup(Long aHealthGroup) {theHealthGroup = aHealthGroup;}
	private Long theHealthGroup ;

}
