package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.NetworkEquipment;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.validators.IntegerString;

@EntityForm
@EntityFormPersistance(clazz = NetworkEquipment.class)
@Comment("Сетевое оборудование")
@WebTrail(comment = "Сетевое оборудование", nameProperties= "id", list="entityParentList-asset_networkEquipment.do", view="entityParentView-asset_networkEquipment.do")
@Parent(property="automatedWorkplace", parentForm=AutomatedWorkplaceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/NetworkEquipment")
public class NetworkEquipmentForm extends EquipmentForm{
	/**
	 * Активное оборудование
	 */
	@Comment("Активное оборудование")
	@Persist
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
	 * Количество портов
	 */
	@Comment("Количество портов")
	@Persist
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
	@Persist
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
