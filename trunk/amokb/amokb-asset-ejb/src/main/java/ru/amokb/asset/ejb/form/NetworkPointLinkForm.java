package ru.amokb.asset.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.amokb.asset.ejb.domain.NetworkPointLink;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = NetworkPointLink.class)
@Comment("Соединение с сетевой точкой")
@WebTrail(comment = "Соединение с сетевой точкой", nameProperties= "id", list="entityParentList-asset_networkPointLink.do", view="entityParentView-asset_networkPointLink.do")
@Parent(property="equipment", parentForm=EquipmentForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/NetworkPointLink")
public class NetworkPointLinkForm extends IdEntityForm{
	/**
	 * Сетевая точка
	 */
	@Comment("Сетевая точка")
	@Persist
	public Long getNetworkPoint() {
		return theNetworkPoint;
	}
	public void setNetworkPoint(Long aNetworkPoint) {
		theNetworkPoint = aNetworkPoint;
	}
	/**
	 * Сетевая точка
	 */
	private Long theNetworkPoint;
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
	 * Длина соединения в метрах
	 */
	@Comment("Длина соединения в метрах")
	@Persist
	public String getLinkLength() {
		return theLinkLength;
	}
	public void setLinkLength(String aLinkLength) {
		theLinkLength = aLinkLength;
	}
	/**
	 * Длина соединения в метрах
	 */
	private String theLinkLength;
	/**
	 * Фабричное
	 */
	@Comment("Фабричное")
	@Persist
	public Boolean getFactory() {
		return theFactory;
	}
	public void setFactory(Boolean aFactory) {
		theFactory = aFactory;
	}
	/**
	 * Фабричное
	 */
	private Boolean theFactory;
}
