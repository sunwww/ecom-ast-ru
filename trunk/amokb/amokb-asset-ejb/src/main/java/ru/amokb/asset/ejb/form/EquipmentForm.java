package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.Equipment;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Equipment.class)
@Comment("Оборудование")
@WebTrail(comment = "Оборудование", nameProperties= "id", list="entityParentList-asset_equipment.do", view="entityParentView-asset_equipment.do")
@Parent(property="automatedWorkplace", parentForm=AutomatedWorkplaceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment")
@Subclasses(value = { NetworkEquipmentForm.class, ComputerForm.class 
		,ServerForm.class,TerminalForm.class,PersonalComputerForm.class
		,CopyingEquipmentForm.class
})
public class EquipmentForm extends PermanentAssetForm{
	/**
	 * Модель
	 */
	@Comment("Модель")
	@Persist
	public String getModel() {
		return theModel;
	}
	public void setModel(String aModel) {
		theModel = aModel;
	}
	/**
	 * Модель
	 */
	private String theModel;
	/**
	 * Серийный номер
	 */
	@Comment("Серийный номер")
	@Persist
	public String getSerialNumber() {
		return theSerialNumber;
	}
	public void setSerialNumber(String aSerialNumber) {
		theSerialNumber = aSerialNumber;
	}
	/**
	 * Серийный номер
	 */
	private String theSerialNumber;
	/**
	 * Учетный номер
	 */
	@Comment("Учетный номер")
	@Persist
	public String getAccountNumber() {
		return theAccountNumber;
	}
	public void setAccountNumber(String aAccountNumber) {
		theAccountNumber = aAccountNumber;
	}
	/**
	 * Учетный номер
	 */
	private String theAccountNumber;
	/**
	 * Автоматизированное рабочее место
	 */
	@Comment("Автоматизированное рабочее место")
	@Persist
	public Long getAutomatedWorkplace() {
		return theAutomatedWorkplace;
	}
	public void setAutomatedWorkplace(Long aAutomatedWorkplace) {
		theAutomatedWorkplace = aAutomatedWorkplace;
	}
	/**
	 * Автоматизированное рабочее место
	 */
	private Long theAutomatedWorkplace;
}
