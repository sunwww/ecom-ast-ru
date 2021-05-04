package ru.ecom.mis.ejb.service.worker;

import java.io.Serializable;
import java.sql.Time;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@SuppressWarnings("serial")
@Getter
@Setter
public class TableSpetialistByDay implements Serializable{
	/** ИД */
	private Long id;
	/** Порядковый номер */
	private long sn;
	/** Время */
	private Time time;
	/** Время текст*/
	private String timeString;
}
