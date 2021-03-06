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
 /**
  * Наблюдения
  */
 @Comment("Наблюдения")
 @OneToMany(mappedBy="lpuAreaPsychCareCard", cascade=CascadeType.ALL)
 public List<PsychiaticObservation> getObservations() {
  return theObservations;
 }
 public void setObservations(List<PsychiaticObservation> aObservations) {
  theObservations = aObservations;
 }
 /**
  * Наблюдения
  */
 private List<PsychiaticObservation> theObservations;
 
 /** Дата создания */
@Comment("Дата создания")
public Date getCreateDate() {
	return theCreateDate;
}

public void setCreateDate(Date aCreateDate) {
	theCreateDate = aCreateDate;
}
/** Дата редактирования */
@Comment("Дата редактирования")
public Date getEditDate() {
	return theEditDate;
}

public void setEditDate(Date aEditDate) {
	theEditDate = aEditDate;
}
/** Пользователь, создавший запись */
@Comment("Пользователь, создавший запись")
public String getCreateUsername() {
	return theCreateUsername;
}

public void setCreateUsername(String aCreateUsername) {
	theCreateUsername = aCreateUsername;
}
/** Пользователь, последний редактировавший запись */
@Comment("Пользователь, последний редактировавший запись")
public String getEditUsername() {
	return theEditUsername;
}

public void setEditUsername(String aEditUsername) {
	theEditUsername = aEditUsername;
}

/** Пользователь, последний редактировавший запись */
private String theEditUsername;
/** Пользователь, создавший запись */
private String theCreateUsername;

/** Дата редактирования */
private Date theEditDate;
/** Дата создания */
private Date theCreateDate;

/** Др.место перевода */
@Comment("Др.место перевода")
@OneToOne
public VocPsychOtherPlaceTransfer getOtherPlaceTransfer() {
	return theOtherPlaceTransfer;
}

public void setOtherPlaceTransfer(VocPsychOtherPlaceTransfer aOtherPlaceTransfer) {
	theOtherPlaceTransfer = aOtherPlaceTransfer;
}

/** Др.место перевода */
private VocPsychOtherPlaceTransfer theOtherPlaceTransfer;
}
