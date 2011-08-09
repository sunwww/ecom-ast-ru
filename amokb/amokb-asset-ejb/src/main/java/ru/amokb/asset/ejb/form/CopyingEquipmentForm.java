package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.CopyingEquipment;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = CopyingEquipment.class)
@Comment("Копировальное оборудование")
@WebTrail(comment = "Копировальное оборудование", nameProperties= "id", list="entityParentList-asset_copyingEquipment.do", view="entityParentView-asset_copyingEquipment.do")
@Parent(property="automatedWorkplace", parentForm=AutomatedWorkplaceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment")
public class CopyingEquipmentForm extends EquipmentForm{
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
}
