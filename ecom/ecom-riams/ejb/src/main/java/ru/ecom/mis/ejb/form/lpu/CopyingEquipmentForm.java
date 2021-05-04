package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.CopyingEquipment;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = CopyingEquipment.class)
@Comment("Копировальное оборудование")
@WebTrail(comment = "Копировальное оборудование", nameProperties= "id", view="entityParentView-mis_copyingEquipment.do")
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment")
@Setter
public class CopyingEquipmentForm extends IdEntityForm {
	/** IP адрес */
	@Comment("IP адрес")
	@Persist
	public String getIpaddress() {return ipaddress;}

	/** Модель */
	@Comment("Модель")
	@Persist
	public String getModel() {return model;}

	/** Серийный номер */
	@Comment("Серийный номер")
	@Persist
	public String getSerialNumber() {return serialNumber;}

	/** Учетный номер */
	@Comment("Учетный номер")
	@Persist
	public String getAccountNumber() {return accountNumber;}

	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return lpu;}
	/**
	 * Комментарии
	 */
	@Comment("Комментарии")
	@Persist
	public String getComment() {return comment;}
	/** Комментарии */
	private String comment;
	/** Название */
	@Comment("Название")
	@Persist @Required
	public String getName() {return name;}
	/** Название */
	private String name;
	/** ЛПУ */
	private Long lpu;
	/** Модель */
	private String model;
	/** Серийный номер */
	private String serialNumber;
	/** Учетный номер */
	private String accountNumber;
	/** IP адрес */
	private String ipaddress;
	/** Печать в txt файл */
	@Comment("Печать в txt файл")
	@Persist
	public Boolean getIsTxtFile() {return isTxtFile;}

	/** Печать в txt файл */
	private Boolean isTxtFile;
	/** Команда */
	@Comment("Команда")
	@Persist
	public String getCommandPrintTxt() {return commandPrintTxt;}

	/** Команда */
	private String commandPrintTxt;
	
	/** Маска файла */
	@Comment("Маска файла")
	@Persist
	public String getMaskFiles() {return maskFiles;}

	/** Маска файла */
	private String maskFiles;
	/** Родитель */
	@Comment("Родитель")
	@Persist
	public Long getParent() {return parent;}

	/** Родитель */
	private Long parent;


}
