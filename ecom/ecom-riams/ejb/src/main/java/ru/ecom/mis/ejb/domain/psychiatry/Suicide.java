package ru.ecom.mis.ejb.domain.psychiatry;

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
public class Suicide extends BaseEntity{
 /**
  * Карта обратившегося за психиатрической помощью
  */
 @Comment("Карта обратившегося за психиатрической помощью")
 @OneToOne
 public PsychiatricCareCard getCareCard() {
  return theCareCard;
 }
 public void setCareCard(PsychiatricCareCard aCareCard) {
  theCareCard = aCareCard;
 }
 /**
  * Карта обратившегося за психиатрической помощью
  */
 private PsychiatricCareCard theCareCard;
 /**
  * Описание
  */
 @Comment("Описание")
 public String getNotes() {
  return theNotes;
 }
 public void setNotes(String aNotes) {
  theNotes = aNotes;
 }
 /**
  * Описание
  */
 private String theNotes;
	 /** Дата регистрации */
	@Comment("Дата регистрации")
	public Date getRegistrationDate() {
		return theRegistrationDate;
	}
	
	public void setRegistrationDate(Date aRegistrationDate) {
		theRegistrationDate = aRegistrationDate;
	}
	
	/** Дата регистрации */
	private Date theRegistrationDate;


	/** Получает психотропную помощь */
	@Comment("Получает психотропную помощь")
	@OneToOne 
	public VocYesNo getPsychotropicHelp() {
		return thePsychotropicHelp;
	}

	public void setPsychotropicHelp(VocYesNo aPsychotropicHelp) {
		thePsychotropicHelp = aPsychotropicHelp;
	}

	/** Получает психотропную помощь */
	private VocYesNo thePsychotropicHelp;
	
	/** Какую психотропную помощь */
	@Comment("Какую психотропную помощь")
	public String getPsychotropicHelpDesc() {
		return thePsychotropicHelpDesc;
	}

	public void setPsychotropicHelpDesc(String aPsychotropicHelpDesc) {
		thePsychotropicHelpDesc = aPsychotropicHelpDesc;
	}

	/** Какую психотропную помощь */
	private String thePsychotropicHelpDesc;
	/** Сообщение о суициде */
	@Comment("Сообщение о суициде")
	@OneToOne
	public SuicideMessage getSuiMessage() {
		return theSuiMessage;
	}

	public void setSuiMessage(SuicideMessage aSuiMessage) {
		theSuiMessage = aSuiMessage;
	}
	
	

	/** Сообщение о суициде */
	private SuicideMessage theSuiMessage;
	
	/** Диагноз МКБ */
	@Comment("Диагноз МКБ")
	@OneToOne
	public VocIdc10 getDiagnosMkb() {
		return theDiagnosMkb;
	}

	public void setDiagnosMkb(VocIdc10 aDiagnosMkb) {
		theDiagnosMkb = aDiagnosMkb;
	}

	/** Диагноз МКБ */
	private VocIdc10 theDiagnosMkb;
	
	/** Диагноз текст */
	@Comment("Диагноз текст")
	public String getDiagnosText() {
		return theDiagnosText;
	}

	public void setDiagnosText(String aDiagnosText) {
		theDiagnosText = aDiagnosText;
	}

	/** Диагноз текст */
	private String theDiagnosText;
	
	/** Другие суициды */
	@Comment("Другие суициды")
	@OneToOne
	public VocYesNo getOtherSuicide() {
		return theOtherSuicide;
	}

	public void setOtherSuicide(VocYesNo aOtherSuicide) {
		theOtherSuicide = aOtherSuicide;
	}

	/** Другие суициды */
	private VocYesNo theOtherSuicide;

	/** Другие суициды описание */
	@Comment("Другие суициды описание")
	public String getOtherSuicideDesc() {
		return theOtherSuicideDesc;
	}

	public void setOtherSuicideDesc(String aOtherSuicideDesc) {
		theOtherSuicideDesc = aOtherSuicideDesc;
	}

	/** Другие суициды описание */
	private String theOtherSuicideDesc;
	
	/** Алкоголизм */
	@Comment("Алкоголизм")
	@OneToOne
	public VocYesNo getAlcoholism() {
		return theAlcoholism;
	}

	public void setAlcoholism(VocYesNo aAlcoholism) {
		theAlcoholism = aAlcoholism;
	}

	/** Алкоголизм */
	private VocYesNo theAlcoholism;
	
	/** Алкоголизм стаж */
	@Comment("Алкоголизм стаж")
	public Long getAlcoholismExperience() {
		return theAlcoholismExperience;
	}

	public void setAlcoholismExperience(Long aAlcoholismExperience) {
		theAlcoholismExperience = aAlcoholismExperience;
	}

	/** Алкоголизм стаж */
	private Long theAlcoholismExperience;
	
	/** Наркомания */
	@Comment("Наркомания")
	@OneToOne
	public VocYesNo getNarcomania() {
		return theNarcomania;
	}

	public void setNarcomania(VocYesNo aNarcomania) {
		theNarcomania = aNarcomania;
	}

	/** Наркомания */
	private VocYesNo theNarcomania;
	
	/** Наркомания стаж */
	@Comment("Наркомания стаж")
	public Long getNarcomaniaExperience() {
		return theNarcomaniaExperience;
	}

	public void setNarcomaniaExperience(Long aNarcomaniaExperience) {
		theNarcomaniaExperience = aNarcomaniaExperience;
	}

	/** Наркомания стаж */
	private Long theNarcomaniaExperience;
	
	/** Получал психотерапевтическую помощь */
	@Comment("Получал психотерапевтическую помощь")
	@OneToOne
	public VocYesNo getPsychoTherapeuticHelp() {
		return thePsychoTherapeuticHelp;
	}

	public void setPsychoTherapeuticHelp(VocYesNo aPsychoTherapeuticHelp) {
		thePsychoTherapeuticHelp = aPsychoTherapeuticHelp;
	}

	/** Получал психотерапевтическую помощь */
	private VocYesNo thePsychoTherapeuticHelp;
	
	/** Где оказывалась психотерапевтическая помощь? */
	@Comment("Где оказывалась психотерапевтическая помощь?")
	public String getPsychoTherapeuticHelpWhere() {
		return thePsychoTherapeuticHelpWhere;
	}

	public void setPsychoTherapeuticHelpWhere(String aPsychoTherapeuticHelpWhere) {
		thePsychoTherapeuticHelpWhere = aPsychoTherapeuticHelpWhere;
	}

	/** Где оказывалась психотерапевтическая помощь? */
	private String thePsychoTherapeuticHelpWhere;
	
	/** Семейное положение */
	@Comment("Семейное положение")
	@OneToOne
	public VocMarriageStatus getMarriageStatus() {
		return theMarriageStatus;
	}

	public void setMarriageStatus(VocMarriageStatus aMarriageStatus) {
		theMarriageStatus = aMarriageStatus;
	}

	/** Семейное положение */
	private VocMarriageStatus theMarriageStatus;
	
	/** Суицидальная настроенность */
	@Comment("Суицидальная настроенность")
	@OneToOne
	public VocSuicidalAttitude getSuicidalAttitude() {
		return theSuicidalAttitude;
	}

	public void setSuicidalAttitude(VocSuicidalAttitude aSuicidalAttitude) {
		theSuicidalAttitude = aSuicidalAttitude;
	}

	/** Суицидальная настроенность */
	private VocSuicidalAttitude theSuicidalAttitude;
	
	/** Характер суицидальной активности */
	@Comment("Характер суицидальной активности")
	@OneToOne
	public VocSuicidalActivity getSuicidalActivity() {
		return theSuicidalActivity;
	}

	public void setSuicidalActivity(VocSuicidalActivity aSuicidalActivity) {
		theSuicidalActivity = aSuicidalActivity;
	}

	/** Характер суицидальной активности */
	private VocSuicidalActivity theSuicidalActivity;
	
	/** Мотив */
	@Comment("Мотив")
	@OneToOne
	public VocSuicidalMotive getMotive() {
		return theMotive;
	}

	public void setMotive(VocSuicidalMotive aMotive) {
		theMotive = aMotive;
	}

	/** Мотив */
	private VocSuicidalMotive theMotive;
	
	/** Другие мотивы */
	@Comment("Другие мотивы")
	public String getMotiveOther() {
		return theMotiveOther;
	}

	public void setMotiveOther(String aMotiveOther) {
		theMotiveOther = aMotiveOther;
	}

	/** Другие мотивы */
	private String theMotiveOther;
	
	/** Материальное положение */
	@Comment("Материальное положение")
	@OneToOne
	public VocFinancialSituation getFinSituation() {
		return theFinSituation;
	}

	public void setFinSituation(VocFinancialSituation aFinSituation) {
		theFinSituation = aFinSituation;
	}

	/** Материальное положение */
	private VocFinancialSituation theFinSituation;
	
	/** Соматическое заболевание */
	@Comment("Соматическое заболевание")
	@OneToOne
	public VocPsychSomaticDisease getSomaticDisease() {
		return theSomaticDisease;
	}

	public void setSomaticDisease(VocPsychSomaticDisease aSomaticDisease) {
		theSomaticDisease = aSomaticDisease;
	}

	/** Соматическое заболевание */
	private VocPsychSomaticDisease theSomaticDisease;
	
	/** Рекомандации */
	@Comment("Рекомандации")
	@OneToOne
	public VocPsychRecommendation getRecommendation() {
		return theRecommendation;
	}

	public void setRecommendation(VocPsychRecommendation aRecommendation) {
		theRecommendation = aRecommendation;
	}

	/** Рекомандации */
	private VocPsychRecommendation theRecommendation;
	
	/** Сожалеет о суициде */
	@Comment("Сожалеет о суициде")
	@OneToOne
	public VocSuicideRegret getRegret() {
		return theRegret;
	}

	public void setRegret(VocSuicideRegret aRegret) {
		theRegret = aRegret;
	}

	/** Сожалеет о суициде */
	private VocSuicideRegret theRegret;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {
		return theCreateDate;
	}

	public void setCreateDate(Date aCreateDate) {
		theCreateDate = aCreateDate;
	}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {
		return theEditDate;
	}

	public void setEditDate(Date aEditDate) {
		theEditDate = aEditDate;
	}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {
		return theCreateUsername;
	}

	public void setCreateUsername(String aCreateUsername) {
		theCreateUsername = aCreateUsername;
	}

	/** Пользователь, последний редактировавший запись */
	@Comment("Пользователь, последний редактировавший запись")
	public String getEditUsername() {
		return theEditUsername;
	}

	public void setEditUsername(String aEditUsername) {
		theEditUsername = aEditUsername;
	}

	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {
		return theCreateTime;
	}

	public void setCreateTime(Time aCreateTime) {
		theCreateTime = aCreateTime;
	}

	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {
		return theEditTime;
	}

	public void setEditTime(Time aEditTime) {
		theEditTime = aEditTime;
	}

	/** Время редактрования */
	private Time theEditTime;

	/** Время создания */
	private Time theCreateTime;

	/** Пользователь, последний редактировавший запись */
	private String theEditUsername;

	/** Пользователь, создавший запись */
	private String theCreateUsername;

	/** Дата редактирования */
	private Date theEditDate;

	/** Дата создания */
	private Date theCreateDate;
}
