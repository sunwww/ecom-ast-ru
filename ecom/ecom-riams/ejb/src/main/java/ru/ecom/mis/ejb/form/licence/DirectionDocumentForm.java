package ru.ecom.mis.ejb.form.licence;

import lombok.Setter;
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
@Setter
public class DirectionDocumentForm extends InternalDocumentsForm {
	/** Диагноз */
	@Comment("Диагноз")
	@Persist @Required
	public String getDiagnosis() {return diagnosis;}

	/** Код диагноза */
	@Comment("Код диагноза")
	@Persist @Required
	public Long getIdc10() {return idc10;}

	/** Код диагноза */
	private Long idc10;
	/** Диагноз */
	private String diagnosis;
	
	/** Отделение */
	@Comment("Отделение")
	@Persist
	public Long getDepartment() {return department;}

	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist @Required
	public Long getBedType() {return bedType;}

	/** Профиль коек */
	private Long bedType;
	/** Отделение */
	private Long department;
	
	/** Телефон */
	@Comment("Телефон")
	@Persist @Required
	public String getPhonePatient() {return phonePatient;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return serviceStream;}

	
	/** Поток обслуживания */
	private Long serviceStream;
	/** Телефон */
	private String phonePatient;
	/** Куда направлен */
	@Comment("Куда направлен")
	@Persist @Required
	public Long getSentToLpu() {return sentToLpu;}
	/** Куда направлен */
	private Long sentToLpu;
	/** Планируемая дата с */
	@Comment("Планируемая дата с")
	@Persist @DateString @DoDateString @Required
	public String getPlanDateFrom() {return planDateFrom;}

	/** Планируемая дата по */
	@Comment("Планируемая дата по")
	@Persist @DateString @DoDateString
	public String getPlanDateTo() {return planDateTo;}

	/** Планируемая дата по */
	private String planDateTo;
	/** Планируемая дата с */
	private String planDateFrom;
	
	/** Планируется операция? */
	@Comment("Планируется операция?")
	@Persist
	public Boolean getIsPlanOperation() {return isPlanOperation;}

	/** Планируется операция? */
	private Boolean isPlanOperation;
	
	/** Создать запись о план. госпитализаций */
	@Comment("Создать запись о план. госпитализаций")
	public Boolean getIsCreatePlanHosp() {return isCreatePlanHosp;}

	/** Создать запись о план. госпитализаций */
	private Boolean isCreatePlanHosp;
	
	/** Пред.госпитал. */
	@Comment("Пред.госпитал.")
	@Persist
	public Long getPlanHospitalBed() {return planHospitalBed;}

	/** Пред.госпитал. */
	private Long planHospitalBed;
	/** Тип коек */
	@Comment("Тип коек")
	@Persist @Required
	public Long getBedSubType() {return bedSubType;}

	/** Тип коек */
	private Long bedSubType;
}
