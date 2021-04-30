package ru.ecom.mis.ejb.form.psychiatry;

import lombok.Setter;
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
@Setter
public class LpuAreaPsychCareCardForm extends IdEntityForm {
	 /**
	  * Дата прихода на участок
	  */
	 @Comment("Дата прихода на участок")
	 @DateString @DoDateString @Persist @Required
	 public String getStartDate() {
	  return startDate;
	 }
	 /**
	  * Дата прихода на участок
	  */
	 private String startDate;
	 /**
	  * Дата выбытия с участка
	  */
	 @Comment("Дата выбытия с участка")
	 @DateString @DoDateString @Persist
	 public String getFinishDate() {
	  return finishDate;
	 }
	 /**
	  * Дата выбытия с участка
	  */
	 private String finishDate;
	 /**
	  * Участок ЛПУ
	  */
	 @Comment("Участок ЛПУ")
	 @Persist @Required
	 public Long getLpuArea() {
	  return lpuArea;
	 }
	 /**
	  * Участок ЛПУ
	  */
	 private Long lpuArea;
	 /**
	  * Карта обративщегося за психиатрической помощью
	  */
	 @Comment("Карта обративщегося за психиатрической помощью")
	 @Persist @Required
	 public Long getCareCard() {
	  return careCard;
	 }
	 /**
	  * Карта обративщегося за психиатрической помощью
	  */
	 private Long careCard;
	 
	 /** Участок ЛПУ (ИНФО) */
	@Comment("Участок ЛПУ (ИНФО)")
	@Persist
	public String getLpuAreaInfo() {
		return lpuAreaInfo;
	}

	 /** Дата перевода */
	@Comment("Дата перевода")
	@Persist @DateString @DoDateString
	public String getTransferDate() {return transferDate;}

	/** Причина перевода */
	@Comment("Причина перевода")
	@Persist
	public Long getTransferReason() {return transferReason;}

	/** Причина взятия */
	@Comment("Причина взятия")
	@Persist @Required
	public Long getObservationReason() {return observationReason;}

	/** Причина снятия с учета */
	@Comment("Причина снятия с учета")
	@Persist
	public Long getStikeOffReason() {return stikeOffReason;}

	/** Причина снятия с учета */
	private Long stikeOffReason;
	/** Причина взятия */
	private Long observationReason;
	/** Причина перевода */
	private Long transferReason;
	/** Дата перевода */
	private String transferDate;
	/** Участок ЛПУ (ИНФО) */
	private String lpuAreaInfo;
	
	 /** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return createDate;}
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {return editDate;}
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	/** Пользователь, последний редактировавший запись */
	@Comment("Пользователь, последний редактировавший запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Пользователь, последний редактировавший запись */
	private String editUsername;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Дата редактирования */
	private String editDate;
	/** Дата создания */
	private String createDate;
	
	/** Наблюдения */
	@Comment("Наблюдения")
	public Long getAmbulatoryCare() {return ambulatoryCare;}

	/** Группа */
	@Comment("Группа")
	public Long getDispensaryGroup() {
		return dispensaryGroup;
	}

	/** Группа */
	private Long dispensaryGroup;
	/** Наблюдения */
	private Long ambulatoryCare;
	
	/** old date start */
	@Comment("old date start")
	public String getOldStartDate() {
		return oldStartDate;
	}

	/** old finish date */
	@Comment("old finish date")
	public String getOldFinishDate() {
		return oldFinishDate;
	}

	/** old finish date */
	private String oldFinishDate;
	/** old date start */
	private String oldStartDate;
	
	@Comment("Др.место перевода")
	@Persist
	public Long getOtherPlaceTransfer() {
		return otherPlaceTransfer;
	}

	/** Др.место перевода */
	private Long otherPlaceTransfer;
}
