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
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychObservationReason;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychStrikeOffReason;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychTransferReason;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Карта обратившегося за психиатрической помощью по участку
  */
 @Comment("Карта обратившегося за психиатрической помощью по участку")
@Entity
@AIndexes({
	@AIndex(properties={"careCard"})
})
@Table(schema="SQLUser")
public class LpuAreaPsychCareCard extends BaseEntity{
 /**
  * Дата прихода на участок
  */
 @Comment("Дата прихода на участок")
 public Date getStartDate() {
  return theStartDate;
 }
 public void setStartDate(Date aStartDate) {
  theStartDate = aStartDate;
 }
 /**
  * Дата прихода на участок
  */
 private Date theStartDate;
 /**
  * Дата выбытия с участка
  */
 @Comment("Дата выбытия с участка")
 public Date getFinishDate() {
  return theFinishDate;
 }
 public void setFinishDate(Date aFinishDate) {
  theFinishDate = aFinishDate;
 }
 /**
  * Дата выбытия с участка
  */
 private Date theFinishDate;
 /**
  * Участок ЛПУ
  */
 @Comment("Участок ЛПУ")
 @ManyToOne
 public LpuArea getLpuArea() {
  return theLpuArea;
 }
 public void setLpuArea(LpuArea aLpuArea) {
  theLpuArea = aLpuArea;
 }
 /**
  * Участок ЛПУ
  */
 private LpuArea theLpuArea;
 /**
  * Карта обративщегося за психиатрической помощью
  */
 @Comment("Карта обративщегося за психиатрической помощью")
 @ManyToOne
 public PsychiatricCareCard getCareCard() {
  return theCareCard;
 }
 public void setCareCard(PsychiatricCareCard aCareCard) {
  theCareCard = aCareCard;
 }
 
	 /** Дата перевода */
	@Comment("Дата перевода")
	public Date getTransferDate() {return theTransferDate;}
	public void setTransferDate(Date aTransferDate) {theTransferDate = aTransferDate;}
	
	/** Причина перевода */
	@Comment("Причина перевода")
	@OneToOne
	public VocPsychTransferReason getTransferReason() {
		return theTransferReason;
	}

	public void setTransferReason(VocPsychTransferReason aTransferReason) {
		theTransferReason = aTransferReason;
	}
	
	/** Причина взятия */
	@Comment("Причина взятия")
	@OneToOne
	public VocPsychObservationReason getObservationReason() {
		return theObservationReason;
	}

	public void setObservationReason(VocPsychObservationReason aObservationReason) {
		theObservationReason = aObservationReason;
	}
	
	/** Причина снятия с учета */
	@Comment("Причина снятия с учета")
	@OneToOne
	public VocPsychStrikeOffReason getStikeOffReason() {
		return theStikeOffReason;
	}

	public void setStikeOffReason(VocPsychStrikeOffReason aStikeOffReason) {
		theStikeOffReason = aStikeOffReason;
	}

	/** Причина снятия с учета */
	private VocPsychStrikeOffReason theStikeOffReason;

	/** Причина взятия */
	private VocPsychObservationReason theObservationReason;

	/** Причина перевода */
	private VocPsychTransferReason theTransferReason;
	/** Дата перевода */
	private Date theTransferDate;
 /**
  * Карта обративщегося за психиатрической помощью
  */
 private PsychiatricCareCard theCareCard;
 
 @Comment("Участок ЛПУ (ИНФО)")
 @Transient
 public String getLpuAreaInfo() {
	 return theLpuArea!=null? theLpuArea.getName():"" ;
 }
}
