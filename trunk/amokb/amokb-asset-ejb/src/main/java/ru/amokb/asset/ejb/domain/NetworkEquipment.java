package ru.amokb.asset.ejb.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Сетевое оборудование
	 */
	@Comment("Сетевое оборудование")
@Entity
@Table(schema="SQLUser")
public class NetworkEquipment extends Equipment{
	@OneToMany(mappedBy="equipment", cascade=CascadeType.ALL)
	public List<NetworkPort> getPorts() {
		return thePorts;
	}
	public void setPorts(List<NetworkPort> aPorts) {
		thePorts = aPorts;
	}
	/**
	 * Порты
	 */
	private List<NetworkPort> thePorts;
	/**
	 * Активное оборудование
	 */
	@Comment("Активное оборудование")
	
	public Boolean getActive() {
		return theActive;
	}
	public void setActive(Boolean aActive) {
		theActive = aActive;
	}
	/**
	 * Активное оборудование
	 */
	private Boolean theActive;
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
	/**
	 * Количество портов
	 */
	@Comment("Количество портов")
	
	public int getPortNumber() {
		return thePortNumber;
	}
	public void setPortNumber(int aPortNumber) {
		thePortNumber = aPortNumber;
	}
	/**
	 * Количество портов
	 */
	private int thePortNumber;
	/**
	 * Пароль локального администратора
	 */
	@Comment("Пароль локального администратора")
	
	public Boolean getLocalAdminPassword() {
		return theLocalAdminPassword;
	}
	public void setLocalAdminPassword(Boolean aLocalAdminPassword) {
		theLocalAdminPassword = aLocalAdminPassword;
	}
	/**
	 * Пароль локального администратора
	 */
	private Boolean theLocalAdminPassword;
}
