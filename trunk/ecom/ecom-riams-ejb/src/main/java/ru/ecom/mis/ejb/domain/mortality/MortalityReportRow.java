package ru.ecom.mis.ejb.domain.mortality;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.mortality.voc.VocAgePeriod;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Строка отчета по смертности
 * @author azviagin
 *
 */
@Comment("Строка отчета по смертности")
@Entity
@Table(schema="SQLUser")
public class MortalityReportRow extends BaseEntity{
	
	/** Возрастной период */
	@Comment("Возрастной период")
	@OneToOne
	public VocAgePeriod getAgePeriod() {
		return theAgePeriod;
	}

	public void setAgePeriod(VocAgePeriod aAgePeriod) {
		theAgePeriod = aAgePeriod;
	}

	/** Возрастной период */
	private VocAgePeriod theAgePeriod;
		
	
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
	
	/** Живорожденность */
	@Comment("Живорожденность")
	public Boolean getLiveBorn() {
		return theLiveBorn;
	}

	public void setLiveBorn(Boolean aLiveBorn) {
		theLiveBorn = aLiveBorn;
	}

	/** Живорожденность */
	private Boolean theLiveBorn;
	
	/** Количество */
	@Comment("Количество")
	public int getAmount() {
		return theAmount;
	}

	public void setAmount(int aAmount) {
		theAmount = aAmount;
	}

	/** Количество */
	private int theAmount;
	
	/** Дата отчета */
	@Comment("Дата отчета")
	@ManyToOne
	public MortalityReportDate getMortalityReportDate() {
		return theMortalityReportDate;
	}

	public void setMortalityReportDate(MortalityReportDate aMortalityReportDate) {
		theMortalityReportDate = aMortalityReportDate;
	}

	/** Дата отчета */
	private MortalityReportDate theMortalityReportDate;
	


}
