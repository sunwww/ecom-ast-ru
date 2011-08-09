package ru.ecom.mis.ejb.domain.birth;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.mis.ejb.domain.birth.voc.VocCommonMask;
import ru.ecom.mis.ejb.domain.birth.voc.VocDownesAuscultation;
import ru.ecom.mis.ejb.domain.birth.voc.VocDownesCommonMark;
import ru.ecom.mis.ejb.domain.birth.voc.VocDownesCyanosis;
import ru.ecom.mis.ejb.domain.birth.voc.VocDownesDifExhalation;
import ru.ecom.mis.ejb.domain.birth.voc.VocDownesIntercostalRet;
import ru.ecom.mis.ejb.domain.birth.voc.VocDownesRespirationRate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Оценка респираторного дистресса новорожденного по Downes
 * @author azviagin
 *
 */

@Comment("Оценка респираторного дистресса новорожденного по Downes")
@Entity
@Table(schema="SQLUser")
public class DownesEstimation extends Inspection{
	
	/** Частота дыхательных движений */
	@Comment("Частота дыхательных движений")
	@OneToOne
	public VocDownesRespirationRate getRespirationRate() {
		return theRespirationRate;
	}

	public void setRespirationRate(VocDownesRespirationRate aRespirationRate) {
		theRespirationRate = aRespirationRate;
	}

	/** Частота дыхательных движений */
	private VocDownesRespirationRate theRespirationRate;
	
	/** Цианоз */
	@Comment("Цианоз")
	@OneToOne
	public VocDownesCyanosis getCyanosis() {
		return theCyanosis;
	}

	public void setCyanosis(VocDownesCyanosis aCyanosis) {
		theCyanosis = aCyanosis;
	}

	/** Цианоз */
	private VocDownesCyanosis theCyanosis;
	
	/** Втяжение межреберных промежутков */
	@Comment("Втяжение межреберных промежутков")
	@OneToOne
	public VocDownesIntercostalRet getIntercostalRetraction() {
		return theIntercostalRetraction;
	}

	public void setIntercostalRetraction(VocDownesIntercostalRet aIntercostalRetraction) {
		theIntercostalRetraction = aIntercostalRetraction;
	}

	/** Втяжение межреберных промежутков */
	private VocDownesIntercostalRet theIntercostalRetraction;
	
	/** Затрудненный выдох */
	@Comment("Затрудненный выдох")
	@OneToOne
	public VocDownesDifExhalation getDifficultExhalation() {
		return theDifficultExhalation;
	}

	public void setDifficultExhalation(VocDownesDifExhalation aDifficultExhalation) {
		theDifficultExhalation = aDifficultExhalation;
	}

	/** Затрудненный выдох */
	private VocDownesDifExhalation theDifficultExhalation;
	
	/** Аускультация */
	@Comment("Аускультация")
	@OneToOne
	public VocDownesAuscultation getAuscultation() {
		return theAuscultation;
	}

	public void setAuscultation(VocDownesAuscultation aAuscultation) {
		theAuscultation = aAuscultation;
	}

	/** Аускультация */
	private VocDownesAuscultation theAuscultation;
	
	/** Общая оценка */
	@Comment("Общая оценка")
	@OneToOne
	public VocCommonMask getCommonMark() {
		return theCommonMark;
	}

	public void setCommonMark(VocCommonMask aCommonMark) {
		theCommonMark = aCommonMark;
	}

	/** Общая оценка */
	private VocCommonMask theCommonMark;
	
	@Transient
	public String getTypeInformation() {
		return  "Оценка респираторного дистресса новорожденного по Downes" ;
	}
	@Transient
	public String getInformation() {
		StringBuilder ret = new StringBuilder() ;
		ret.append("Общая оценка (балл): ").append(theCommonMark!=null?theCommonMark.getCode():"").append(" ").append(theCommonMark!=null?theCommonMark.getName():"") ;
		return ret.toString() ;
	}
}
