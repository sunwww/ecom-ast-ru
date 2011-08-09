package ru.amokb.asset.ejb.domain;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.amokb.asset.ejb.domain.voc.VocSecurityMark;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * 
	 */
	@Comment("")
@Entity
@Table(schema="SQLUser")
public class PermanentAsset extends Asset{
	/**
	 * Дата производства
	 */
	@Comment("Дата производства")
	
	public Date getProductionDate() {
		return theProductionDate;
	}
	public void setProductionDate(Date aProductionDate) {
		theProductionDate = aProductionDate;
	}
	/**
	 * Дата производства
	 */
	private Date theProductionDate;
	/**
	 * Инвентарный номер
	 */
	@Comment("Инвентарный номер")
	
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
	@OneToOne
	public VocSecurityMark getSecurityMark() {
		return theSecurityMark;
	}
	public void setSecurityMark(VocSecurityMark aSecurityMark) {
		theSecurityMark = aSecurityMark;
	}
	/**
	 * Метка безопасности
	 */
	private VocSecurityMark theSecurityMark;
}
