package ru.ecom.mis.ejb.domain.birth;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class ApgarEstimation extends Inspection {
	

	/** Сердцебиение */
	@Comment("Сердцебиение")
	@OneToOne
	public VocApgarPalpitation getPalpitation() {return palpitation;}

	/** Дыхание */
	@Comment("Дыхание")
	@OneToOne
	public VocApgarRespiration getRespiration() {return respiration;}

	/** Окраска кожи */
	@Comment("Окраска кожи")
	@OneToOne
	public VocApgarSkinColor getSkinColor() {return skinColor;}

	/** Тонус мышц */
	@Comment("Тонус мышц")
	@OneToOne
	public VocApgarMuscleTone getMuscleTone() {return muscleTone;}

	/** Рефлексы */
	@Comment("Рефлексы")
	@OneToOne
	public VocApgarReflexes getReflexes() {return reflexes;}

	@Transient
	public String getTypeInformation() {
		return  "Оценка новорожденного по Апгар" ;
	}
	@Transient
	public String getInformation() {
		return "Общая оценка (балл) " + commonMark;
	}
	/** Время после рождения (мин) */
	private Integer postNatalTime;
	/** Сердцебиение */
	private VocApgarPalpitation palpitation;
	/** Дыхание */
	private VocApgarRespiration respiration;
	/** Окраска кожи */
	private VocApgarSkinColor skinColor;
	/** Тонус мышц */
	private VocApgarMuscleTone muscleTone;
	/** Рефлексы */
	private VocApgarReflexes reflexes;
	/** Общая оценка (балл) */
	private Integer commonMark;
}
