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
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarHospitalBed.class)
@Comment("Предварительная госпитализация")
@WebTrail(comment = "Предварительная госпитализация", nameProperties= "id"
, view="entityView-stac_planHospitalByVisit.do")
@Parent(property="visit", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Planning")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(WorkCalendarHospitalBedCreate.class)
)
@ACreateInterceptors(
		@AEntityFormInterceptor(WorkCalendarHospitalBedSave.class)
)
@Setter
public class WorkCalendarHospitalBedByVisitForm extends WorkCalendarHospitalBedForm {

	/** Отделение */
	@Comment("Отделение")
	@Persist @Required
	public Long getDepartment() {return department;}
	private Long department;

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
	private Long workFunction;
}