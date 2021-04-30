package ru.ecom.mis.ejb.domain.birth;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.birth.voc.*;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Оценка респираторного дистресса новорожденного по Downes
 * @author azviagin
 *
 */

@Comment("Оценка респираторного дистресса новорожденного по Downes")
@Entity
@Getter
@Setter
public class DownesEstimation extends Inspection {
	
	/** Частота дыхательных движений */
	@Comment("Частота дыхательных движений")
	@OneToOne
	public VocDownesRespirationRate getRespirationRate() {
		return respirationRate;
	}
	private VocDownesRespirationRate respirationRate;
	
	/** Цианоз */
	@Comment("Цианоз")
	@OneToOne
	public VocDownesCyanosis getCyanosis() {
		return cyanosis;
	}
	private VocDownesCyanosis cyanosis;
	
	/** Втяжение межреберных промежутков */
	@Comment("Втяжение межреберных промежутков")
	@OneToOne
	public VocDownesIntercostalRet getIntercostalRetraction() {
		return intercostalRetraction;
	}
	private VocDownesIntercostalRet intercostalRetraction;
	
	/** Затрудненный выдох */
	@Comment("Затрудненный выдох")
	@OneToOne
	public VocDownesDifExhalation getDifficultExhalation() {
		return difficultExhalation;
	}
	private VocDownesDifExhalation difficultExhalation;
	
	/** Аускультация */
	@Comment("Аускультация")
	@OneToOne
	public VocDownesAuscultation getAuscultation() {
		return auscultation;
	}
	private VocDownesAuscultation auscultation;
	
	/** Общая оценка */
	@Comment("Общая оценка")
	@OneToOne
	public VocCommonMask getCommonMark() {
		return commonMark;
	}
	private VocCommonMask commonMark;
	
	@Transient
	public String getTypeInformation() {
		return  "Оценка респираторного дистресса новорожденного по Downes" ;
	}
	@Transient
	public String getInformation() {
		return "Общая оценка (балл): " + (commonMark != null ? commonMark.getCode() : "") + " " + (commonMark != null ? commonMark.getName() : "");
	}
}
