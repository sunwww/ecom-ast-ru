package ru.ecom.mis.ejb.domain.licence;

import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarHospitalBed;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Comment("Направление")
public class DirectionDocument extends InternalDocuments {
	/** Пред.госпитал. */
	@Comment("Пред.госпитал.")
	@OneToOne
	public WorkCalendarHospitalBed getPlanHospitalBed() {return thePlanHospitalBed;}
	public void setPlanHospitalBed(WorkCalendarHospitalBed aPlanHospitalBed) {thePlanHospitalBed = aPlanHospitalBed;}

	/** Пред.госпитал. */
	private WorkCalendarHospitalBed thePlanHospitalBed;
}
