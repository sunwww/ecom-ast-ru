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
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychAmbulatoryCare;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychDispensaryGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Психиатрическое наблюдение
  */
 @Comment("Психиатрическое наблюдение")
@Entity
@AIndexes({
	@AIndex(properties={"careCard"})
	,@AIndex(properties={"careCard","startDate"})
})
@Table(schema="SQLUser")
public class PsychiaticObservation extends BaseEntity{
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
  * 
  */
 @Comment("")
 @OneToOne
 public VocPsychDispensaryGroup getDispensaryGroup() {
  return theDispensaryGroup;
 }
 public void setDispensaryGroup(VocPsychDispensaryGroup aDispensaryGroup) {
  theDispensaryGroup = aDispensaryGroup;
 }
 /**
  * 
  */
 private VocPsychDispensaryGroup theDispensaryGroup;
 /**
  * Вид амбулаторного наблюдения
  */
 @Comment("Вид амбулаторного наблюдения")
 @OneToOne
 public VocPsychAmbulatoryCare getAmbulatoryCare() {
  return theAmbulatoryCare;
 }
 public void setAmbulatoryCare(VocPsychAmbulatoryCare aAmbulatoryCare) {
  theAmbulatoryCare = aAmbulatoryCare;
 }
 /**
  * Вид амбулаторного наблюдения
  */
 private VocPsychAmbulatoryCare theAmbulatoryCare;
 /**
  * Дата начала наблюдения
  */
 @Comment("Дата начала наблюдения")
 public Date getStartDate() {
  return theStartDate;
 }
 public void setStartDate(Date aStartDate) {
  theStartDate = aStartDate;
 }
 /**
  * Дата начала наблюдения
  */
 private Date theStartDate;
 
 @Comment("Вид амбулаторного наблюдения (ИНФО)	")
 @Transient
 public String getAmbulatoryCareInfo() {
	 return theAmbulatoryCare!=null?new StringBuilder().append(theAmbulatoryCare.getCode()).append(". ").append(theAmbulatoryCare.getName()).toString() : ""  ;
 }
 @Comment("Диспансерная группа (ИНФО)")
 @Transient
 public String getDispensaryGroupInfo() {
	 return theDispensaryGroup!=null?new StringBuilder().append(theDispensaryGroup.getCode()).append(". ").append(theDispensaryGroup.getName()).toString() : "" ;
 }
}
