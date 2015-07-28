package ru.ecom.mis.ejb.domain.medstandard;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.equipment.MedicalEquipmentPosition;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

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
	
}
