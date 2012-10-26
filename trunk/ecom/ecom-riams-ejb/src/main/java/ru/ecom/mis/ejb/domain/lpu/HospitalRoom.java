package ru.ecom.mis.ejb.domain.lpu;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.lpu.voc.VocCountBedInHospitalRoom;
import ru.ecom.mis.ejb.domain.medcase.voc.VocRoomType;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class HospitalRoom extends WorkPlace {
	/** Количество коек */
	@Comment("Количество коек")
	public Long getBedCapacity() {
		return theBedCapacity;
	}

	public void setBedCapacity(Long aBedCapacity) {
		theBedCapacity = aBedCapacity;
	}
	
	/** Тип коек */
	@Comment("Тип коек")
	@OneToOne
	public VocRoomType getRoomType() {
		return theRoomType;
	}

	public void setRoomType(VocRoomType aRoomType) {
		theRoomType = aRoomType;
	}

	/** Пол */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {return theSex;}
	public void setSex(VocSex aSex) {theSex = aSex;}

	/** Пол */
	private VocSex theSex;
	/** Тип коек */
	private VocRoomType theRoomType;
	/** Количество коек */
	private Long theBedCapacity;
	/** Кол-во коек в палате */
	@Comment("Кол-во коек в палате")
	@OneToOne
	public VocCountBedInHospitalRoom getCountBed() {return theCountBed;}
	public void setCountBed(VocCountBedInHospitalRoom aCountBed) {theCountBed = aCountBed;}

	/** Кол-во коек в палате */
	private VocCountBedInHospitalRoom theCountBed;
	
}
