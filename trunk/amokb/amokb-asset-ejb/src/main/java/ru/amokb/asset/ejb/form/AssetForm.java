package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.Asset;
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
@EntityFormPersistance(clazz = Asset.class)
@Comment("Актив")
@WebTrail(comment = "Актив", nameProperties= "id", list="entityParentList-asset_asset.do", view="entityParentView-asset_asset.do")
@Parent(property="lpu", parentForm=MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset")
@Subclasses(value={NetworkEquipmentForm.class, ComputerForm.class 
		,ServerForm.class,TerminalForm.class,PersonalComputerForm.class
		,CopyingEquipmentForm.class
		,NetworkForm.class,AutomatedWorkplaceForm.class,RoomForm.class
		,BuildingForm.class,TerritoryForm.class,CartridgeForm.class,ProgramForm.class
		,ExternalDataMediumForm.class
		,TransitoryAssetForm.class
		,ComponentForm.class, ExpensesMaterialForm.class
		,CPUForm.class,MBForm.class,RAMForm.class,LaserDriveForm.class,
		NetworkCardForm.class,OtherComponentForm.class,FloppyDriveForm.class
		,VideoCardForm.class,HDDForm.class
		})
public class AssetForm extends IdEntityForm{
	/**
	 * ЛПУ
	 */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {
		return theLpu;
	}
	public void setLpu(Long aLpu) {
		theLpu = aLpu;
	}
	/**
	 * ЛПУ
	 */
	private Long theLpu;
	/**
	 * Производитель
	 */
	@Comment("Производитель")
	@Persist
	public Long getProducer() {
		return theProducer;
	}
	public void setProducer(Long aProducer) {
		theProducer = aProducer;
	}
	/**
	 * Производитель
	 */
	private Long theProducer;
	/**
	 * Дата списания
	 */
	@Comment("Дата списания")
	@Persist
	@DateString @DoDateString
	public String getDiscardingDate() {
		return theDiscardingDate;
	}
	public void setDiscardingDate(String aDiscardingDate) {
		theDiscardingDate = aDiscardingDate;
	}
	/**
	 * Дата списания
	 */
	private String theDiscardingDate;
	/**
	 * Дата поступления
	 */
	@Comment("Дата поступления")
	@Persist
	@DateString @DoDateString
	public String getEntranceDate() {
		return theEntranceDate;
	}
	public void setEntranceDate(String aEntranceDate) {
		theEntranceDate = aEntranceDate;
	}
	/**
	 * Дата поступления
	 */
	private String theEntranceDate;
	/**
	 * Ответственное лицо
	 */
	@Comment("Ответственное лицо")
	@Persist
	public Long getResponsiblePerson() {
		return theResponsiblePerson;
	}
	public void setResponsiblePerson(Long aResponsiblePerson) {
		theResponsiblePerson = aResponsiblePerson;
	}
	/**
	 * Ответственное лицо
	 */
	private Long theResponsiblePerson;
	/**
	 * Комментарии
	 */
	@Comment("Комментарии")
	@Persist
	public String getComment() {
		return theComment;
	}
	public void setComment(String aComment) {
		theComment = aComment;
	}
	/**
	 * Комментарии
	 */
	private String theComment;
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	/**
	 * Название
	 */
	private String theName;
}
