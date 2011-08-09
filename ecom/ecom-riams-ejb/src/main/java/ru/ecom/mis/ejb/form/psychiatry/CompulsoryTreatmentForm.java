package ru.ecom.mis.ejb.form.psychiatry;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.psychiatry.CompulsoryTreatment;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = CompulsoryTreatment.class)
@Comment("Принудительное лечение")
@WebTrail(comment = "Принудительное лечение", nameProperties= "orderNumber",list="entityParentList-psych_compulsoryTreatment.do", view="entityParentView-psych_compulsoryTreatment.do", listComment="список по пациенту")
@Parent(property="careCard", parentForm=PsychiatricCareCardForm.class,orderBy="decisionDate")
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment")
public class CompulsoryTreatmentForm extends IdEntityForm {
	/**
	  * Порядковый номер лечения
	  */
	 @Comment("Порядковый номер лечения")
	 @Persist @Required
	 public int getOrderNumber() {
	  return theOrderNumber;
	 }
	 public void setOrderNumber(int aOrderNumber) {
	  theOrderNumber = aOrderNumber;
	 }
	 /**
	  * Порядковый номер лечения
	  */
	 private int theOrderNumber;
	 /**
	  * Дата решения суда
	  */
	 @Comment("Дата решения суда")
	 @DateString @DoDateString @Persist @Required
	 public String getDecisionDate() {
	  return theDecisionDate;
	 }
	 public void setDecisionDate(String aDecisionDate) {
	  theDecisionDate = aDecisionDate;
	 }
	 /**
	  * Дата решения суда
	  */
	 private String theDecisionDate;
	 /**
	  * Описание решения
	  */
	 @Comment("Описание решения")
	 @Persist
	 public String getDecisionNotes() {
	  return theDecisionNotes;
	 }
	 public void setDecisionNotes(String aDecisionNotes) {
	  theDecisionNotes = aDecisionNotes;
	 }
	 /**
	  * Описание решения
	  */
	 private String theDecisionNotes;
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 @Comment("Карта обратившегося за психиатрической помощью")
	 @Persist @Required
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
	  * Решение суда
	  */
	 @Comment("Решение суда")
	 @Persist @Required
	 public Long getCourtDecision() {
	  return theCourtDecision;
	 }
	 public void setCourtDecision(Long aCourtDecision) {
	  theCourtDecision = aCourtDecision;
	 }
	 /**
	  * Решение суда
	  */
	 private Long theCourtDecision;
	 /**
	  * Вид принудительного лечения
	  */
	 @Comment("Вид принудительного лечения")
	 @Persist @Required
	 public Long getKind() {
	  return theKind;
	 }
	 public void setKind(Long aKind) {
	  theKind = aKind;
	 }
	 /**
	  * Вид принудительного лечения
	  */
	 private Long theKind;
	 /**
	  * Суд, принявший решение
	  */
	 @Comment("Суд, принявший решение")
	 @Persist @Required
	 public Long getLawCourt() {
	  return theLawCourt;
	 }
	 public void setLawCourt(Long aLawCourt) {
	  theLawCourt = aLawCourt;
	 }
	 /**
	  * Суд, принявший решение
	  */
	 private Long theLawCourt;
	 /**
	  * Статья уголовного кодекса
	  */
	 @Comment("Статья уголовного кодекса")
	 @Persist @Required
	 public Long getCrimainalCodeArticle() {
	  return theCrimainalCodeArticle;
	 }
	 public void setCrimainalCodeArticle(Long aCrimainalCodeArticle) {
	  theCrimainalCodeArticle = aCrimainalCodeArticle;
	 }
	 /**
	  * Статья уголовного кодекса
	  */
	 private Long theCrimainalCodeArticle;
	 /**
	  * Психиатрическая экспертиза
	  */
	 @Comment("Психиатрическая экспертиза")
	 @Persist
	 public Long getPsychatricExamination() {
	  return thePsychatricExamination;
	 }
	 public void setPsychatricExamination(Long aPsychatricExamination) {
	  thePsychatricExamination = aPsychatricExamination;
	 }
	 /**
	  * Психиатрическая экспертиза
	  */
	 private Long thePsychatricExamination;
	 
	 /** Решение суда (инфо) */
	@Comment("Решение суда (инфо)")
	@Persist
	public String getCourtDecisionInfo() {
		return theCourtDecisionInfo;
	}

	public void setCourtDecisionInfo(String aCourtDecisionInfo) {
		theCourtDecisionInfo = aCourtDecisionInfo;
	}

	/** Решение суда (инфо) */
	private String theCourtDecisionInfo;
	
	/** Статья уголовного кодекса (инфо) */
	@Comment("Статья уголовного кодекса (инфо)")
	@Persist
	public String getCrimainalCodeArticleInfo() {
		return theCrimainalCodeArticleInfo;
	}

	public void setCrimainalCodeArticleInfo(String aCrimainalCodeArticleInfo) {
		theCrimainalCodeArticleInfo = aCrimainalCodeArticleInfo;
	}

	/** Статья уголовного кодекса (инфо) */
	private String theCrimainalCodeArticleInfo;
	
	/** Вид принудительного лечения (инфо) */
	@Comment("Вид принудительного лечения (инфо)")
	@Persist
	public String getKindInfo() {
		return theKindInfo;
	}

	public void setKindInfo(String aKindInfo) {
		theKindInfo = aKindInfo;
	}

	/** Вид принудительного лечения (инфо) */
	private String theKindInfo;
	
	/** Суд, принявший решений (инфо) */
	@Comment("Суд, принявший решений (инфо)")
	@Persist
	public String getLawCourtInfo() {
		return theLawCourtInfo;
	}

	public void setLawCourtInfo(String aLawCourtInfo) {
		theLawCourtInfo = aLawCourtInfo;
	}

	/** Суд, принявший решений (инфо) */
	private String theLawCourtInfo;
	
	/** Психиатрическая экспертиза */
	@Comment("Психиатрическая экспертиза")
	@Persist
	public String getPsychatricExaminationInfo() {
		return thePsychatricExaminationInfo;
	}

	public void setPsychatricExaminationInfo(String aPsychatricExaminationInfo) {
		thePsychatricExaminationInfo = aPsychatricExaminationInfo;
	}

	/** Психиатрическая экспертиза */
	private String thePsychatricExaminationInfo;
	
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
}
