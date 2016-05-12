package ru.ecom.mis.ejb.domain.workcalendar.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник типов четности (с начала года, с начала месяца, с начала работы)
	 */
	@Comment("Справочник типов четности (с начала года, с начала месяца, с начала работы)")
@Entity
@Table(schema="SQLUser")
public class VocWorkCalendarParity extends VocBaseEntity{
}
