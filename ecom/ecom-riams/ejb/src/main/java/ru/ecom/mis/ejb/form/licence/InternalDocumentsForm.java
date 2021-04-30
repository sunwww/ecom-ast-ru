package ru.ecom.mis.ejb.form.licence;

import lombok.Setter;
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
@Setter
public class InternalDocumentsForm extends DocumentForm{
	/** Обоснование */
	@Comment("Обоснование")
	@Persist
	public String getHistory() {return history;}
	private String history;

	/** Рекомендации */
	@Comment("Рекомендации")
	@Persist
	public String getRecommendations() {return recommendations;}
	private String recommendations;

	/** Куда направлен */
	@Comment("Куда направлен")
	@Persist
	public Long getSentToLpu() {return sentToLpu;}
	private Long sentToLpu;

	/** Диагноз */
	@Comment("Диагноз")
	@Persist
	public String getDiagnosis() {return diagnosis;}
	private String diagnosis;

	/** Код диагноза */
	@Comment("Код диагноза")
	@Persist
	public Long getIdc10() {return idc10;}
	private Long idc10;

	/** Цель биологического исследования */
	@Comment("Цель биологического исследования")
	@Persist
	public Long getObjectBiologAnalysis() {
		return objectBiologAnalysis;
	}
	private Long objectBiologAnalysis;

	/** Исследование */
	@Comment("Исследование")
	@Persist
	public Long getBiologAnalysis() {
		return biologAnalysis;
	}
	private Long biologAnalysis;

	/** Материал для микробилогического исследования */
	@Comment("Материал для микробилогического исследования")
	@Persist
	public Long getMaterialBiologAnalysis() {
		return materialBiologAnalysis;
	}
	private Long materialBiologAnalysis;
	
	/** Услуги */
	@Comment("Услуги")
	@Persist
	public String getServicies() {return servicies;}
	private String servicies;

	/** Отделение */
	@Comment("Отделение")
	@Persist
	public Long getDepartment() {return department;}
	private Long department;

	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist
	public Long getBedType() {return bedType;}
	private Long bedType;

	
	/** Телефон */
	@Comment("Телефон")
	@Persist
	public String getPhonePatient() {return phonePatient;}
	private String phonePatient;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist
	public Long getServiceStream() {return serviceStream;}
	private Long serviceStream;

	/** Планируемая дата с */
	@Comment("Планируемая дата с")
	@Persist @DateString @DoDateString
	public String getPlanDateFrom() {return planDateFrom;}
	private String planDateFrom;

	/** Планируемая дата по */
	@Comment("Планируемая дата по")
	@Persist @DateString @DoDateString
	public String getPlanDateTo() {return planDateTo;}
	private String planDateTo;

	/** Планируется операция? */
	@Comment("Планируется операция?")
	@Persist
	public Boolean getIsPlanOperation() {return isPlanOperation;}
	private Boolean isPlanOperation;
	
	/** Тип коек */
	@Comment("Тип коек")
	@Persist
	public Long getBedSubType() {return bedSubType;}
	private Long bedSubType;

	/** Группа здоровья */
	@Comment("Группа здоровья")
	@Persist
	public Long getHealthGroup() {return healthGroup;}
	private Long healthGroup ;

}
