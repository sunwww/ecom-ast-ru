package ru.ecom.mis.ejb.domain.licence;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarHospitalBed;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Comment("Направление")
@Getter
@Setter
public class DirectionDocument extends InternalDocuments {
	/** Пред.госпитал. */
	@Comment("Пред.госпитал.")
	@OneToOne
	public WorkCalendarHospitalBed getPlanHospitalBed() {return planHospitalBed;}

	/** Пред.госпитал. */
	private WorkCalendarHospitalBed planHospitalBed;
}
