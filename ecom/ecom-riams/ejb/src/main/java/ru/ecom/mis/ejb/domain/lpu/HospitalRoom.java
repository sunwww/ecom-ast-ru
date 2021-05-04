package ru.ecom.mis.ejb.domain.lpu;


import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.lpu.voc.VocCountBedInHospitalRoom;
import ru.ecom.mis.ejb.domain.medcase.voc.VocRoomType;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class HospitalRoom extends WorkPlace {

	/**
	 * Тип коек
	 */
	@Comment("Тип коек")
	@OneToOne
	public VocRoomType getRoomType() {
		return roomType;
	}

	/**
	 * Пол
	 */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {
		return sex;
	}

	/**
	 * Пол
	 */
	private VocSex sex;
	/**
	 * Тип коек
	 */
	private VocRoomType roomType;
	/**
	 * Количество коек
	 */
	private Long bedCapacity;

	/**
	 * Кол-во коек в палате
	 */
	@Comment("Кол-во коек в палате")
	@OneToOne
	public VocCountBedInHospitalRoom getCountBed() {
		return countBed;
	}

	/**
	 * Кол-во коек в палате
	 */
	private VocCountBedInHospitalRoom countBed;

	/** Признак палаты по умолчанию (для новорожденных) */
	private Boolean defaultRoom;
}