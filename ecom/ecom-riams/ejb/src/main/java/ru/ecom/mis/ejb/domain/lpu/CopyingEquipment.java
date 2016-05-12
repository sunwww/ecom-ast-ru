package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
@Entity
@Table(schema="SQLUser")
@AIndexes({
    @AIndex(properties="parent")
    }) 
public class CopyingEquipment extends BaseEntity {
	/** IP адрес */
	@Comment("IP адрес")
	public String getIpaddress() {return theIpaddress;}
	public void setIpaddress(String aIpaddress) {theIpaddress = aIpaddress;}
	
	/** Модель */
	@Comment("Модель")
	public String getModel() {return theModel;}
	public void setModel(String aModel) {theModel = aModel;}
	
	/** Серийный номер */
	@Comment("Серийный номер")
	public String getSerialNumber() {return theSerialNumber;}
	public void setSerialNumber(String aSerialNumber) {theSerialNumber = aSerialNumber;}
	
	/** Учетный номер */
	@Comment("Учетный номер")
	public String getAccountNumber() {return theAccountNumber;}
	public void setAccountNumber(String aAccountNumber) {theAccountNumber = aAccountNumber;}
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}
	/**
	 * Комментарии
	 */
	@Comment("Комментарии")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	/** Комментарии */
	private String theComment;
	/** Название */
	@Comment("Название")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	/** Название */
	private String theName;
	/** ЛПУ */
	private MisLpu theLpu;
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
	public Boolean getIsTxtFile() {return theIsTxtFile;}
	public void setIsTxtFile(Boolean aIsTxtFile) {theIsTxtFile = aIsTxtFile;}

	/** Печать в txt файл */
	private Boolean theIsTxtFile;
	
	/** Команда */
	@Comment("Команда")
	public String getCommandPrintTxt() {return theCommandPrintTxt;}
	public void setCommandPrintTxt(String aCommandPrintTxt) {theCommandPrintTxt = aCommandPrintTxt;}

	/** Команда */
	private String theCommandPrintTxt;
	
	/** Маска файла */
	@Comment("Маска файла")
	public String getMaskFiles() {return theMaskFiles;}
	public void setMaskFiles(String aMaskFiles) {theMaskFiles = aMaskFiles;}

	/** Маска файла */
	private String theMaskFiles;
	
	/** Основной принтер */
	@Comment("Основной принтер")
	@OneToOne
	public CopyingEquipment getParent() {return theParent;}
	public void setParent(CopyingEquipment aParent) {theParent = aParent;}

	/** Основной принтер */
	private CopyingEquipment theParent;
}
