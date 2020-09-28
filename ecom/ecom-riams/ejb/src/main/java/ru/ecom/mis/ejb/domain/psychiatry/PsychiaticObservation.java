package ru.ecom.mis.ejb.domain.psychiatry;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocCriminalCodeArticle;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychAmbulatoryCare;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychDispensaryGroup;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychStrikeOffReasonAdn;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
 /**
  * Психиатрическое наблюдение
  */
 @Comment("Психиатрическое наблюдение")
@Entity
@AIndexes({
	@AIndex(properties={"careCard"})
	,@AIndex(properties={"lpuAreaPsychCareCard"})
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
 
 /** Участок */
@Comment("Участок")
@ManyToOne
public LpuAreaPsychCareCard getLpuAreaPsychCareCard() {
	return theLpuAreaPsychCareCard;
}

public void setLpuAreaPsychCareCard(LpuAreaPsychCareCard aLpuAreaPsychCareCard) {
	theLpuAreaPsychCareCard = aLpuAreaPsychCareCard;
}

/** Участок */
private LpuAreaPsychCareCard theLpuAreaPsychCareCard;
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
	 return theAmbulatoryCare!=null? theAmbulatoryCare.getName()
             //.append(". ").append(theAmbulatoryCare.getName())
             : ""  ;
 }
 @Comment("Диспансерная группа (ИНФО)")
 @Transient
 public String getDispensaryGroupInfo() {
	 return theDispensaryGroup!=null? theDispensaryGroup.getName()
             //.append(". ").append(theDispensaryGroup.getName())
             : "" ;
 }
 
	 /** Дата окончания наблюдения */
	@Comment("Дата окончания наблюдения")
	public Date getFinishDate() {
		return theFinishDate;
	}
	
	public void setFinishDate(Date aFinishDate) {
		theFinishDate = aFinishDate;
	}
	
	/** Дата окончания наблюдения */
	private Date theFinishDate;
	
	/** Статья уголовного кодекса */
	@Comment("Статья уголовного кодекса")
	@OneToOne
	public VocCriminalCodeArticle getCriminalCodeArticle() {
		return theCriminalCodeArticle;
	}

	public void setCriminalCodeArticle(VocCriminalCodeArticle aCriminalCodeArticle) {
		theCriminalCodeArticle = aCriminalCodeArticle;
	}

	/** Статья уголовного кодекса */
	private VocCriminalCodeArticle theCriminalCodeArticle;
	
	/** Причина снятия */
	@Comment("Причина снятия")
	@OneToOne
	public VocPsychStrikeOffReasonAdn getStrikeOffReason() {
		return theStrikeOffReason;
	}

	public void setStrikeOffReason(VocPsychStrikeOffReasonAdn aStrikeOffReason) {
		theStrikeOffReason = aStrikeOffReason;
	}

	/** Причина снятия */
	private VocPsychStrikeOffReasonAdn theStrikeOffReason;
}
