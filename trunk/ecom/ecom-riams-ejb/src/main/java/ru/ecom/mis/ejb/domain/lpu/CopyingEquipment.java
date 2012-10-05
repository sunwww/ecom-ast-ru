package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
@Entity
@Table(schema="SQLUser")
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
	public Boolean getIsTxtFile() {
		return theIsTxtFile;
	}

	public void setIsTxtFile(Boolean aIsTxtFile) {
		theIsTxtFile = aIsTxtFile;
	}

	/** Печать в txt файл */
	private Boolean theIsTxtFile;
}
