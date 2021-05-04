package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import java.sql.Time;

	/**
	 * Шаблон времен рабочего календаря
	 */
	@Comment("Шаблон времен рабочего календаря")
@Entity
	@Getter
	@Setter
public class WorkCalendarTimeInterval extends WorkCalendarTimePattern {

	/** Начиная с времени */
	private Time timeFrom;

	/** Заканчивая временем */
	private Time timeTo;

	/** Среднее время на визит */
	private Integer visitTime;

	/** Кол-во визитов */
	private Integer countVisits;
}