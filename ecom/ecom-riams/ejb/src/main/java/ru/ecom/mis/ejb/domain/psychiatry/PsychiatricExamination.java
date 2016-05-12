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
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocCriminalCase;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocCriminalCodeArticle;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychExamination;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychExamPaticipation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Психиатрическая экспертиза
  */
 @Comment("Психиатрическая экспертиза")
@Entity
@AIndexes({
	@AIndex(properties={"careCard"})
})
@Table(schema="SQLUser")
public class PsychiatricExamination extends BaseEntity{
 /**
  * Номер акта
  */
 @Comment("Номер акта")
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
 public Date getExaminationDate() {
  return theExaminationDate;
 }
 public void setExaminationDate(Date aExaminationDate) {
  theExaminationDate = aExaminationDate;
 }
 /**
  * Дата экспертизы
  */
 private Date theExaminationDate;
 /**
  * Экспертное заключение
  */
 @Comment("Экспертное заключение")
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
 @ManyToOne
 public PsychiatricCareCard getCareCard() {
  return theCareCard;
 }
 public void setCareCard(PsychiatricCareCard aCareCard) {
  theCareCard = aCareCard;
 }
 /**
  * Карта обратившихся за психиатрической помощью
  */
 private PsychiatricCareCard theCareCard;
 /**
  * Вид экспертизы
  */
 @Comment("Вид экспертизы")
 @OneToOne
 public VocPsychExamination getKind() {
  return theKind;
 }
 public void setKind(VocPsychExamination aKind) {
  theKind = aKind;
 }
 /**
  * Вид экспертизы
  */
 private VocPsychExamination theKind;
 /**
  * Вид уголовного дела
  */
 @Comment("Вид уголовного дела")
 @OneToOne
 public VocCriminalCase getCriminalCase() {
  return theCriminalCase;
 }
 public void setCriminalCase(VocCriminalCase aCriminalCase) {
  theCriminalCase = aCriminalCase;
 }
 /**
  * Вид уголовного дела
  */
 private VocCriminalCase theCriminalCase;
 /**
  * Статья уголовного кодекса
  */
 @Comment("Статья уголовного кодекса")
 @OneToOne
 public VocCriminalCodeArticle getCriminalCodeArtical() {
  return theCriminalCodeArtical;
 }
 public void setCriminalCodeArtical(VocCriminalCodeArticle aCriminalCodeArtical) {
  theCriminalCodeArtical = aCriminalCodeArtical;
 }
 /**
  * Статья уголовного кодекса
  */
 private VocCriminalCodeArticle theCriminalCodeArtical;
 /**
  * Вид участия в экспертизе
  */
 @Comment("Вид участия в экспертизе")
 @OneToOne
 public VocPsychExamPaticipation getPaticipation() {
  return thePaticipation;
 }
 public void setPaticipation(VocPsychExamPaticipation aPaticipation) {
  thePaticipation = aPaticipation;
 }
 /**
  * Вид участия в экспертизе
  */
 private VocPsychExamPaticipation thePaticipation;
 @Comment("Вид уголовного дела (ИНФО)")
 @Transient
 public String getCriminalCaseInfo() {
	 return theCriminalCase!=null?theCriminalCase.getName():"" ;
 }
 
 @Comment("Статья уголовного кодекса (ИНФО)")
 @Transient
 public String getCriminalCodeArticalInfo() {
	 return theCriminalCodeArtical!=null?theCriminalCodeArtical.getName():"" ;
 }
 
 @Comment("Вид экспертизы (ИНФО)")
 @Transient
 public String getKindInfo() {
	 return theKind!=null?theKind.getName():"" ;
 }
 
 @Comment("Вид участия в экспертизе (ИНФО)")
 @Transient
 public String getPaticipationInfo() {
	 return thePaticipation!=null?thePaticipation.getName():"" ;
 }
 
}
