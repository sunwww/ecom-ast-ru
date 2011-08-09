package ru.ecom.mis.ejb.form.psychiatry;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.psychiatry.LpuAreaPsychCareCard;
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
@EntityFormPersistance(clazz = LpuAreaPsychCareCard.class)
@Comment("Движение по участку")
@WebTrail(comment = "Движение по участку", nameProperties= "id",list="entityParentList-psych_lpuAreaPsychCareCard.do",listComment="список по пациенту", view="entityParentView-psych_lpuAreaPsychCareCard.do")
@Parent(property="careCard", parentForm=PsychiatricCareCardForm.class,orderBy="startDate")
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard")
public class LpuAreaPsychCareCardForm extends IdEntityForm {
	 /**
	  * Дата прихода на участок
	  */
	 @Comment("Дата прихода на участок")
	 @DateString @DoDateString @Persist @Required
	 public String getStartDate() {
	  return theStartDate;
	 }
	 public void setStartDate(String aStartDate) {
	  theStartDate = aStartDate;
	 }
	 /**
	  * Дата прихода на участок
	  */
	 private String theStartDate;
	 /**
	  * Дата выбытия с участка
	  */
	 @Comment("Дата выбытия с участка")
	 @DateString @DoDateString @Persist
	 public String getFinishDate() {
	  return theFinishDate;
	 }
	 public void setFinishDate(String aFinishDate) {
	  theFinishDate = aFinishDate;
	 }
	 /**
	  * Дата выбытия с участка
	  */
	 private String theFinishDate;
	 /**
	  * Участок ЛПУ
	  */
	 @Comment("Участок ЛПУ")
	 @Persist @Required
	 public Long getLpuArea() {
	  return theLpuArea;
	 }
	 public void setLpuArea(Long aLpuArea) {
	  theLpuArea = aLpuArea;
	 }
	 /**
	  * Участок ЛПУ
	  */
	 private Long theLpuArea;
	 /**
	  * Карта обративщегося за психиатрической помощью
	  */
	 @Comment("Карта обративщегося за психиатрической помощью")
	 @Persist @Required
	 public Long getCareCard() {
	  return theCareCard;
	 }
	 public void setCareCard(Long aCareCard) {
	  theCareCard = aCareCard;
	 }
	 /**
	  * Карта обративщегося за психиатрической помощью
	  */
	 private Long theCareCard;
	 
	 /** Участок ЛПУ (ИНФО) */
	@Comment("Участок ЛПУ (ИНФО)")
	@Persist
	public String getLpuAreaInfo() {
		return theLpuAreaInfo;
	}

	public void setLpuAreaInfo(String aLpuAreaInfo) {
		theLpuAreaInfo = aLpuAreaInfo;
	}

	 /** Дата перевода */
	@Comment("Дата перевода")
	@Persist @DateString @DoDateString
	public String getTransferDate() {return theTransferDate;}
	public void setTransferDate(String aTransferDate) {theTransferDate = aTransferDate;}
	
	/** Причина перевода */
	@Comment("Причина перевода")
	@Persist
	public Long getTransferReason() {return theTransferReason;}
	public void setTransferReason(Long aTransferReason) {theTransferReason = aTransferReason;}
	
	/** Причина взятия */
	@Comment("Причина взятия")
	@Persist @Required
	public Long getObservationReason() {return theObservationReason;}
	public void setObservationReason(Long aObservationReason) {	theObservationReason = aObservationReason;}
	
	/** Причина снятия с учета */
	@Comment("Причина снятия с учета")
	@Persist
	public Long getStikeOffReason() {return theStikeOffReason;}
	public void setStikeOffReason(Long aStikeOffReason) {theStikeOffReason = aStikeOffReason;}

	/** Причина снятия с учета */
	private Long theStikeOffReason;
	/** Причина взятия */
	private Long theObservationReason;
	/** Причина перевода */
	private Long theTransferReason;
	/** Дата перевода */
	private String theTransferDate;
	/** Участок ЛПУ (ИНФО) */
	private String theLpuAreaInfo;
}
