package ru.ecom.mis.ejb.domain.equipment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.equipment.voc.VocTypeEquip;
import ru.ecom.mis.ejb.domain.medstandard.MedicalStandard;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Позиция мед. оборудования")
@Table(schema="SQLUser")

public class MedicalEquipmentPosition extends BaseEntity {

	/** Медицинский стандарт */
	@Comment("Медицинский стандарт")
	@ManyToOne
	public MedicalStandard getStandard() {return theStandard;}
	public void setStandard(MedicalStandard aStandard) {theStandard = aStandard;}
	/** Медицинский стандарт */
	private MedicalStandard theStandard;
	
	/** Тип оборудования */
	@Comment("Тип Оборудования")
	@OneToOne
	public VocTypeEquip getEquipmentType() {return theEquipmentType;}
	public void setEquipmentType(VocTypeEquip aEquipmentType) {theEquipmentType = aEquipmentType;}
	/** Оборудование */
	private VocTypeEquip theEquipmentType;
	
	/** Необходимое количество */
	@Comment("Необходимое количество")
	public Long getAmount() {return theAmount;}
	public void setAmount(Long aAmount) {theAmount = aAmount;}
	/** Необходимое количество */
	private Long theAmount;
	
	/** Примечание */
	@Comment("Примечание")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	/** Примечание */
	private String theComment;
}
