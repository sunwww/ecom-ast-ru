package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Программа
	 */
	@Comment("Программа")
@Entity
@Table(schema="SQLUser")
public class Program extends PermanentAsset{
	/**
	 * Версия
	 */
	@Comment("Версия")
	
	public String getVersion() {
		return theVersion;
	}
	public void setVersion(String aVersion) {
		theVersion = aVersion;
	}
	/**
	 * Версия
	 */
	private String theVersion;
	/**
	 * Количество лицензий
	 */
	@Comment("Количество лицензий")
	
	public int getLicenseAmount() {
		return theLicenseAmount;
	}
	public void setLicenseAmount(int aLicenseAmount) {
		theLicenseAmount = aLicenseAmount;
	}
	/**
	 * Количество лицензий
	 */
	private int theLicenseAmount;
}
