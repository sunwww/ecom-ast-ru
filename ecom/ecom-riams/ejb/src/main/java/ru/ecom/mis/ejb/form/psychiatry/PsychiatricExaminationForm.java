package ru.ecom.mis.ejb.form.psychiatry;

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
public class PsychiatricExaminationForm extends IdEntityForm {
	 /**
	  * Номер акта
	  */
	 @Comment("Номер акта")
	 @Persist @Required
	 public String getActNumber() {
	  return theActNumber;
	 }
	 public void setActNumber(String aActNumber) {
	  theActNumber = aActNumber;
	 }
	 /**
	  * Номер акта
	  */
	 private String theActNumber;
	 /**
	  * Дата экспертизы
	  */
	 @Comment("Дата экспертизы")
	 @Persist @DoDateString @DateString @Required
	 public String getExaminationDate() {
	  return theExaminationDate;
	 }
	 public void setExaminationDate(String aExaminationDate) {
	  theExaminationDate = aExaminationDate;
	 }
	 /**
	  * Дата экспертизы
	  */
	 private String theExaminationDate;
	 /**
	  * Экспертное заключение
	  */
	 @Comment("Экспертное заключение")
	 @Persist @Required
	 public String getExpertDecision() {
	  return theExpertDecision;
	 }
	 public void setExpertDecision(String aExpertDecision) {
	  theExpertDecision = aExpertDecision;
	 }
	 /**
	  * Экспертное заключение
	  */
	 private String theExpertDecision;
	 /**
	  * Докладчик
	  */
	 @Comment("Докладчик")
	 @Persist @DoUpperCase @Required
	 public String getReporter() {
	  return theReporter;
	 }
	 public void setReporter(String aReporter) {
	  theReporter = aReporter;
	 }
	 /**
	  * Докладчик
	  */
	 private String theReporter;
	 /**
	  * Описание акта
	  */
	 @Comment("Описание акта")
	 @Persist
	 public String getActNotes() {
	  return theActNotes;
	 }
	 public void setActNotes(String aActNotes) {
	  theActNotes = aActNotes;
	 }
	 /**
	  * Описание акта
	  */
	 private String theActNotes;
	 /**
	  * Карта обратившихся за психиатрической помощью
	  */
	 @Comment("Карта обратившихся за психиатрической помощью")
	 @Persist @Required
	 public Long getCareCard() {
	  return theCareCard;
	 }
	 public void setCareCard(Long aCareCard) {
	  theCareCard = aCareCard;
	 }
	 /**
	  * Карта обратившихся за психиатрической помощью
	  */
	 private Long theCareCard;
	 /**
	  * Вид экспертизы
	  */
	 @Comment("Вид экспертизы")
	 @Persist @Required
	 public Long getKind() {
	  return theKind;
	 }
	 public void setKind(Long aKind) {
	  theKind = aKind;
	 }
	 /**
	  * Вид экспертизы
	  */
	 private Long theKind;
	 /**
	  * Вид уголовного дела
	  */
	 @Comment("Вид уголовного дела")
	 @Persist @Required
	 public Long getCriminalCase() {
	  return theCriminalCase;
	 }
	 public void setCriminalCase(Long aCriminalCase) {
	  theCriminalCase = aCriminalCase;
	 }
	 /**
	  * Вид уголовного дела
	  */
	 private Long theCriminalCase;
	 /**
	  * Статья уголовного кодекса
	  */
	 @Comment("Статья уголовного кодекса")
	 @Persist @Required
	 public Long getCriminalCodeArtical() {
	  return theCriminalCodeArtical;
	 }
	 public void setCriminalCodeArtical(Long aCriminalCodeArtical) {
	  theCriminalCodeArtical = aCriminalCodeArtical;
	 }
	 /**
	  * Статья уголовного кодекса
	  */
	 private Long theCriminalCodeArtical;
	 /**
	  * Вид участия в экспертизе
	  */
	 @Comment("Вид участия в экспертизе")
	 @Persist @Required
	 public Long getPaticipation() {
	  return thePaticipation;
	 }
	 public void setPaticipation(Long aPaticipation) {
	  thePaticipation = aPaticipation;
	 }
	 /**
	  * Вид участия в экспертизе
	  */
	 private Long thePaticipation;
	 
	 /** Вид уголовного дела (ИНФО) */
	@Comment("Вид уголовного дела (ИНФО)")
	@Persist
	public String getCriminalCaseInfo() {
		return theCriminalCaseInfo;
	}

	public void setCriminalCaseInfo(String aCriminalCaseInfo) {
		theCriminalCaseInfo = aCriminalCaseInfo;
	}

	/** Вид уголовного дела (ИНФО) */
	private String theCriminalCaseInfo;
	
	/** Вид уголовного дела (ИНФО) */
	@Comment("Вид уголовного дела (ИНФО)")
	@Persist
	public String getCriminalCodeArticalInfo() {
		return theCriminalCodeArticalInfo;
	}

	public void setCriminalCodeArticalInfo(String aCriminalCodeArticalInfo) {
		theCriminalCodeArticalInfo = aCriminalCodeArticalInfo;
	}

	/** Вид уголовного дела (ИНФО) */
	private String theCriminalCodeArticalInfo;
	
	/** Вид экспертизы */
	@Comment("Вид экспертизы")
	@Persist
	public String getKindInfo() {
		return theKindInfo;
	}

	public void setKindInfo(String aKindInfo) {
		theKindInfo = aKindInfo;
	}

	/** Вид экспертизы */
	private String theKindInfo;
	
	/** Вид участия в экспертизе */
	@Comment("Вид участия в экспертизе")
	@Persist
	public String getPaticipationInfo() {
		return thePaticipationInfo;
	}

	public void setPaticipationInfo(String aPaticipationInfo) {
		thePaticipationInfo = aPaticipationInfo;
	}

	/** Вид участия в экспертизе */
	private String thePaticipationInfo;
}
