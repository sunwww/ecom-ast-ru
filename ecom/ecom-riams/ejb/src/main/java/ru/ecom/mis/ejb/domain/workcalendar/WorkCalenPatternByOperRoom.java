package ru.ecom.mis.ejb.domain.workcalendar;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.lpu.OperatingRoom;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class WorkCalenPatternByOperRoom extends WorkCalendarPattern {
	/** Операционная */
	@Comment("Операционная")
	@OneToOne
	public OperatingRoom getOperatingRoom() {
		return theOperatingRoom;
	}

	public void setOperatingRoom(OperatingRoom aNAME) {
		theOperatingRoom = aNAME;
	}

	/** Операционная */
	private OperatingRoom theOperatingRoom;
}
