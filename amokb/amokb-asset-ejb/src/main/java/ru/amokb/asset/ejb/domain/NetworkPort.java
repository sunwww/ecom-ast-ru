package ru.amokb.asset.ejb.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Сетевой порт
	 */
	@Comment("Сетевой порт")
@Entity
@Table(schema="SQLUser")
public class NetworkPort extends BaseEntity{
	/**
	 * Оборудование
	 */
	@Comment("Оборудование")
	@ManyToOne
	public NetworkEquipment getEquipment() {
		return theEquipment;
	}
	public void setEquipment(NetworkEquipment aEquipment) {
		theEquipment = aEquipment;
	}
	/**
	 * Оборудование
	 */
	private NetworkEquipment theEquipment;
	/**
	 * Номер сетевого порта
	 */
	@Comment("Номер сетевого порта")
	
	public String getPortNumber() {
		return thePortNumber;
	}
	public void setPortNumber(String aPortNumber) {
		thePortNumber = aPortNumber;
	}
	/**
	 * Номер сетевого порта
	 */
	private String thePortNumber;
	/**
	 * Номер VLAN
	 */
	@Comment("Номер VLAN")
	
	public int getVlanNumber() {
		return theVlanNumber;
	}
	public void setVlanNumber(int aVlanNumber) {
		theVlanNumber = aVlanNumber;
	}
	/**
	 * Номер VLAN
	 */
	private int theVlanNumber;
	/**
	 * Номер транка
	 */
	@Comment("Номер транка")
	
	public int getTrankNumber() {
		return theTrankNumber;
	}
	public void setTrankNumber(int aTrankNumber) {
		theTrankNumber = aTrankNumber;
	}
	/**
	 * Номер транка
	 */
	private int theTrankNumber;
	/**
	 * MAC адрес
	 */
	@Comment("MAC адрес")
	
	public String getMacAddress() {
		return theMacAddress;
	}
	public void setMacAddress(String aMacAddress) {
		theMacAddress = aMacAddress;
	}
	/**
	 * MAC адрес
	 */
	private String theMacAddress;
	/**
	 * Скорость передачи в Мб\с
	 */
	@Comment("Скорость передачи в Мб/с")
	
	public BigDecimal getSpeed() {
		return theSpeed;
	}
	public void setSpeed(BigDecimal aSpeed) {
		theSpeed = aSpeed;
	}
	/**
	 * Скорость передачи в Мб\с
	 */
	private BigDecimal theSpeed;
	/**
	 * Оптический
	 */
	@Comment("Оптический")
	
	public Boolean getOptical() {
		return theOptical;
	}
	public void setOptical(Boolean aOptical) {
		theOptical = aOptical;
	}
	/**
	 * Оптический
	 */
	private Boolean theOptical;
}
