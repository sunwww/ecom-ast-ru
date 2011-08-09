package ru.ecom.mis.ejb.domain.birth;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.mis.ejb.domain.birth.voc.VocApgarMuscleTone;
import ru.ecom.mis.ejb.domain.birth.voc.VocApgarPalpitation;
import ru.ecom.mis.ejb.domain.birth.voc.VocApgarReflexes;
import ru.ecom.mis.ejb.domain.birth.voc.VocApgarRespiration;
import ru.ecom.mis.ejb.domain.birth.voc.VocApgarSkinColor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Оценка новорожденного по Апгар
 * @author azviagin
 *
 */

@Comment("Оценка новорожденного по Апгар")
@Entity
@Table(schema="SQLUser")
public class ApgarEstimation extends Inspection{
	
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
		StringBuilder ret = new StringBuilder() ;
		ret.append("Общая оценка (балл) ").append(theCommonMark) ;
		return ret.toString() ;
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
