package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Копировальное оборудование компьютера (сетевое)
	 */
	@Comment("Копировальное оборудование компьютера (сетевое)")
@Entity
@Table(schema="SQLUser")
public class ComputerCopyingEquipment extends BaseEntity{
	/**
	 * Копировальное оборудование
	 */
	@Comment("Копировальное оборудование")
	@OneToOne
	public CopyingEquipment getCopyingEquipment() {
		return theCopyingEquipment;
	}
	public void setCopyingEquipment(CopyingEquipment aCopyingEquipment) {
		theCopyingEquipment = aCopyingEquipment;
	}
	/**
	 * Копировальное оборудование
	 */
	private CopyingEquipment theCopyingEquipment;
	/**
	 * Компьютер
	 */
	@Comment("Компьютер")
	@OneToOne
	public Computer getComputer() {
		return theComputer;
	}
	public void setComputer(Computer aComputer) {
		theComputer = aComputer;
	}
	/**
	 * Компьютер
	 */
	private Computer theComputer;
}
