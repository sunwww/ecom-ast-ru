package ru.ecom.mis.ejb.domain.psychiatry;

import lombok.Getter;
import lombok.Setter;
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
 @Getter
 @Setter
public class PsychiaticObservation extends BaseEntity{
 /**
  * Карта обратившегося за психиатрической помощью
  */
 @Comment("Карта обратившегося за психиатрической помощью")
 @ManyToOne
 public PsychiatricCareCard getCareCard() {
  return careCard;
 }
 /**
  * Карта обратившегося за психиатрической помощью
  */
 private PsychiatricCareCard careCard;
 
 /** Участок */
@Comment("Участок")
@ManyToOne
public LpuAreaPsychCareCard getLpuAreaPsychCareCard() {
	return lpuAreaPsychCareCard;
}

/** Участок */
private LpuAreaPsychCareCard lpuAreaPsychCareCard;
 /**
  * 
  */
 @Comment("")
 @OneToOne
 public VocPsychDispensaryGroup getDispensaryGroup() {
  return dispensaryGroup;
 }
 /**
  * 
  */
 private VocPsychDispensaryGroup dispensaryGroup;
 /**
  * Вид амбулаторного наблюдения
  */
 @Comment("Вид амбулаторного наблюдения")
 @OneToOne
 public VocPsychAmbulatoryCare getAmbulatoryCare() {
  return ambulatoryCare;
 }
 /**
  * Вид амбулаторного наблюдения
  */
 private VocPsychAmbulatoryCare ambulatoryCare;
 /**
  * Дата начала наблюдения
  */
 private Date startDate;
 
 @Comment("Вид амбулаторного наблюдения (ИНФО)	")
 @Transient
 public String getAmbulatoryCareInfo() {
	 return ambulatoryCare!=null? ambulatoryCare.getName()
             : ""  ;
 }
 @Comment("Диспансерная группа (ИНФО)")
 @Transient
 public String getDispensaryGroupInfo() {
	 return dispensaryGroup!=null? dispensaryGroup.getName()
             : "" ;
 }
 
	/** Дата окончания наблюдения */
	private Date finishDate;
	
	/** Статья уголовного кодекса */
	@Comment("Статья уголовного кодекса")
	@OneToOne
	public VocCriminalCodeArticle getCriminalCodeArticle() {
		return criminalCodeArticle;
	}

	/** Статья уголовного кодекса */
	private VocCriminalCodeArticle criminalCodeArticle;
	
	/** Причина снятия */
	@Comment("Причина снятия")
	@OneToOne
	public VocPsychStrikeOffReasonAdn getStrikeOffReason() {
		return strikeOffReason;
	}

	/** Причина снятия */
	private VocPsychStrikeOffReasonAdn strikeOffReason;
}
