package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.TemplateByCopyingEquipment;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = TemplateByCopyingEquipment.class)
@Comment("Шаблон документа")
@WebTrail(comment = "Шаблон документа", nameProperties= "id", list="entityParentList-mis_templateByCopyingEquipment.do", view="entityParentView-mis_copyingEquipment.do")
@Parent(property="copyingEquipment", parentForm=CopyingEquipmentForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment/Template")
public class TemplateByCopyingEquipmentForm extends IdEntityForm{
	/** Название шаблона */
	@Comment("Название шаблона")
	@Persist @Required
	public String getNameTemplate() {return theNameTemplate;}
	public void setNameTemplate(String aNameTemplate) {theNameTemplate = aNameTemplate;}

	/** Новое название */
	@Comment("Новое название")
	@Persist
	public String getNewNameTemplate() {return theNewNameTemplate;}
	public void setNewNameTemplate(String aNewNameTemplate) {theNewNameTemplate = aNewNameTemplate;}

	/** Не отображать документ */
	@Comment("Не отображать документ")
	@Persist
	public Boolean getIsNotViewDisplay() {return theIsNotViewDisplay;}
	public void setIsNotViewDisplay(Boolean aNotViewDisplay) {theIsNotViewDisplay = aNotViewDisplay;}

	/** Копировальное оборудование */
	@Comment("Копировальное оборудование")
	@Persist
	public Long getCopyingEquipment() {return theCopyingEquipment;}
	public void setCopyingEquipment(Long aCopyingEquipment) {theCopyingEquipment = aCopyingEquipment;}

	/** Печать в txt файл */
	@Comment("Печать в txt файл")
	@Persist
	public Boolean getIsTxtFile() {return theIsTxtFile;}
	public void setIsTxtFile(Boolean aIsTxtFile) {theIsTxtFile = aIsTxtFile;}

	/** Печать в txt файл */
	private Boolean theIsTxtFile;
	/** Копировальное оборудование */
	private Long theCopyingEquipment;
	/** Не отображать документ */
	private Boolean theIsNotViewDisplay;
	/** Новое название */
	private String theNewNameTemplate;
	/** Название шаблона */
	private String theNameTemplate;
}
