package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.ComputerCopyingEquipment;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ComputerCopyingEquipment.class)
@Comment("Копировальное оборудование компьютера (сетевое)")
@WebTrail(comment = "Копировальное оборудование компьютера (сетевое)", nameProperties= "id", list="entityParentList-asset_computerCopyingEquipment.do", view="entityParentView-asset_computerCopyingEquipment.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class ComputerCopyingEquipmentForm extends IdEntityForm{
	/**
	 * Копировальное оборудование
	 */
	@Comment("Копировальное оборудование")
	@Persist
	public Long getCopyingEquipment() {
		return theCopyingEquipment;
	}
	public void setCopyingEquipment(Long aCopyingEquipment) {
		theCopyingEquipment = aCopyingEquipment;
	}
	/**
	 * Копировальное оборудование
	 */
	private Long theCopyingEquipment;
	/**
	 * Компьютер
	 */
	@Comment("Компьютер")
	@Persist
	public Long getComputer() {
		return theComputer;
	}
	public void setComputer(Long aComputer) {
		theComputer = aComputer;
	}
	/**
	 * Компьютер
	 */
	private Long theComputer;
}
