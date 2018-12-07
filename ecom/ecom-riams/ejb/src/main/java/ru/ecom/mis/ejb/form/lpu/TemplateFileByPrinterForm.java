package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.TemplateFileByPrinter;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = TemplateFileByPrinter.class)
@Comment("Копировальное оборудование")
@WebTrail(comment = "Копировальное оборудование", nameProperties= "id", view="entityParentView-mis_copyingEquipmentMaskFiles.do")
@Parent(property="parent", parentForm=CopyingEquipmentForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment")
public class TemplateFileByPrinterForm extends CopyingEquipmentForm {
	/** Маска файла */
	@Comment("Маска файла")
	@Persist @Required
	public String getMaskFiles() {return theMaskFiles;}
	public void setMaskFiles(String aMaskFiles) {theMaskFiles = aMaskFiles;}

	/** Маска файла */
	private String theMaskFiles;
}
