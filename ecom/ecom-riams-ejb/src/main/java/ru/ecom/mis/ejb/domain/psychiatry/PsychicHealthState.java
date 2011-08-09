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
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsyhicHealthState;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Состояние психического здоровья
  */
 @Comment("Состояние психического здоровья")
@Entity
@AIndexes({
	@AIndex(properties={"careCard"})
})
@Table(schema="SQLUser")
public class PsychicHealthState extends BaseEntity{
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
  * Дата начала
  */
 @Comment("Дата начала")
 public Date getStartDate() {
  return theStartDate;
 }
 public void setStartDate(Date aStartDate) {
  theStartDate = aStartDate;
 }
 /**
  * Дата начала
  */
 private Date theStartDate;
 /**
  * Вид состояния психического здоровья
  */
 @Comment("Вид состояния психического здоровья")
 @OneToOne
 public VocPsyhicHealthState getKind() {
  return theKind;
 }
 public void setKind(VocPsyhicHealthState aKind) {
  theKind = aKind;
 }
 /**
  * Вид состояния психического здоровья
  */
 private VocPsyhicHealthState theKind;
 /**
  * Дата окончания
  */
 @Comment("Дата окончания")
 public Date getFinishDate() {
  return theFinishDate;
 }
 public void setFinishDate(Date aFinishDate) {
  theFinishDate = aFinishDate;
 }
 /**
  * Дата окончания
  */
 private Date theFinishDate;
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
 /** Вид состояния психического здоровья */
@Comment("Вид состояния психического здоровья")
@Transient
public String getKindInfo() {
	return theKind!=null?theKind.getName():"";
}

}
