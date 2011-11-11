package ru.ecom.mis.ejb.domain.psychiatry;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocCriminalCodeArticle;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocLawCourt;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychCompulsoryTreatment;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Принудительное лечение
  */
 @Comment("Принудительное лечение")
@Entity
@AIndexes({
	@AIndex(properties={"careCard"})
})
@Table(schema="SQLUser")
public class CompulsoryTreatment extends BaseEntity{
 /**
  * Порядковый номер лечения
  */
 @Comment("Порядковый номер лечения")
 public int getOrderNumber() {
  return theOrderNumber;
 }
 public void setOrderNumber(int aOrderNumber) {
  theOrderNumber = aOrderNumber;
 }
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
 /**
  * Порядковый номер лечения
  */
 private int theOrderNumber;
 /**
  * Дата решения суда
  */
 @Comment("Дата решения суда")
 public Date getDecisionDate() {
  return theDecisionDate;
 }
 public void setDecisionDate(Date aDecisionDate) {
  theDecisionDate = aDecisionDate;
 }
 /**
  * Дата решения суда
  */
 private Date theDecisionDate;
 /**
  * Описание решения
  */
 @Comment("Описание решения")
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
 @ManyToOne
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
  * Вид принудительного лечения
  */
 @Comment("Вид принудительного лечения")
 @OneToOne
 public VocPsychCompulsoryTreatment getKind() {
  return theKind;
 }
 public void setKind(VocPsychCompulsoryTreatment aKind) {
  theKind = aKind;
 }
 /**
  * Вид принудительного лечения
  */
 private VocPsychCompulsoryTreatment theKind;
 /**
  * Суд, принявший решение
  */
 @Comment("Суд, принявший решение")
 @OneToOne
 public VocLawCourt getLawCourt() {
  return theLawCourt;
 }
 public void setLawCourt(VocLawCourt aLawCourt) {
  theLawCourt = aLawCourt;
 }
 /**
  * Суд, принявший решение
  */
 private VocLawCourt theLawCourt;
 /**
  * Статья уголовного кодекса
  */
 @Comment("Статья уголовного кодекса")
 @OneToOne
 public VocCriminalCodeArticle getCrimainalCodeArticle() {
  return theCrimainalCodeArticle;
 }
 public void setCrimainalCodeArticle(VocCriminalCodeArticle aCrimainalCodeArticle) {
  theCrimainalCodeArticle = aCrimainalCodeArticle;
 }
 /**
  * Статья уголовного кодекса
  */
 private VocCriminalCodeArticle theCrimainalCodeArticle;
 /**
  * Психиатрическая экспертиза
  */
 @Comment("Психиатрическая экспертиза")
 @OneToOne
 public PsychiatricExamination getPsychatricExamination() {
  return thePsychatricExamination;
 }
 public void setPsychatricExamination(PsychiatricExamination aPsychatricExamination) {
  thePsychatricExamination = aPsychatricExamination;
 }
 /**
  * Психиатрическая экспертиза
  */
 private PsychiatricExamination thePsychatricExamination;
 

 @Comment("Статья уголовного кодекса инфо")
 @Transient
 public String getCrimainalCodeArticleInfo() {
	 return theCrimainalCodeArticle!=null?theCrimainalCodeArticle.getName():"" ;
 }
 @Comment("Суд, принявший решение инфо")
 @Transient
 public String getLawCourtInfo() {
	 return theLawCourt!=null?theLawCourt.getName():"" ;
 }
 @Comment("Вид принудительного решения инфо")
 @Transient
 public String getKindInfo() {
	 return theKind!=null?theKind.getCode():"" ;
 }
 @Comment("Психиатрическая экспертиза инфо")
 @Transient
 public String getPsychatricExaminationInfo() {
	 return thePsychatricExamination!=null?thePsychatricExamination.getExpertDecision():"" ;
 }
 
	 /** Дата замены */
	@Comment("Дата замены")
	public Date getDateReplace() {return theDateReplace;}
	public void setDateReplace(Date aDateReplace) {theDateReplace = aDateReplace;}
	
	/** Суд, заменивший тип принудительного лечения */
	@Comment("Суд, заменивший тип принудительного лечения")
	@OneToOne
	public VocLawCourt getLawCourtReplace() {return theLawCourtReplace;}
	public void setLawCourtReplace(VocLawCourt aLawCourtReplace) {theLawCourtReplace = aLawCourtReplace;}

	/** Решение заменено на */
	@Comment("Решение заменено на")
	@OneToOne
	public VocPsychCompulsoryTreatment getCourtDecisionReplace() {
		return theCourtDecisionReplace;
	}

	public void setCourtDecisionReplace(VocPsychCompulsoryTreatment aCourtDecisionReplace) {
		theCourtDecisionReplace = aCourtDecisionReplace;
	}

	/** Решение заменено на */
	private VocPsychCompulsoryTreatment theCourtDecisionReplace;
	/** Суд, заменивший тип принудительного лечения */
	private VocLawCourt theLawCourtReplace;
	/** Дата замены */
	private Date theDateReplace;


}
