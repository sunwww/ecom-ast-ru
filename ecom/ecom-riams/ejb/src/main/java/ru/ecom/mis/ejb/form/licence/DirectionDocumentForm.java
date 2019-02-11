package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.licence.DirectionDocument;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = DirectionDocument.class)
@Comment("Направления")
@WebTrail(comment = "Направления", nameProperties = "id"
, view = "entityParentView-doc_direction.do"
, shortView="entitySubclassShortView-doc_direction.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Document/Internal/Direction")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(DocumentPrepareCreateInterceptor.class)
)
public class DirectionDocumentForm extends InternalDocumentsForm {
	/** Диагноз */
	@Comment("Диагноз")
	@Persist @Required
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}

	/** Код диагноза */
	@Comment("Код диагноза")
	@Persist @Required
	public Long getIdc10() {return theIdc10;}
	public void setIdc10(Long aIdc10) {theIdc10 = aIdc10;}

	/** Код диагноза */
	private Long theIdc10;
	/** Диагноз */
	private String theDiagnosis;
	
	/** Отделение */
	@Comment("Отделение")
	@Persist
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist @Required
	public Long getBedType() {return theBedType;}
	public void setBedType(Long aBedType) {theBedType = aBedType;}

	/** Профиль коек */
	private Long theBedType;
	/** Отделение */
	private Long theDepartment;
	
	/** Телефон */
	@Comment("Телефон")
	@Persist @Required
	public String getPhonePatient() {return thePhonePatient;}
	public void setPhonePatient(String aPhonePatient) {thePhonePatient = aPhonePatient;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}

	
	/** Поток обслуживания */
	private Long theServiceStream;
	/** Телефон */
	private String thePhonePatient;
	/** Куда направлен */
	@Comment("Куда направлен")
	@Persist @Required
	public Long getSentToLpu() {return theSentToLpu;}
	public void setSentToLpu(Long aSentToLpu) {theSentToLpu = aSentToLpu;}
	/** Куда направлен */
	private Long theSentToLpu;
	/** Планируемая дата с */
	@Comment("Планируемая дата с")
	@Persist @DateString @DoDateString @Required
	public String getPlanDateFrom() {return thePlanDateFrom;}
	public void setPlanDateFrom(String aPlanDateFrom) {thePlanDateFrom = aPlanDateFrom;}

	/** Планируемая дата по */
	@Comment("Планируемая дата по")
	@Persist @DateString @DoDateString
	public String getPlanDateTo() {return thePlanDateTo;}
	public void setPlanDateTo(String aPlanDateTo) {thePlanDateTo = aPlanDateTo;}

	/** Планируемая дата по */
	private String thePlanDateTo;
	/** Планируемая дата с */
	private String thePlanDateFrom;
	
	/** Планируется операция? */
	@Comment("Планируется операция?")
	@Persist
	public Boolean getIsPlanOperation() {return theIsPlanOperation;}
	public void setIsPlanOperation(Boolean aIsPlanOperation) {theIsPlanOperation = aIsPlanOperation;}

	/** Планируется операция? */
	private Boolean theIsPlanOperation;
	
	/** Создать запись о план. госпитализаций */
	@Comment("Создать запись о план. госпитализаций")
	public Boolean getIsCreatePlanHosp() {return theIsCreatePlanHosp;}
	public void setIsCreatePlanHosp(Boolean aIsCreatePlanHosp) {theIsCreatePlanHosp = aIsCreatePlanHosp;}

	/** Создать запись о план. госпитализаций */
	private Boolean theIsCreatePlanHosp;
	
	/** Пред.госпитал. */
	@Comment("Пред.госпитал.")
	@Persist
	public Long getPlanHospitalBed() {return thePlanHospitalBed;}
	public void setPlanHospitalBed(Long aPlanHospitalBed) {thePlanHospitalBed = aPlanHospitalBed;}

	/** Пред.госпитал. */
	private Long thePlanHospitalBed;
	/** Тип коек */
	@Comment("Тип коек")
	@Persist @Required
	public Long getBedSubType() {return theBedSubType;}
	public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}

	/** Тип коек */
	private Long theBedSubType;
}
