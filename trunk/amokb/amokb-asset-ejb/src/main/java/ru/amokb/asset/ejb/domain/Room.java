package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Помещение
	 */
	@Comment("Помещение")
@Entity
@Table(schema="SQLUser")
public class Room extends PermanentAsset{
	/**
	 * Здание
	 */
	@Comment("Здание")
	@ManyToOne
	public Building getBuilding() {
		return theBuilding;
	}
	public void setBuilding(Building aBuilding) {
		theBuilding = aBuilding;
	}
	/**
	 * Здание
	 */
	private Building theBuilding;
	/**
	 * Местный телефон
	 */
	@Comment("Местный телефон")
	
	public String getLocalPhone() {
		return theLocalPhone;
	}
	public void setLocalPhone(String aLocalPhone) {
		theLocalPhone = aLocalPhone;
	}
	/**
	 * Местный телефон
	 */
	private String theLocalPhone;
	/**
	 * Городской телефон
	 */
	@Comment("Городской телефон")
	
	public String getCityPhone() {
		return theCityPhone;
	}
	public void setCityPhone(String aCityPhone) {
		theCityPhone = aCityPhone;
	}
	/**
	 * Городской телефон
	 */
	private String theCityPhone;
	/**
	 * Этаж
	 */
	@Comment("Этаж")
	
	public String getFloor() {
		return theFloor;
	}
	public void setFloor(String aFloor) {
		theFloor = aFloor;
	}
	/**
	 * Этаж
	 */
	private String theFloor;
	/**
	 * Номер помещения
	 */
	@Comment("Номер помещения")
	
	public String getRoomNumber() {
		return theRoomNumber;
	}
	public void setRoomNumber(String aRoomNumber) {
		theRoomNumber = aRoomNumber;
	}
	/**
	 * Номер помещения
	 */
	private String theRoomNumber;
}
