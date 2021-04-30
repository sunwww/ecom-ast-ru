package ru.ecom.mis.ejb.form.psychiatry;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.psychiatry.PsychiatricExamination;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = PsychiatricExamination.class)
@Comment("Психиатрическая экспертиза")
@WebTrail(comment = "Психиатрическая экспертиза", nameProperties= "actNumber",list="entityParentList-psych_examination.do", listComment="список по пациенту", view="entityParentView-psych_examination.do")
@Parent(property="careCard", parentForm=PsychiatricCareCardForm.class, orderBy="expertDecision")
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard/PsychiatricExamination")
@Setter
public class PsychiatricExaminationForm extends IdEntityForm {
	 /**
	  * Номер акта
	  */
	 @Comment("Номер акта")
	 @Persist @Required
	 public String getActNumber() {
	  return actNumber;
	 }
	 /**
	  * Номер акта
	  */
	 private String actNumber;
	 /**
	  * Дата экспертизы
	  */
	 @Comment("Дата экспертизы")
	 @Persist @DoDateString @DateString @Required
	 public String getExaminationDate() {
	  return examinationDate;
	 }
	 /**
	  * Дата экспертизы
	  */
	 private String examinationDate;
	 /**
	  * Экспертное заключение
	  */
	 @Comment("Экспертное заключение")
	 @Persist @Required
	 public String getExpertDecision() {
	  return expertDecision;
	 }
	 /**
	  * Экспертное заключение
	  */
	 private String expertDecision;
	 /**
	  * Докладчик
	  */
	 @Comment("Докладчик")
	 @Persist @DoUpperCase @Required
	 public String getReporter() {
	  return reporter;
	 }
	 /**
	  * Докладчик
	  */
	 private String reporter;
	 /**
	  * Описание акта
	  */
	 @Comment("Описание акта")
	 @Persist
	 public String getActNotes() {
	  return actNotes;
	 }
	 /**
	  * Описание акта
	  */
	 private String actNotes;
	 /**
	  * Карта обратившихся за психиатрической помощью
	  */
	 @Comment("Карта обратившихся за психиатрической помощью")
	 @Persist @Required
	 public Long getCareCard() {
	  return careCard;
	 }
	 /**
	  * Карта обратившихся за психиатрической помощью
	  */
	 private Long careCard;
	 /**
	  * Вид экспертизы
	  */
	 @Comment("Вид экспертизы")
	 @Persist @Required
	 public Long getKind() {
	  return kind;
	 }
	 /**
	  * Вид экспертизы
	  */
	 private Long kind;
	 /**
	  * Вид уголовного дела
	  */
	 @Comment("Вид уголовного дела")
	 @Persist @Required
	 public Long getCriminalCase() {
	  return criminalCase;
	 }
	 /**
	  * Вид уголовного дела
	  */
	 private Long criminalCase;
	 /**
	  * Статья уголовного кодекса
	  */
	 @Comment("Статья уголовного кодекса")
	 @Persist @Required
	 public Long getCriminalCodeArtical() {
	  return criminalCodeArtical;
	 }
	 /**
	  * Статья уголовного кодекса
	  */
	 private Long criminalCodeArtical;
	 /**
	  * Вид участия в экспертизе
	  */
	 @Comment("Вид участия в экспертизе")
	 @Persist @Required
	 public Long getPaticipation() {
	  return paticipation;
	 }
	 /**
	  * Вид участия в экспертизе
	  */
	 private Long paticipation;
	 
	 /** Вид уголовного дела (ИНФО) */
	@Comment("Вид уголовного дела (ИНФО)")
	@Persist
	public String getCriminalCaseInfo() {
		return criminalCaseInfo;
	}

	/** Вид уголовного дела (ИНФО) */
	private String criminalCaseInfo;
	
	/** Вид уголовного дела (ИНФО) */
	@Comment("Вид уголовного дела (ИНФО)")
	@Persist
	public String getCriminalCodeArticalInfo() {
		return criminalCodeArticalInfo;
	}

	/** Вид уголовного дела (ИНФО) */
	private String criminalCodeArticalInfo;
	
	/** Вид экспертизы */
	@Comment("Вид экспертизы")
	@Persist
	public String getKindInfo() {
		return kindInfo;
	}

	/** Вид экспертизы */
	private String kindInfo;
	
	/** Вид участия в экспертизе */
	@Comment("Вид участия в экспертизе")
	@Persist
	public String getPaticipationInfo() {
		return paticipationInfo;
	}

	/** Вид участия в экспертизе */
	private String paticipationInfo;
}
