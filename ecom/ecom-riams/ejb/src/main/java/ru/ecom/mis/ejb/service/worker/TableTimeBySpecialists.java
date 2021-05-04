package ru.ecom.mis.ejb.service.worker;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@SuppressWarnings("serial")
@Getter
@Setter
public class TableTimeBySpecialists implements Serializable {
	/** Дата */
	private Date date;
	/** CalendarDayId */
	private Long calendarDayId;
	/** Время максимальное */
	private String timeMax;
	/** Время минимальное */
	private String timeMin;
	/** Дата */
	private String dateString;
	/** ИД специалиста */
	private Long specialistId;
	/** Порядковый номер */
	private long sn;

}
