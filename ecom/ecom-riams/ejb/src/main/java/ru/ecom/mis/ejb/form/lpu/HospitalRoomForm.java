package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
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
@Setter
public class HospitalRoomForm extends WorkPlaceForm {
	/**
	 * Количество коек
	 */
	@Comment("Количество коек")
	@Persist
	public Long getBedCapacity() {
		return bedCapacity;
	}

	/**
	 * Тип коек
	 */
	@Comment("Тип коек")
	@Persist
	public Long getRoomType() {
		return roomType;
	}

	/**
	 * Лечебное учреждение
	 */
	@Comment("Лечебное учреждение")
	@Persist
	@Required
	public Long getLpu() {
		return lpu;
	}

	/**
	 * Лечебное учреждение
	 */
	private Long lpu;

	/**
	 * Пол
	 */
	@Comment("Пол")
	@Persist
	public Long getSex() {
		return sex;
	}

	/**
	 * Пол
	 */
	private Long sex;
	/**
	 * Тип коек
	 */
	private Long roomType;
	/**
	 * Количество коек
	 */
	private Long bedCapacity;

	/**
	 * Кол-во коек в палате
	 */
	@Comment("Кол-во коек в палате")
	@Persist
	public Long getCountBed() {
		return countBed;
	}

	/**
	 * Кол-во коек в палате
	 */
	private Long countBed;

	/** Признак палаты по умолчанию (для новорожденных) */
	@Comment("Признак палаты по умолчанию (для новорожденных)")
	@Persist
	public Boolean getDefaultRoom() { return defaultRoom; }
	/** Признак палаты по умолчанию (для новорожденных) */
	private Boolean defaultRoom;
}