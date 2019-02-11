package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.HospitalRoom;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@Comment("Больничная палата")
@EntityForm
@EntityFormPersistance(clazz = HospitalRoom.class)
@WebTrail(comment = "Больничная палата", nameProperties = "name", view = "entityView-mis_hospitalRoom.do")
@Parent(property = "parent", parentForm = FloorBuildingForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/WorkPlace/HospitalRoom")
public class HospitalRoomForm extends WorkPlaceForm {
	/**
	 * Количество коек
	 */
	@Comment("Количество коек")
	@Persist
	public Long getBedCapacity() {
		return theBedCapacity;
	}

	public void setBedCapacity(Long aBedCapacity) {
		theBedCapacity = aBedCapacity;
	}

	/**
	 * Тип коек
	 */
	@Comment("Тип коек")
	@Persist
	public Long getRoomType() {
		return theRoomType;
	}

	public void setRoomType(Long aRoomType) {
		theRoomType = aRoomType;
	}

	/**
	 * Лечебное учреждение
	 */
	@Comment("Лечебное учреждение")
	@Persist
	@Required
	public Long getLpu() {
		return theLpu;
	}

	public void setLpu(Long aLpu) {
		theLpu = aLpu;
	}

	/**
	 * Лечебное учреждение
	 */
	private Long theLpu;

	/**
	 * Пол
	 */
	@Comment("Пол")
	@Persist
	public Long getSex() {
		return theSex;
	}

	public void setSex(Long aSex) {
		theSex = aSex;
	}

	/**
	 * Пол
	 */
	private Long theSex;
	/**
	 * Тип коек
	 */
	private Long theRoomType;
	/**
	 * Количество коек
	 */
	private Long theBedCapacity;

	/**
	 * Кол-во коек в палате
	 */
	@Comment("Кол-во коек в палате")
	@Persist
	public Long getCountBed() {
		return theCountBed;
	}

	public void setCountBed(Long aCountBed) {
		theCountBed = aCountBed;
	}

	/**
	 * Кол-во коек в палате
	 */
	private Long theCountBed;

	/** Признак палаты по умолчанию (для новорожденных) */
	@Comment("Признак палаты по умолчанию (для новорожденных)")
	@Persist
	public Boolean getDefaultRoom() { return theDefaultRoom; }
	public void setDefaultRoom(Boolean aDefaultRoom) {theDefaultRoom = aDefaultRoom;}

	/** Признак палаты по умолчанию (для новорожденных) */
	private Boolean theDefaultRoom;
}