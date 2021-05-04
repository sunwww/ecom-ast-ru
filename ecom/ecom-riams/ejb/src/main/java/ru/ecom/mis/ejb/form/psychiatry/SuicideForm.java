package ru.ecom.mis.ejb.form.psychiatry;


import lombok.Setter;
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
@Setter
public class SuicideForm extends IdEntityForm {
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 @Comment("Карта обратившегося за психиатрической помощью")
	 @Persist 
	 public Long getCareCard() {
	  return careCard;
	 }
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 private Long careCard;
	 /**
	  * Описание
	  */
	 @Comment("Описание")
	 public String getNotes() {
	  return notes;
	 }
	 /**
	  * Описание
	  */
	 private String notes;
		 /** Дата регистрации */
		@Comment("Дата регистрации")
		@Persist @DateString @DoDateString
		public String getRegistrationDate() {
			return registrationDate;
		}

		/** Дата регистрации */
		private String registrationDate;

		
		/** Получает психотропную помощь */
		@Comment("Получает психотропную помощь")
		@Persist 
		public Long getPsychotropicHelp() {
			return psychotropicHelp;
		}

		/** Получает психотропную помощь */
		private Long psychotropicHelp;
		
		/** Какую психотропную помощь */
		@Comment("Какую психотропную помощь")
		@Persist
		public String getPsychotropicHelpDesc() {
			return psychotropicHelpDesc;
		}

		/** Какую психотропную помощь */
		private String psychotropicHelpDesc;
		/** Сообщение о суициде */
		@Comment("Сообщение о суициде")
		@Persist
		public Long getSuiMessage() {
			return suiMessage;
		}

		/** Сообщение о суициде */
		private Long suiMessage;
		
		/** Диагноз МКБ */
		@Comment("Диагноз МКБ")
		@Persist @Required
		public Long getDiagnosMkb() {
			return diagnosMkb;
		}

		/** Диагноз МКБ */
		private Long diagnosMkb;
		
		/** Диагноз текст */
		@Comment("Диагноз текст")
		@Persist @Required
		public String getDiagnosText() {
			return diagnosText;
		}

		/** Диагноз текст */
		private String diagnosText;
		
		/** Другие суициды */
		@Comment("Другие суициды")
		@Persist 
		public Long getOtherSuicide() {
			return otherSuicide;
		}

		/** Другие суициды */
		private Long otherSuicide;

		/** Другие суициды описание */
		@Comment("Другие суициды описание")
		@Persist
		public String getOtherSuicideDesc() {
			return otherSuicideDesc;
		}

		/** Другие суициды описание */
		private String otherSuicideDesc;
		
		/** Алкоголизм */
		@Comment("Алкоголизм")
		@Persist
		public Long getAlcoholism() {
			return alcoholism;
		}

		/** Алкоголизм */
		private Long alcoholism;
		
		/** Алкоголизм стаж */
		@Comment("Алкоголизм стаж")
		@Persist
		public Long getAlcoholismExperience() {
			return alcoholismExperience;
		}
		/** Алкоголизм стаж */
		private Long alcoholismExperience;
		
		/** Наркомания */
		@Comment("Наркомания")
		@Persist
		public Long getNarcomania() {
			return narcomania;
		}

		/** Наркомания */
		private Long narcomania;
		
		/** Наркомания стаж */
		@Comment("Наркомания стаж")
		@Persist
		public Long getNarcomaniaExperience() {
			return narcomaniaExperience;
		}

		/** Наркомания стаж */
		private Long narcomaniaExperience;
		
		/** Получал психотерапевтическую помощь */
		@Comment("Получал психотерапевтическую помощь")
		@Persist @Required
		public Long getPsychoTherapeuticHelp() {
			return psychoTherapeuticHelp;
		}

		/** Получал психотерапевтическую помощь */
		private Long psychoTherapeuticHelp;
		
		/** Где оказывалась психотерапевтическая помощь? */
		@Comment("Где оказывалась психотерапевтическая помощь?")
		@Persist
		public String getPsychoTherapeuticHelpWhere() {
			return psychoTherapeuticHelpWhere;
		}

		/** Где оказывалась психотерапевтическая помощь? */
		private String psychoTherapeuticHelpWhere;
		
		/** Семейное положение */
		@Comment("Семейное положение")
		@Persist
		public Long getMarriageStatus() {
			return marriageStatus;
		}

		/** Семейное положение */
		private Long marriageStatus;
		
		/** Суицидальная настроенность */
		@Comment("Суицидальная настроенность")
		@Persist @Required
		public Long getSuicidalAttitude() {
			return suicidalAttitude;
		}

		/** Суицидальная настроенность */
		private Long suicidalAttitude;
		
		/** Характер суицидальной активности */
		@Comment("Характер суицидальной активности")
		@Persist @Required
		public Long getSuicidalActivity() {
			return suicidalActivity;
		}

		/** Характер суицидальной активности */
		private Long suicidalActivity;
		
		/** Мотив */
		@Comment("Мотив")
		@Persist @Required
		public Long getMotive() {
			return motive;
		}

		/** Мотив */
		private Long motive;
		
		/** Другие мотивы */
		@Comment("Другие мотивы")
		@Persist
		public String getMotiveOther() {
			return motiveOther;
		}

		/** Другие мотивы */
		private String motiveOther;
		
		/** Материальное положение */
		@Comment("Материальное положение")
		@Persist
		public Long getFinSituation() {
			return finSituation;
		}

		/** Материальное положение */
		private Long finSituation;
		
		/** Соматическое заболевание */
		@Comment("Соматическое заболевание")
		@Persist @Required
		public Long getSomaticDisease() {
			return somaticDisease;
		}

		/** Соматическое заболевание */
		private Long somaticDisease;
		
		/** Рекомандации */
		@Comment("Рекомандации")
		@Persist @Required
		public Long getRecommendation() {
			return recommendation;
		}

		/** Рекомандации */
		private Long recommendation;
		
		/** Сожалеет о суициде */
		@Comment("Сожалеет о суициде")
		@Persist @Required
		public Long getRegret() {
			return regret;
		}

		/** Сожалеет о суициде */
		private Long regret;
		
		/** Дата создания */
		@Comment("Дата создания")
		@Persist @DateString @DoDateString
		public String getCreateDate() {
			return createDate;
		}

		/** Дата редактирования */
		@Comment("Дата редактирования")
		@Persist @DateString @DoDateString
		public String getEditDate() {
			return editDate;
		}

		/** Пользователь, создавший запись */
		@Persist @Comment("Пользователь, создавший запись")
		public String getCreateUsername() {
			return createUsername;
		}

		/** Пользователь, последний редактировавший запись */
		@Persist @Comment("Пользователь, последний редактировавший запись")
		public String getEditUsername() {
			return editUsername;
		}

		/** Время создания */
		@Persist @Comment("Время создания")
		@TimeString @DoTimeString
		public String getCreateTime() {
			return createTime;
		}

		/** Время редактрования */
		@Persist @Comment("Время редактрования")
		@TimeString @DoTimeString
		public String getEditTime() {
			return editTime;
		}

		/** Время редактрования */
		private String editTime;

		/** Время создания */
		private String createTime;

		/** Пользователь, последний редактировавший запись */
		private String editUsername;

		/** Пользователь, создавший запись */
		private String createUsername;

		/** Дата редактирования */
		private String editDate;

		/** Дата создания */
		private String createDate;

}
