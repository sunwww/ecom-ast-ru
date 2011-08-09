package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.PermanentAsset;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = PermanentAsset.class)
@Comment("")
@WebTrail(comment = "", nameProperties= "id", list="entityParentList-asset_permanentAsset.do", view="entityParentView-asset_permanentAsset.do")
@Parent(property="lpu", parentForm=MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset")
@Subclasses(value={NetworkEquipmentForm.class, ComputerForm.class 
		,ServerForm.class,TerminalForm.class,PersonalComputerForm.class
		,CopyingEquipmentForm.class
		,NetworkForm.class,AutomatedWorkplaceForm.class,RoomForm.class
		,BuildingForm.class,TerritoryForm.class,CartridgeForm.class,ProgramForm.class
		,ExternalDataMediumForm.class
		})
public class PermanentAssetForm extends AssetForm {
	/**
	 * Дата производства
	 */
	@Comment("Дата производства")
	@Persist
	@DateString @DoDateString
	public String getProductionDate() {
		return theProductionDate;
	}
	public void setProductionDate(String aProductionDate) {
		theProductionDate = aProductionDate;
	}
	/**
	 * Дата производства
	 */
	private String theProductionDate;
	/**
	 * Инвентарный номер
	 */
	@Comment("Инвентарный номер")
	@Persist
	public String getInventoryNumber() {
		return theInventoryNumber;
	}
	public void setInventoryNumber(String aInventoryNumber) {
		theInventoryNumber = aInventoryNumber;
	}
	/**
	 * Инвентарный номер
	 */
	private String theInventoryNumber;
	/**
	 * Метка безопасности
	 */
	@Comment("Метка безопасности")
	@Persist
	public Long getSecurityMark() {
		return theSecurityMark;
	}
	public void setSecurityMark(Long aSecurityMark) {
		theSecurityMark = aSecurityMark;
	}
	/**
	 * Метка безопасности
	 */
	private Long theSecurityMark;
}
