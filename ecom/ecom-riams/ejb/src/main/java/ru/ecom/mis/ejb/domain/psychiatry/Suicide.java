package ru.ecom.mis.ejb.domain.psychiatry;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.patient.voc.VocMarriageStatus;
import ru.ecom.mis.ejb.domain.patient.voc.VocYesNo;
import ru.ecom.mis.ejb.domain.psychiatry.voc.*;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;
 /**
  * Суицид
  */
 @Comment("Суицид")
@Entity
@AIndexes({
	@AIndex(properties={"careCard"})
	,@AIndex(properties={"suiMessage"})
})
@Table(schema="SQLUser")
 @Getter
 @Setter
public class Suicide extends BaseEntity{
 /**
  * Карта обратившегося за психиатрической помощью
  */
 @Comment("Карта обратившегося за психиатрической помощью")
 @OneToOne
 public PsychiatricCareCard getCareCard() {
  return careCard;
 }
 /**
  * Карта обратившегося за психиатрической помощью
  */
 private PsychiatricCareCard careCard;
 /**
  * Описание
  */
 private String notes;

	/** Дата регистрации */
	private Date registrationDate;


	/** Получает психотропную помощь */
	@Comment("Получает психотропную помощь")
	@OneToOne 
	public VocYesNo getPsychotropicHelp() {
		return psychotropicHelp;
	}

	/** Получает психотропную помощь */
	private VocYesNo psychotropicHelp;
	
	/** Какую психотропную помощь */
	private String psychotropicHelpDesc;
	/** Сообщение о суициде */
	@Comment("Сообщение о суициде")
	@OneToOne
	public SuicideMessage getSuiMessage() {
		return suiMessage;
	}

	/** Сообщение о суициде */
	private SuicideMessage suiMessage;
	
	/** Диагноз МКБ */
	@Comment("Диагноз МКБ")
	@OneToOne
	public VocIdc10 getDiagnosMkb() {
		return diagnosMkb;
	}

	/** Диагноз МКБ */
	private VocIdc10 diagnosMkb;
	
	/** Диагноз текст */
	private String diagnosText;
	
	/** Другие суициды */
	@Comment("Другие суициды")
	@OneToOne
	public VocYesNo getOtherSuicide() {
		return otherSuicide;
	}

	/** Другие суициды */
	private VocYesNo otherSuicide;

	/** Другие суициды описание */
	private String otherSuicideDesc;
	
	/** Алкоголизм */
	@Comment("Алкоголизм")
	@OneToOne
	public VocYesNo getAlcoholism() {
		return alcoholism;
	}

	/** Алкоголизм */
	private VocYesNo alcoholism;

	/** Алкоголизм стаж */
	private Long alcoholismExperience;
	
	/** Наркомания */
	@Comment("Наркомания")
	@OneToOne
	public VocYesNo getNarcomania() {
		return narcomania;
	}

	/** Наркомания */
	private VocYesNo narcomania;
	
	/** Наркомания стаж */
	@Comment("Наркомания стаж")
	public Long getNarcomaniaExperience() {
		return narcomaniaExperience;
	}

	/** Наркомания стаж */
	private Long narcomaniaExperience;
	
	/** Получал психотерапевтическую помощь */
	@Comment("Получал психотерапевтическую помощь")
	@OneToOne
	public VocYesNo getPsychoTherapeuticHelp() {
		return psychoTherapeuticHelp;
	}

	/** Получал психотерапевтическую помощь */
	private VocYesNo psychoTherapeuticHelp;
	

	/** Где оказывалась психотерапевтическая помощь? */
	private String psychoTherapeuticHelpWhere;
	
	/** Семейное положение */
	@Comment("Семейное положение")
	@OneToOne
	public VocMarriageStatus getMarriageStatus() {
		return marriageStatus;
	}

	/** Семейное положение */
	private VocMarriageStatus marriageStatus;
	
	/** Суицидальная настроенность */
	@Comment("Суицидальная настроенность")
	@OneToOne
	public VocSuicidalAttitude getSuicidalAttitude() {
		return suicidalAttitude;
	}

	/** Суицидальная настроенность */
	private VocSuicidalAttitude suicidalAttitude;
	
	/** Характер суицидальной активности */
	@Comment("Характер суицидальной активности")
	@OneToOne
	public VocSuicidalActivity getSuicidalActivity() {
		return suicidalActivity;
	}

	/** Характер суицидальной активности */
	private VocSuicidalActivity suicidalActivity;
	
	/** Мотив */
	@Comment("Мотив")
	@OneToOne
	public VocSuicidalMotive getMotive() {
		return motive;
	}

	/** Мотив */
	private VocSuicidalMotive motive;
	
	/** Другие мотивы */
	private String motiveOther;
	
	/** Материальное положение */
	@Comment("Материальное положение")
	@OneToOne
	public VocFinancialSituation getFinSituation() {
		return finSituation;
	}

	/** Материальное положение */
	private VocFinancialSituation finSituation;
	
	/** Соматическое заболевание */
	@Comment("Соматическое заболевание")
	@OneToOne
	public VocPsychSomaticDisease getSomaticDisease() {
		return somaticDisease;
	}

	/** Соматическое заболевание */
	private VocPsychSomaticDisease somaticDisease;
	
	/** Рекомандации */
	@Comment("Рекомандации")
	@OneToOne
	public VocPsychRecommendation getRecommendation() {
		return recommendation;
	}

	/** Рекомандации */
	private VocPsychRecommendation recommendation;
	
	/** Сожалеет о суициде */
	@Comment("Сожалеет о суициде")
	@OneToOne
	public VocSuicideRegret getRegret() {
		return regret;
	}

	/** Сожалеет о суициде */
	private VocSuicideRegret regret;

	/** Время редактрования */
	private Time editTime;

	/** Время создания */
	private Time createTime;

	/** Пользователь, последний редактировавший запись */
	private String editUsername;

	/** Пользователь, создавший запись */
	private String createUsername;

	/** Дата редактирования */
	private Date editDate;

	/** Дата создания */
	private Date createDate;
}
