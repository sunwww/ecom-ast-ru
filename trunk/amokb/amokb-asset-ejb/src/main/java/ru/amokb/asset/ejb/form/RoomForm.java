package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.Room;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Room.class)
@Comment("Помещение")
@WebTrail(comment = "Помещение", nameProperties= "id", list="entityParentList-asset_room.do", view="entityParentView-asset_room.do",shortView="entityShortView-asset_room.do")
@Parent(property="building", parentForm=BuildingForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/Territory/Building/Room")
public class RoomForm extends PermanentAssetForm{
	/**
	 * Здание
	 */
	@Comment("Здание")
	@Persist
	public Long getBuilding() {
		return theBuilding;
	}
	public void setBuilding(Long aBuilding) {
		theBuilding = aBuilding;
	}
	/**
	 * Здание
	 */
	private Long theBuilding;
	/**
	 * Местный телефон
	 */
	@Comment("Местный телефон")
	@Persist
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
	@Persist
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
	@Persist
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
	@Persist
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
