package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Копировальное оборудование
	 */
	@Comment("Копировальное оборудование")
@Entity
@Table(schema="SQLUser")
public class CopyingEquipment extends Equipment{
	/**
	 * IP адрес
	 */
	@Comment("IP адрес")
	
	public String getIpaddress() {
		return theIpaddress;
	}
	public void setIpaddress(String aIpaddress) {
		theIpaddress = aIpaddress;
	}
	/**
	 * IP адрес
	 */
	private String theIpaddress;
}
