package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
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
@Setter
public class TemplateByCopyingEquipmentForm extends IdEntityForm{
	/** Название шаблона */
	@Comment("Название шаблона")
	@Persist @Required
	public String getNameTemplate() {return nameTemplate;}

	/** Новое название */
	@Comment("Новое название")
	@Persist
	public String getNewNameTemplate() {return newNameTemplate;}

	/** Не отображать документ */
	@Comment("Не отображать документ")
	@Persist
	public Boolean getIsNotViewDisplay() {return isNotViewDisplay;}

	/** Копировальное оборудование */
	@Comment("Копировальное оборудование")
	@Persist
	public Long getCopyingEquipment() {return copyingEquipment;}

	/** Печать в txt файл */
	@Comment("Печать в txt файл")
	@Persist
	public Boolean getIsTxtFile() {return isTxtFile;}

	/** Печать в txt файл */
	private Boolean isTxtFile;
	/** Копировальное оборудование */
	private Long copyingEquipment;
	/** Не отображать документ */
	private Boolean isNotViewDisplay;
	/** Новое название */
	private String newNameTemplate;
	/** Название шаблона */
	private String nameTemplate;
}
