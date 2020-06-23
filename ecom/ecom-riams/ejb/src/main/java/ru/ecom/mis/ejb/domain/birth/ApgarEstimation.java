package ru.ecom.mis.ejb.domain.birth;

import ru.ecom.mis.ejb.domain.birth.voc.*;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Оценка новорожденного по Апгар
 * @author azviagin
 *
 */

@Comment("Оценка новорожденного по Апгар")
@Entity
public class ApgarEstimation extends Inspection {
	
	/** Время после рождения (мин) */
	@Comment("Время после рождения (мин)")
	public Integer getPostNatalTime() {return thePostNatalTime;}
	public void setPostNatalTime(Integer aPostNatalTime) {thePostNatalTime = aPostNatalTime;}

	/** Сердцебиение */
	@Comment("Сердцебиение")
	@OneToOne
	public VocApgarPalpitation getPalpitation() {return thePalpitation;}
	public void setPalpitation(VocApgarPalpitation aPalpitation) {thePalpitation = aPalpitation;}

	/** Дыхание */
	@Comment("Дыхание")
	@OneToOne
	public VocApgarRespiration getRespiration() {return theRespiration;}
	public void setRespiration(VocApgarRespiration aRespiration) {theRespiration = aRespiration;}

	/** Окраска кожи */
	@Comment("Окраска кожи")
	@OneToOne
	public VocApgarSkinColor getSkinColor() {return theSkinColor;}
	public void setSkinColor(VocApgarSkinColor aSkinColor) {theSkinColor = aSkinColor;}

	/** Тонус мышц */
	@Comment("Тонус мышц")
	@OneToOne
	public VocApgarMuscleTone getMuscleTone() {return theMuscleTone;}
	public void setMuscleTone(VocApgarMuscleTone aMuscleTone) {theMuscleTone = aMuscleTone;}

	/** Рефлексы */
	@Comment("Рефлексы")
	@OneToOne
	public VocApgarReflexes getReflexes() {return theReflexes;}
	public void setReflexes(VocApgarReflexes aReflexes) {theReflexes = aReflexes;}

	/** Общая оценка (балл) */
	@Comment("Общая оценка (балл)")
	public Integer getCommonMark() {return theCommonMark;}
	public void setCommonMark(Integer aCommonMark) {theCommonMark = aCommonMark;}

	@Transient
	public String getTypeInformation() {
		return  "Оценка новорожденного по Апгар" ;
	}
	@Transient
	public String getInformation() {
		return "Общая оценка (балл) " + theCommonMark;
	}
	/** Время после рождения (мин) */
	private Integer thePostNatalTime;
	/** Сердцебиение */
	private VocApgarPalpitation thePalpitation;
	/** Дыхание */
	private VocApgarRespiration theRespiration;
	/** Окраска кожи */
	private VocApgarSkinColor theSkinColor;
	/** Тонус мышц */
	private VocApgarMuscleTone theMuscleTone;
	/** Рефлексы */
	private VocApgarReflexes theReflexes;
	/** Общая оценка (балл) */
	private Integer theCommonMark;
	
	
}
