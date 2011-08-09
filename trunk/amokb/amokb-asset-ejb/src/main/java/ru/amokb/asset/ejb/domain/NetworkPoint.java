package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Сетевая точка
	 */
	@Comment("Сетевая точка")
@Entity
@Table(schema="SQLUser")
public class NetworkPoint extends BaseEntity{
	/**
	 * Номер сетевой точки
	 */
	@Comment("Номер сетевой точки")
	
	public String getPointNumber() {
		return thePointNumber;
	}
	public void setPointNumber(String aPointNumber) {
		thePointNumber = aPointNumber;
	}
	/**
	 * Номер сетевой точки
	 */
	private String thePointNumber;
	/**
	 * Помещение
	 */
	@Comment("Помещение")
	@OneToOne
	public Room getRoom() {
		return theRoom;
	}
	public void setRoom(Room aRoom) {
		theRoom = aRoom;
	}
	/**
	 * Помещение
	 */
	private Room theRoom;
}
