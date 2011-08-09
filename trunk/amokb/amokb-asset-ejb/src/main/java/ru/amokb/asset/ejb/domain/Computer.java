package ru.amokb.asset.ejb.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * 
	 */
	@Comment("")
@Entity
@Table(schema="SQLUser")
public class Computer extends Equipment{
	@OneToMany(mappedBy="computer", cascade=CascadeType.ALL)
	public List<ComputerComponent> getComponents() {
		return theComponents;
	}
	public void setComponents(List<ComputerComponent> aComponents) {
		theComponents = aComponents;
	}
	/**
	 * Компоненты
	 */
	private List<ComputerComponent> theComponents;
	/**
	 * Доступ в Интернет
	 */
	@Comment("Доступ в Интернет")
	
	public Boolean getInternetAccess() {
		return theInternetAccess;
	}
	public void setInternetAccess(Boolean aInternetAccess) {
		theInternetAccess = aInternetAccess;
	}
	/**
	 * Доступ в Интернет
	 */
	private Boolean theInternetAccess;
	/**
	 * Доступ к USB
	 */
	@Comment("Доступ к USB")
	
	public Boolean getUsbAccess() {
		return theUsbAccess;
	}
	public void setUsbAccess(Boolean aUsbAccess) {
		theUsbAccess = aUsbAccess;
	}
	/**
	 * Доступ к USB
	 */
	private Boolean theUsbAccess;
	/**
	 * Пароль на BIOS
	 */
	@Comment("Пароль на BIOS")
	
	public Boolean getBiosPassword() {
		return theBiosPassword;
	}
	public void setBiosPassword(Boolean aBiosPassword) {
		theBiosPassword = aBiosPassword;
	}
	/**
	 * Пароль на BIOS
	 */
	private Boolean theBiosPassword;
	/**
	 * Сетевое имя
	 */
	@Comment("Сетевое имя")
	
	public String getNetworkName() {
		return theNetworkName;
	}
	public void setNetworkName(String aNetworkName) {
		theNetworkName = aNetworkName;
	}
	/**
	 * Сетевое имя
	 */
	private String theNetworkName;
	/**
	 * 
	 */
	@Comment("")
	@OneToMany(mappedBy="computer", cascade=CascadeType.ALL)
	public List<ComputerProgram> getPrograms() {
		return thePrograms;
	}
	public void setPrograms(List<ComputerProgram> aPrograms) {
		thePrograms = aPrograms;
	}
	/**
	 * 
	 */
	private List<ComputerProgram> thePrograms;
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
	 * Опечатывание
	 */
	@Comment("Опечатывание")
	
	public Boolean getSealing() {
		return theSealing;
	}
	public void setSealing(Boolean aSealing) {
		theSealing = aSealing;
	}
	/**
	 * Опечатывание
	 */
	private Boolean theSealing;
}
