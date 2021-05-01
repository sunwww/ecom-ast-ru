package ru.ecom.mis.ejb.domain.equipment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.equipment.voc.VocTypeEquip;
import ru.ecom.mis.ejb.domain.medstandard.MedicalStandard;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Позиция мед. оборудования")
@Table(schema="SQLUser")
@Getter
@Setter

public class MedicalEquipmentPosition extends BaseEntity {

	/** Медицинский стандарт */
	@Comment("Медицинский стандарт")
	@ManyToOne
	public MedicalStandard getStandard() {return standard;}
	/** Медицинский стандарт */
	private MedicalStandard standard;
	
	/** Тип оборудования */
	@Comment("Тип Оборудования")
	@OneToOne
	public VocTypeEquip getEquipmentType() {return equipmentType;}
	/** Оборудование */
	private VocTypeEquip equipmentType;
	
	/** Необходимое количество */
	private Long amount;
	
	/** Примечание */
	private String comment;
}
