package ru.ecom.mis.ejb.domain.birth;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.birth.voc.VocBirthOrder;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Строка отчета по рождаемости
 * @author oegorova
 *
 */
@Comment("Строка отчета по рождаемости")
@Entity
@Table(schema="SQLUser")
public class BirthReportRow extends BaseEntity{
	
	
	/** Количество родов */
	@Comment("Количество родов")
	public int getBirthAmount() {
		return theBirthAmount;
	}

	public void setBirthAmount(int aBirthAmount) {
		theBirthAmount = aBirthAmount;
	}

	/** Количество родов */
	private int theBirthAmount;
	
	/** Пол */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {
		return theSex;
	}

	public void setSex(VocSex aSex) {
		theSex = aSex;
	}

	/** Пол */
	private VocSex theSex;
	
	/** День отчета */
	@Comment("День отчета")
	@ManyToOne
	public BirthReportDate getBirthReportDate() {
		return theBirthReportDate;
	}

	public void setBirthReportDate(BirthReportDate aBirthReportDate) {
		theBirthReportDate = aBirthReportDate;
	}

	/** День отчета */
	private BirthReportDate theBirthReportDate;
	
	/** Порядок родов матери */
	@Comment("Порядок родов матери")
	@OneToOne
	public VocBirthOrder getVocBirthOrder() {
		return theVocBirthOrder;
	}

	public void setVocBirthOrder(VocBirthOrder aVocBirthOrder) {
		theVocBirthOrder = aVocBirthOrder;
	}

	/** Порядок родов матери */
	private VocBirthOrder theVocBirthOrder;

}
