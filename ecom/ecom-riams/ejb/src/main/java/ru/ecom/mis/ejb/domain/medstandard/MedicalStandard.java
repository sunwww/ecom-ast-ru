package ru.ecom.mis.ejb.domain.medstandard;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.equipment.MedicalEquipmentPosition;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Comment("Стандарт оказания мед. помощи")
@Table(schema="SQLUser")
@Getter
@Setter
public class MedicalStandard extends VocBaseEntity {
	/** Необходимое оборудование */
	@Comment("Необходимое оборудование")
	@OneToMany(mappedBy="standard", cascade=CascadeType.ALL)
	public List<MedicalEquipmentPosition> getEquipments() {return equipments;}
	private List<MedicalEquipmentPosition> equipments;
	
	/** Дата начала действия */
	private Date startDate;
	
	/** Дата окончания действия */
	private Date finishDate;
	
	/** Родительский стандарт */
	@Comment("Родительский стандарт")
	@OneToOne
	public MedicalStandard getParent() {return parent;}
	/** Родительский стандарт */
	private MedicalStandard parent;
	
}
