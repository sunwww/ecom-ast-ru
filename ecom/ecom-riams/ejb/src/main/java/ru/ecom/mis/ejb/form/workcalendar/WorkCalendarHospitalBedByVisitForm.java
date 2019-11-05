package ru.ecom.mis.ejb.form.workcalendar;

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
public class WorkCalendarHospitalBedByVisitForm extends WorkCalendarHospitalBedForm {

	/** Отделение */
	@Comment("Отделение")
	@Persist @Required
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
	private Long theDepartment;

	/** Показания для госпитализации */
	@Comment("Показания для госпитализации")
	@Persist
	public Long getIndicationToHosp() {return theIndicationToHosp;}
	public void setIndicationToHosp(Long aIndicationToHosp) {theIndicationToHosp = aIndicationToHosp;}
	/** Показания для госпитализации */
	private Long theIndicationToHosp;
	
	/** Палата */
	@Comment("Палата")
	@Persist 
	public Long getHospitalRoom() {return theHospitalRoom;}
	public void setHospitalRoom(Long aHospitalRoom) {theHospitalRoom = aHospitalRoom;}
	/** Палата */
	private Long theHospitalRoom;
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist @Required
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Рабочая функция */
	private Long theWorkFunction;
}