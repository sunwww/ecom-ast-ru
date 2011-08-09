package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * 
	 */
	@Comment("")
@Entity
@Table(schema="SQLUser")
public class Cartridge extends PermanentAsset{
	/**
	 * Оборудование
	 */
	@Comment("Оборудование")
	@OneToOne
	public CopyingEquipment getEquipment() {
		return theEquipment;
	}
	public void setEquipment(CopyingEquipment aEquipment) {
		theEquipment = aEquipment;
	}
	/**
	 * Оборудование
	 */
	private CopyingEquipment theEquipment;
	/**
	 * Количество заправок
	 */
	@Comment("Количество заправок")
	
	public int getRefuellingNumber() {
		return theRefuellingNumber;
	}
	public void setRefuellingNumber(int aRefuellingNumber) {
		theRefuellingNumber = aRefuellingNumber;
	}
	/**
	 * Количество заправок
	 */
	private int theRefuellingNumber;
}
