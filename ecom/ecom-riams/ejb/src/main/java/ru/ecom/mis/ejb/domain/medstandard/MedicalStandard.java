package ru.ecom.mis.ejb.domain.medstandard;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.equipment.MedicalEquipmentPosition;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Comment("Стандарт оказания мед. помощи")
@Table(schema="SQLUser")
public class MedicalStandard extends VocBaseEntity {
	/** Необходимое оборудование */
	@Comment("Необходимое оборудование")
	@OneToMany(mappedBy="standard", cascade=CascadeType.ALL)
	public List<MedicalEquipmentPosition> getEquipments() {return theEquipments;}
	public void setEquipments(List<MedicalEquipmentPosition> aEquipments) {theEquipments = aEquipments;}
	/** Необходимое оборудование */
	private List<MedicalEquipmentPosition> theEquipments;
	
	/** Дата начала действия */
	@Comment("Дата начала действия")
	public Date getStartDate() {return theStartDate;}
	public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
	/** Дата начала действия */
	private Date theStartDate;
	
	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	public Date getFinishDate() {return theFinishDate;}
	public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
	/** Дата окончания действия */
	private Date theFinishDate;
	
	/** Родительский стандарт */
	@Comment("Родительский стандарт")
	@OneToOne
	public MedicalStandard getParent() {return theParent;}
	public void setParent(MedicalStandard aParent) {theParent = aParent;}
	/** Родительский стандарт */
	private MedicalStandard theParent;
	
}
