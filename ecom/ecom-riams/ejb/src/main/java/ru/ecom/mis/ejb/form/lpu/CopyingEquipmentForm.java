package ru.ecom.mis.ejb.form.lpu;

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
//@Parent(property="lpu", parentForm=MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment")
public class CopyingEquipmentForm extends IdEntityForm {
	/** IP адрес */
	@Comment("IP адрес")
	@Persist
	public String getIpaddress() {return theIpaddress;}
	public void setIpaddress(String aIpaddress) {theIpaddress = aIpaddress;}
	
	/** Модель */
	@Comment("Модель")
	@Persist
	public String getModel() {return theModel;}
	public void setModel(String aModel) {theModel = aModel;}
	
	/** Серийный номер */
	@Comment("Серийный номер")
	@Persist
	public String getSerialNumber() {return theSerialNumber;}
	public void setSerialNumber(String aSerialNumber) {theSerialNumber = aSerialNumber;}
	
	/** Учетный номер */
	@Comment("Учетный номер")
	@Persist
	public String getAccountNumber() {return theAccountNumber;}
	public void setAccountNumber(String aAccountNumber) {theAccountNumber = aAccountNumber;}
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}
	/**
	 * Комментарии
	 */
	@Comment("Комментарии")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	/** Комментарии */
	private String theComment;
	/** Название */
	@Comment("Название")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	/** Название */
	private String theName;
	/** ЛПУ */
	private Long theLpu;
	/** Модель */
	private String theModel;
	/** Серийный номер */
	private String theSerialNumber;
	/** Учетный номер */
	private String theAccountNumber;
	/** IP адрес */
	private String theIpaddress;
	/** Печать в txt файл */
	@Comment("Печать в txt файл")
	@Persist
	public Boolean getIsTxtFile() {return theIsTxtFile;}
	public void setIsTxtFile(Boolean aIsTxtFile) {theIsTxtFile = aIsTxtFile;}

	/** Печать в txt файл */
	private Boolean theIsTxtFile;
	/** Команда */
	@Comment("Команда")
	@Persist
	public String getCommandPrintTxt() {return theCommandPrintTxt;}
	public void setCommandPrintTxt(String aCommandPrintTxt) {theCommandPrintTxt = aCommandPrintTxt;}

	/** Команда */
	private String theCommandPrintTxt;
	
	/** Маска файла */
	@Comment("Маска файла")
	@Persist
	public String getMaskFiles() {return theMaskFiles;}
	public void setMaskFiles(String aMaskFiles) {theMaskFiles = aMaskFiles;}

	/** Маска файла */
	private String theMaskFiles;
	/** Родитель */
	@Comment("Родитель")
	@Persist
	public Long getParent() {return theParent;}
	public void setParent(Long aParent) {theParent = aParent;}

	/** Родитель */
	private Long theParent;


}
