package ru.ecom.poly.ejb.services;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
@Getter
@Setter
public class GroupByDate implements Serializable {
	/** Кол-во койко дней */
	private Long cntDays;
	/** Кол-во 2 */
	private Long cnt2;
	/** Кол-во 1 */
	private Long cnt1;
	/** Комментарий */
	private String comment;
	/** №№ */
	private long sn;
	/** Дата инфо */
	private String dateInfo;
	/** Кол-во */
	private Long cnt;
	/** Дата */
	private Date date;

}
