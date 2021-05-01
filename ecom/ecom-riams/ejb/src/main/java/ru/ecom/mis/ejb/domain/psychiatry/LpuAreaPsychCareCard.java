package ru.ecom.mis.ejb.domain.psychiatry;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychObservationReason;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychOtherPlaceTransfer;
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
 @Getter
 @Setter
public class LpuAreaPsychCareCard extends BaseEntity{
 /**
  * Дата прихода на участок
  */
 private Date startDate;
 /**
  * Дата выбытия с участка
  */
 private Date finishDate;
 /**
  * Участок ЛПУ
  */
 @Comment("Участок ЛПУ")
 @ManyToOne
 public LpuArea getLpuArea() {
  return lpuArea;
 }
 /**
  * Участок ЛПУ
  */
 private LpuArea lpuArea;
 /**
  * Карта обративщегося за психиатрической помощью
  */
 @Comment("Карта обративщегося за психиатрической помощью")
 @ManyToOne
 public PsychiatricCareCard getCareCard() {
  return careCard;
 }


	/** Причина перевода */
	@Comment("Причина перевода")
	@OneToOne
	public VocPsychTransferReason getTransferReason() {
		return transferReason;
	}

	/** Причина взятия */
	@Comment("Причина взятия")
	@OneToOne
	public VocPsychObservationReason getObservationReason() {
		return observationReason;
	}

	/** Причина снятия с учета */
	@Comment("Причина снятия с учета")
	@OneToOne
	public VocPsychStrikeOffReason getStikeOffReason() {
		return stikeOffReason;
	}

	/** Причина снятия с учета */
	private VocPsychStrikeOffReason stikeOffReason;

	/** Причина взятия */
	private VocPsychObservationReason observationReason;

	/** Причина перевода */
	private VocPsychTransferReason transferReason;
	/** Дата перевода */
	private Date transferDate;
 /**
  * Карта обративщегося за психиатрической помощью
  */
 private PsychiatricCareCard careCard;
 
 @Comment("Участок ЛПУ (ИНФО)")
 @Transient
 public String getLpuAreaInfo() {
	 return lpuArea!=null? lpuArea.getName():"" ;
 }
 /**
  * Наблюдения
  */
 @Comment("Наблюдения")
 @OneToMany(mappedBy="lpuAreaPsychCareCard", cascade=CascadeType.ALL)
 public List<PsychiaticObservation> getObservations() {
  return observations;
 }
 /**
  * Наблюдения
  */
 private List<PsychiaticObservation> observations;
 
/** Пользователь, последний редактировавший запись */
private String editUsername;
/** Пользователь, создавший запись */
private String createUsername;

/** Дата редактирования */
private Date editDate;
/** Дата создания */
private Date createDate;

/** Др.место перевода */
@Comment("Др.место перевода")
@OneToOne
public VocPsychOtherPlaceTransfer getOtherPlaceTransfer() {
	return otherPlaceTransfer;
}

/** Др.место перевода */
private VocPsychOtherPlaceTransfer otherPlaceTransfer;
}
