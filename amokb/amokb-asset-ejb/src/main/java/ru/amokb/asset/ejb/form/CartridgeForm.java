package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.Cartridge;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.validators.IntegerString;

@EntityForm
@EntityFormPersistance(clazz = Cartridge.class)
@Comment("Катридж")
@WebTrail(comment = "Катридж", nameProperties= "id", list="entityParentList-asset_cartridge.do", view="entityParentView-asset_cartridge.do")
@Parent(property="lpu", parentForm=MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/Cartridge")
public class CartridgeForm extends PermanentAssetForm{
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
	 * Количество заправок
	 */
	@Comment("Количество заправок")
	@Persist
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
