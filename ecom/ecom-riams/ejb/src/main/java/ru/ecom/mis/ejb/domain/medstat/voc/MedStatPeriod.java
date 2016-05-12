package ru.ecom.mis.ejb.domain.medstat.voc;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Статистический период Медстата
 * @author azviagin
 *
 */

@Comment("Статистический период Медстата")
@Entity
@Table(name="stat_periods",schema="SQLUser")
public class MedStatPeriod extends VocBaseEntity{
	
	/** Дата начала */
	@Comment("Дата начала")
	@Column(name="start_date")
	public Date getStartDate() {
		return theStartDate;
	}

	public void setStartDate(Date aStartDate) {
		theStartDate = aStartDate;
	}

	/** Дата начала */
	private Date theStartDate;
	
	/** Дата окончания */
	@Comment("Дата окончания")
	@Column(name="finish_date")
	public Date getFinishDate() {
		return theFinishDate;
	}

	public void setFinishDate(Date aFinishDate) {
		theFinishDate = aFinishDate;
	}

	/** Дата окончания */
	private Date theFinishDate;
	
	/** Время начала */
	@Comment("Время окончания")
	@Column(name="start_time")
	public Time getStartTime() {
		return theStartTime;
	}

	public void setStartTime(Time aStartTime) {
		theStartTime = aStartTime;
	}

	/** Время начала */
	private Time theStartTime;
	
	/** Время окончания */
	@Comment("Время окончания")
	@Column(name="finish_time")
	public Time getFinishTime() {
		return theFinishTime;
	}

	public void setFinishTime(Time aFinishTime) {
		theFinishTime = aFinishTime;
	}

	/** Время окончания */
	private Time theFinishTime;
	
	/** Описание */
	@Comment("Описание")
	@Column(name="description")
	public String getDescription() {
		return theDescription;
	}

	public void setDescription(String aDescription) {
		theDescription = aDescription;
	}

	/** Описание */
	private String theDescription;
	

}
