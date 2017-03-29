package ru.ecom.mis.ejb.form.psychiatry;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.psychiatry.LpuAreaPsychCareCard;
import ru.ecom.mis.ejb.form.psychiatry.interceptor.LpuAreaPsychPreCreateInterceptor;
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
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(LpuAreaPsychPreCreateInterceptor.class)
)
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
	
	 /** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, последний редактировавший запись */
	@Comment("Пользователь, последний редактировавший запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, последний редактировавший запись */
	private String theEditUsername;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Дата редактирования */
	private String theEditDate;
	/** Дата создания */
	private String theCreateDate;
	
	/** Наблюдения */
	@Comment("Наблюдения")
	public Long getAmbulatoryCare() {return theAmbulatoryCare;}
	public void setAmbulatoryCare(Long aObservation) {theAmbulatoryCare = aObservation;}

	/** Группа */
	@Comment("Группа")
	public Long getDispensaryGroup() {
		return theDispensaryGroup;
	}

	public void setDispensaryGroup(Long aDispensaryGroup) {
		theDispensaryGroup = aDispensaryGroup;
	}

	/** Группа */
	private Long theDispensaryGroup;
	/** Наблюдения */
	private Long theAmbulatoryCare;
	
	/** old date start */
	@Comment("old date start")
	public String getOldStartDate() {
		return theOldStartDate;
	}

	public void setOldStartDate(String aOldStartDate) {
		theOldStartDate = aOldStartDate;
	}
	
	/** old finish date */
	@Comment("old finish date")
	public String getOldFinishDate() {
		return theOldFinishDate;
	}

	public void setOldFinishDate(String aOldFinishDate) {
		theOldFinishDate = aOldFinishDate;
	}

	/** old finish date */
	private String theOldFinishDate;
	/** old date start */
	private String theOldStartDate;
	
	@Comment("Др.место перевода")
	@Persist
	public Long getOtherPlaceTransfer() {
		return theOtherPlaceTransfer;
	}

	public void setOtherPlaceTransfer(Long aOtherPlaceTransfer) {
		theOtherPlaceTransfer = aOtherPlaceTransfer;
	}

	/** Др.место перевода */
	private Long theOtherPlaceTransfer;
}
