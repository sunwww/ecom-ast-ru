package ru.ecom.mis.ejb.form.workcalendar;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarHospitalBed;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.workcalendar.interceptor.WorkCalendarHospitalBedCreate;
import ru.ecom.mis.ejb.form.workcalendar.interceptor.WorkCalendarHospitalBedSave;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarHospitalBed.class)
@Comment("Предварительная госпитализация")
@WebTrail(comment = "Предварительная госпитализация", nameProperties= "id"
, view="entityView-stac_planHospitalByHosp.do")
@Parent(property="visit", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Planning")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(WorkCalendarHospitalBedCreate.class)
)
@ACreateInterceptors(
		@AEntityFormInterceptor(WorkCalendarHospitalBedSave.class)
)
@Setter
public class WorkCalendarHospitalBedByHospForm extends WorkCalendarHospitalBedForm {

	/** Предполагаемая дата начала госпитализации */
	@Comment("Предполагаемая дата начала госпитализации")
	@Persist @DateString
	@DoDateString
	public String getDateFrom() {return dateFrom;}
	/** Предполагаемая дата начала госпитализации */
	private String dateFrom;

	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist
	public Long getBedType() {return bedType;}
	/** Профиль коек */
	private Long bedType;

	/** Тип коек */
	@Comment("Тип коек")
	@Persist
	public Long getBedSubType() {return bedSubType;}
	/** Тип коек */
	private Long bedSubType;

	/** Показания для госпитализации */
	@Comment("Показания для госпитализации")
	@Persist
	public Long getIndicationToHosp() {return indicationToHosp;}
	/** Показания для госпитализации */
	private Long indicationToHosp;
	
	/** Палата */
	@Comment("Палата")
	@Persist 
	public Long getHospitalRoom() {return hospitalRoom;}
	/** Палата */
	private Long hospitalRoom;

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist @Required
	public Long getWorkFunction() {return workFunction;}
	/** Рабочая функция */
	private Long workFunction;

	/** Отделение */
	@Comment("Отделение")
	@Persist
	public Long getDepartment() {return department;}
	/** Отделение */
	private Long department;
}