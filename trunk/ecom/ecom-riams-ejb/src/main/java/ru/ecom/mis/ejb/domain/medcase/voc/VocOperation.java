package ru.ecom.mis.ejb.domain.medcase.voc;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник операций")
@Table(schema="SQLUser")
public class VocOperation extends VocBaseEntity{
	/** Дата начала актуальности */
	@Comment("Дата начала актуальности")
	public Date getStartActualDate() {return theStartActualDate;}
	public void setStartActualDate(Date aStartActualDate) {theStartActualDate = aStartActualDate;}

	/** Дата окончания актуальности */
	@Comment("Дата окончания актуальности")
	public Date getFinishActualDate() {return theFinishActualDate;}
	public void setFinishActualDate(Date aFinishActualDate) {theFinishActualDate = aFinishActualDate;}

	/** Уровонь сложности */
	@Comment("Уровонь сложности")
	public Long getComplexity() {
		return theComplexity;
	}

	public void setComplexity(Long aComplexity) {
		theComplexity = aComplexity;
	}

	/** Уровонь сложности */
	private Long theComplexity;
	/** Дата окончания актуальности */
	private Date theFinishActualDate;
	/** Дата начала актуальности */
	private Date theStartActualDate;

}
