package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarHospitalBed;
import ru.ecom.mis.ejb.form.licence.DocumentPrepareCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.workcalendar.interceptor.WorkCalendarHospitalBedCreate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarHospitalBed.class)
@Comment("Предварительная госпитализация")
@WebTrail(comment = "Предварительная госпитализация", nameProperties= "id"
//, list="stac_planning_hospitalizations.do"
, view="entityView-stac_planHospitalByVisit.do")
@Parent(property="visit", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Planning")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(WorkCalendarHospitalBedCreate.class)
)
public class WorkCalendarHospitalBedByVisitForm extends WorkCalendarHospitalBedForm{
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
