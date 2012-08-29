package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.medcase.voc.VocRoomType;
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

	/** Тип коек */
	private VocRoomType theRoomType;
	/** Количество коек */
	private Long theBedCapacity;
}
