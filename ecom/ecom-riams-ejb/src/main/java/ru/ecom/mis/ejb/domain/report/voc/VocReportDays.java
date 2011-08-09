package ru.ecom.mis.ejb.domain.report.voc;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Дни расчета отчета
 * @author azviagin
 *
 */

@Comment("Дни расчета отчета")
@Entity
@Table(schema="SQLUser")
public class VocReportDays extends VocBaseEntity{
	
	/** День начала */
	@Comment("День начала")
	public Integer getStartDay() {
		return theStartDay;
	}

	public void setStartDay(Integer aStartDay) {
		theStartDay = aStartDay;
	}

	/** День начала */
	private Integer theStartDay;
	
	/** День окончания */
	@Comment("День окончани")
	public Integer getFinishDay() {
		return theFinishDay;
	}

	public void setFinishDay(Integer aFinishDay) {
		theFinishDay = aFinishDay;
	}

	/** День окончания */
	private Integer theFinishDay;
	
	/** Время начала */
	@Comment("Время начала")
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
	public Time getFinishTime() {
		return theFinishTime;
	}

	public void setFinishTime(Time aFinishTime) {
		theFinishTime = aFinishTime;
	}

	/** Время окончания */
	private Time theFinishTime;
	
	/** День начала расчета */
	@Comment("День начала расчета")
	public Integer getCalculationDate() {
		return theCalculationDate;
	}

	public void setCalculationDate(Integer aCalculationDate) {
		theCalculationDate = aCalculationDate;
	}

	/** День начала расчета */
	private Integer theCalculationDate;
	
	/** Время начала расчета */
	@Comment("Время начала расчета")
	public Time getCalculationTime() {
		return theCalculationTime;
	}

	public void setCalculationTime(Time aCalculationTime) {
		theCalculationTime = aCalculationTime;
	}

	/** Время начала расчета */
	private Time theCalculationTime;
	

}
