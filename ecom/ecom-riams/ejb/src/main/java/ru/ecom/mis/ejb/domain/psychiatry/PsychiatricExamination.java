package ru.ecom.mis.ejb.domain.psychiatry;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
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
 @Getter
 @Setter
public class PsychiatricExamination extends BaseEntity{
 /**
  * Номер акта
  */
 private String actNumber;
 /**
  * Дата экспертизы
  */
 private Date examinationDate;
 /**
  * Экспертное заключение
  */
 private String expertDecision;
 /**
  * Докладчик
  */
 private String reporter;
 /**
  * Описание акта
  */
 private String actNotes;
 /**
  * Карта обратившихся за психиатрической помощью
  */
 @Comment("Карта обратившихся за психиатрической помощью")
 @ManyToOne
 public PsychiatricCareCard getCareCard() {
  return careCard;
 }
 /**
  * Карта обратившихся за психиатрической помощью
  */
 private PsychiatricCareCard careCard;
 /**
  * Вид экспертизы
  */
 @Comment("Вид экспертизы")
 @OneToOne
 public VocPsychExamination getKind() {
  return kind;
 }
 /**
  * Вид экспертизы
  */
 private VocPsychExamination kind;
 /**
  * Вид уголовного дела
  */
 @Comment("Вид уголовного дела")
 @OneToOne
 public VocCriminalCase getCriminalCase() {
  return criminalCase;
 }
 /**
  * Вид уголовного дела
  */
 private VocCriminalCase criminalCase;
 /**
  * Статья уголовного кодекса
  */
 @Comment("Статья уголовного кодекса")
 @OneToOne
 public VocCriminalCodeArticle getCriminalCodeArtical() {
  return criminalCodeArtical;
 }
 /**
  * Статья уголовного кодекса
  */
 private VocCriminalCodeArticle criminalCodeArtical;
 /**
  * Вид участия в экспертизе
  */
 @Comment("Вид участия в экспертизе")
 @OneToOne
 public VocPsychExamPaticipation getPaticipation() {
  return paticipation;
 }
 /**
  * Вид участия в экспертизе
  */
 private VocPsychExamPaticipation paticipation;
 @Comment("Вид уголовного дела (ИНФО)")
 @Transient
 public String getCriminalCaseInfo() {
	 return criminalCase!=null?criminalCase.getName():"" ;
 }
 
 @Comment("Статья уголовного кодекса (ИНФО)")
 @Transient
 public String getCriminalCodeArticalInfo() {
	 return criminalCodeArtical!=null?criminalCodeArtical.getName():"" ;
 }
 
 @Comment("Вид экспертизы (ИНФО)")
 @Transient
 public String getKindInfo() {
	 return kind!=null?kind.getName():"" ;
 }
 
 @Comment("Вид участия в экспертизе (ИНФО)")
 @Transient
 public String getPaticipationInfo() {
	 return paticipation!=null?paticipation.getName():"" ;
 }
 
}
