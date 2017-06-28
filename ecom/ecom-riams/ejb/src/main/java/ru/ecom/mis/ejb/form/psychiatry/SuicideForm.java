package ru.ecom.mis.ejb.form.psychiatry;


import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.psychiatry.Suicide;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = Suicide.class)
@Comment("Осмотр после суицида")
@WebTrail(comment = "Осмотр после суицида", nameProperties= "id",list="entityParentList-psych_suicide.do",listComment="список по пациенту", view="entityParentView-psych_suicide.do")
@Parent(property="suiMessage", parentForm=SuicideMessageForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard/SuicideMessage/Suicide")
public class SuicideForm extends IdEntityForm {
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 @Comment("Карта обратившегося за психиатрической помощью")
	 @Persist 
	 public Long getCareCard() {
	  return theCareCard;
	 }
	 public void setCareCard(Long aCareCard) {
	  theCareCard = aCareCard;
	 }
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 private Long theCareCard;
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
		@Persist @DateString @DoDateString
		public String getRegistrationDate() {
			return theRegistrationDate;
		}
		
		public void setRegistrationDate(String aRegistrationDate) {
			theRegistrationDate = aRegistrationDate;
		}
		
		/** Дата регистрации */
		private String theRegistrationDate;

		
		/** Получает психотропную помощь */
		@Comment("Получает психотропную помощь")
		@Persist 
		public Long getPsychotropicHelp() {
			return thePsychotropicHelp;
		}

		public void setPsychotropicHelp(Long aPsychotropicHelp) {
			thePsychotropicHelp = aPsychotropicHelp;
		}

		/** Получает психотропную помощь */
		private Long thePsychotropicHelp;
		
		/** Какую психотропную помощь */
		@Comment("Какую психотропную помощь")
		@Persist
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
		@Persist
		public Long getSuiMessage() {
			return theSuiMessage;
		}

		public void setSuiMessage(Long aSuiMessage) {
			theSuiMessage = aSuiMessage;
		}
		
		

		/** Сообщение о суициде */
		private Long theSuiMessage;
		
		/** Диагноз МКБ */
		@Comment("Диагноз МКБ")
		@Persist @Required
		public Long getDiagnosMkb() {
			return theDiagnosMkb;
		}

		public void setDiagnosMkb(Long aDiagnosMkb) {
			theDiagnosMkb = aDiagnosMkb;
		}

		/** Диагноз МКБ */
		private Long theDiagnosMkb;
		
		/** Диагноз текст */
		@Comment("Диагноз текст")
		@Persist @Required
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
		@Persist 
		public Long getOtherSuicide() {
			return theOtherSuicide;
		}

		public void setOtherSuicide(Long aOtherSuicide) {
			theOtherSuicide = aOtherSuicide;
		}

		/** Другие суициды */
		private Long theOtherSuicide;

		/** Другие суициды описание */
		@Comment("Другие суициды описание")
		@Persist
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
		@Persist
		public Long getAlcoholism() {
			return theAlcoholism;
		}

		public void setAlcoholism(Long aAlcoholism) {
			theAlcoholism = aAlcoholism;
		}

		/** Алкоголизм */
		private Long theAlcoholism;
		
		/** Алкоголизм стаж */
		@Comment("Алкоголизм стаж")
		@Persist
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
		@Persist
		public Long getNarcomania() {
			return theNarcomania;
		}

		public void setNarcomania(Long aNarcomania) {
			theNarcomania = aNarcomania;
		}

		/** Наркомания */
		private Long theNarcomania;
		
		/** Наркомания стаж */
		@Comment("Наркомания стаж")
		@Persist
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
		@Persist @Required
		public Long getPsychoTherapeuticHelp() {
			return thePsychoTherapeuticHelp;
		}

		public void setPsychoTherapeuticHelp(Long aPsychoTherapeuticHelp) {
			thePsychoTherapeuticHelp = aPsychoTherapeuticHelp;
		}

		/** Получал психотерапевтическую помощь */
		private Long thePsychoTherapeuticHelp;
		
		/** Где оказывалась психотерапевтическая помощь? */
		@Comment("Где оказывалась психотерапевтическая помощь?")
		@Persist
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
		@Persist
		public Long getMarriageStatus() {
			return theMarriageStatus;
		}

		public void setMarriageStatus(Long aMarriageStatus) {
			theMarriageStatus = aMarriageStatus;
		}

		/** Семейное положение */
		private Long theMarriageStatus;
		
		/** Суицидальная настроенность */
		@Comment("Суицидальная настроенность")
		@Persist @Required
		public Long getSuicidalAttitude() {
			return theSuicidalAttitude;
		}

		public void setSuicidalAttitude(Long aSuicidalAttitude) {
			theSuicidalAttitude = aSuicidalAttitude;
		}

		/** Суицидальная настроенность */
		private Long theSuicidalAttitude;
		
		/** Характер суицидальной активности */
		@Comment("Характер суицидальной активности")
		@Persist @Required
		public Long getSuicidalActivity() {
			return theSuicidalActivity;
		}

		public void setSuicidalActivity(Long aSuicidalActivity) {
			theSuicidalActivity = aSuicidalActivity;
		}

		/** Характер суицидальной активности */
		private Long theSuicidalActivity;
		
		/** Мотив */
		@Comment("Мотив")
		@Persist @Required
		public Long getMotive() {
			return theMotive;
		}

		public void setMotive(Long aMotive) {
			theMotive = aMotive;
		}

		/** Мотив */
		private Long theMotive;
		
		/** Другие мотивы */
		@Comment("Другие мотивы")
		@Persist
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
		@Persist
		public Long getFinSituation() {
			return theFinSituation;
		}

		public void setFinSituation(Long aFinSituation) {
			theFinSituation = aFinSituation;
		}

		/** Материальное положение */
		private Long theFinSituation;
		
		/** Соматическое заболевание */
		@Comment("Соматическое заболевание")
		@Persist @Required
		public Long getSomaticDisease() {
			return theSomaticDisease;
		}

		public void setSomaticDisease(Long aSomaticDisease) {
			theSomaticDisease = aSomaticDisease;
		}

		/** Соматическое заболевание */
		private Long theSomaticDisease;
		
		/** Рекомандации */
		@Comment("Рекомандации")
		@Persist @Required
		public Long getRecommendation() {
			return theRecommendation;
		}

		public void setRecommendation(Long aRecommendation) {
			theRecommendation = aRecommendation;
		}

		/** Рекомандации */
		private Long theRecommendation;
		
		/** Сожалеет о суициде */
		@Comment("Сожалеет о суициде")
		@Persist @Required
		public Long getRegret() {
			return theRegret;
		}

		public void setRegret(Long aRegret) {
			theRegret = aRegret;
		}

		/** Сожалеет о суициде */
		private Long theRegret;
		
		/** Дата создания */
		@Comment("Дата создания")
		@Persist @DateString @DoDateString
		public String getCreateDate() {
			return theCreateDate;
		}

		public void setCreateDate(String aCreateDate) {
			theCreateDate = aCreateDate;
		}

		/** Дата редактирования */
		@Comment("Дата редактирования")
		@Persist @DateString @DoDateString
		public String getEditDate() {
			return theEditDate;
		}

		public void setEditDate(String aEditDate) {
			theEditDate = aEditDate;
		}

		/** Пользователь, создавший запись */
		@Persist @Comment("Пользователь, создавший запись")
		public String getCreateUsername() {
			return theCreateUsername;
		}

		public void setCreateUsername(String aCreateUsername) {
			theCreateUsername = aCreateUsername;
		}

		/** Пользователь, последний редактировавший запись */
		@Persist @Comment("Пользователь, последний редактировавший запись")
		public String getEditUsername() {
			return theEditUsername;
		}

		public void setEditUsername(String aEditUsername) {
			theEditUsername = aEditUsername;
		}

		/** Время создания */
		@Persist @Comment("Время создания")
		@TimeString @DoTimeString
		public String getCreateTime() {
			return theCreateTime;
		}

		public void setCreateTime(String aCreateTime) {
			theCreateTime = aCreateTime;
		}

		/** Время редактрования */
		@Persist @Comment("Время редактрования")
		@TimeString @DoTimeString
		public String getEditTime() {
			return theEditTime;
		}

		public void setEditTime(String aEditTime) {
			theEditTime = aEditTime;
		}

		/** Время редактрования */
		private String theEditTime;

		/** Время создания */
		private String theCreateTime;

		/** Пользователь, последний редактировавший запись */
		private String theEditUsername;

		/** Пользователь, создавший запись */
		private String theCreateUsername;

		/** Дата редактирования */
		private String theEditDate;

		/** Дата создания */
		private String theCreateDate;

}
