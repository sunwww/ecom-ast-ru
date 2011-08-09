package ru.amokb.asset.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.amokb.asset.ejb.domain.NetworkPort;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.validators.IntegerString;

@EntityForm
@EntityFormPersistance(clazz = NetworkPort.class)
@Comment("Сетевой порт")
@WebTrail(comment = "Сетевой порт", nameProperties= "id", list="entityParentList-asset_networkPort.do", view="entityParentView-asset_networkPort.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class NetworkPortForm extends IdEntityForm{
	/**
	 * Оборудование
	 */
	@Comment("Оборудование")
	@Persist
	public Long getEquipment() {
		return theEquipment;
	}
	public void setEquipment(Long aEquipment) {
		theEquipment = aEquipment;
	}
	/**
	 * Оборудование
	 */
	private Long theEquipment;
	/**
	 * Номер сетевого порта
	 */
	@Comment("Номер сетевого порта")
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
	@Comment("Скорость передачи в Мб\\с")
	@Persist
	public String getSpeed() {
		return theSpeed;
	}
	public void setSpeed(String aSpeed) {
		theSpeed = aSpeed;
	}
	/**
	 * Скорость передачи в Мб\с
	 */
	private String theSpeed;
	/**
	 * Оптический
	 */
	@Comment("Оптический")
	@Persist
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
