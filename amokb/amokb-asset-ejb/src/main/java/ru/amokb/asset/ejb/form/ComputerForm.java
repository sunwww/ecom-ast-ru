package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.Computer;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Computer.class)
@Comment("Компьютер")
@WebTrail(comment = "Компьютер", nameProperties= "id", list="entityParentList-asset_computer.do", view="entityParentView-asset_computer.do")
@Parent(property="automatedWorkplace", parentForm=AutomatedWorkplaceForm.class)
@Subclasses(value = { ServerForm.class, TerminalForm.class,PersonalComputerForm.class })
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/Computer")
public class ComputerForm extends EquipmentForm{
	/**
	 * Доступ в Интернет
	 */
	@Comment("Доступ в Интернет")
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
	 * IP адрес
	 */
	@Comment("IP адрес")
	@Persist
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
	@Persist
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
