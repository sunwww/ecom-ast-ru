package ru.ecom.mis.ejb.domain.medcase.hospital;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDayTime;
import ru.ecom.mis.ejb.domain.medcase.voc.VocStoolType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Температурная кривая
 * @author oegorova
 *
 */

@Comment ("Температурная кривая")
@Entity
@Table(schema="SQLUser")
public class TemperatureCurve extends BaseEntity {

	/** Время суток */
	@Comment("Время суток")
	@OneToOne
	public VocDayTime getDayTime() {
		return theDayTime;
	}

	public void setDayTime(VocDayTime aDayTime) {
		theDayTime = aDayTime;
	}

	/** Время суток */
	private VocDayTime theDayTime;
	
	/** Температурный градус */
	@Comment("Температурный градус")
	public BigDecimal getDegree() {
		return theDegree;
	}

	public void setDegree(BigDecimal aDegree) {
		theDegree = aDegree;
	}

	/** Температурный градус */
	private BigDecimal theDegree;
	
	/** Пульс */
	@Comment("Пульс")
	public Integer getPulse() {
		return thePulse;
	}

	public void setPulse(Integer aPulse) {
		thePulse = aPulse;
	}

	/** Пульс */
	private Integer thePulse;
	
	/** Частота дыхания */
	@Comment("Частота дыхания")
	public Integer getRespirationRate() {
		return theRespirationRate;
	}

	public void setRespirationRate(Integer aRespirationRate) {
		theRespirationRate = aRespirationRate;
	}

	/** Частота дыхания */
	private Integer theRespirationRate;
	
	/** Артериальное давление - верхнее */
	@Comment("Артериальное давление - верхнее")
	public Integer getBloodPressureUp() {
		return theBloodPressureUp;
	}

	public void setBloodPressureUp(Integer aBloodPressureUp) {
		theBloodPressureUp = aBloodPressureUp;
	}

	/** Артериальное давление - верхнее */
	private Integer theBloodPressureUp;
	
	/** Артериальное давление - нижнее */
	@Comment("Артериальное давление - нижнее")
	public Integer getBloodPressureDown() {
		return theBloodPressureDown;
	}

	public void setBloodPressureDown(Integer aBloodPressureDown) {
		theBloodPressureDown = aBloodPressureDown;
	}

	/** Артериальное давление - нижнее */
	private Integer theBloodPressureDown;
	
	/** Стул */
	@Comment("Стул")
	@OneToOne
	public VocStoolType getStool() {
		return theStool;
	}

	public void setStool(VocStoolType aStool) {
		theStool = aStool;
	}

	/** Стул */
	private VocStoolType theStool;
	
	/** Вес */
	@Comment("Вес")
	public BigDecimal getWeight() {
		return theWeight;
	}

	public void setWeight(BigDecimal aWeight) {
		theWeight = aWeight;
	}

	/** Вес */
	private BigDecimal theWeight;
	
	
	/** Дата измерения температуры */
	@Comment("Дата измерения температуры")
	public Date getTakingDate() {
		return theTakingDate;
	}

	public void setTakingDate(Date aTakingDate) {
		theTakingDate = aTakingDate;
	}

	/** Дата измерения температуры */
	private Date theTakingDate;
	
	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@OneToOne
	public MedCase getMedCase() {
		return theMedCase;
	}

	public void setMedCase(MedCase aMedCase) {
		theMedCase = aMedCase;
	}

	/** Случай медицинского обслуживания */
	private MedCase theMedCase;
	
	/** День пребывания в стационаре */
	@Comment("День пребывания в стационаре")
	@Transient
	public Integer getHospDayNumber() {
		Long dateFinish = theTakingDate.getTime() ;
		Long dateStart = dateFinish;
		if (theMedCase instanceof HospitalMedCase) {
			dateStart = theMedCase.getDateStart().getTime() ;
		}
		if (theMedCase instanceof DepartmentMedCase) {
			dateStart = theMedCase.getParent().getDateStart().getTime() ;
		}
		
		final int msecinday = 1000 * 60 *  60   * 24 ; 
		
		return Integer.valueOf((int) (1 +( (dateFinish-dateStart) / msecinday)));
	}

		
	/** День болезни */
	@Comment("День болезни")
	public Integer getIllnessDayNumber() {
		return theIllnessDayNumber;
	}

	public void setIllnessDayNumber(Integer aIllnessDayNumber) {
		theIllnessDayNumber = aIllnessDayNumber;
	}

	/** День болезни */
	private Integer theIllnessDayNumber;
	
		
	/** Время суток (текст) */
	@Comment("Время суток (текст)")
	@Transient
	public String getDayTimeText() {
		return theDayTime!=null ? theDayTime.getName():"";
	}
	
	/** АД */
	@Comment("АД")
	@Transient
	public String getBloodPressureInfo() {
		return getBloodPressureUp() + "/" +getBloodPressureDown();
	}
}
