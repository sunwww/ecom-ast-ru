package ru.amokb.asset.ejb.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Здание
	 */
	@Comment("Здание")
@Entity
@Table(schema="SQLUser")
public class Building extends PermanentAsset{
	/**
	 * Территория
	 */
	@Comment("Территория")
	@ManyToOne
	public Territory getTerritory() {
		return theTerritory;
	}
	public void setTerritory(Territory aTerritory) {
		theTerritory = aTerritory;
	}
	/**
	 * Территория
	 */
	private Territory theTerritory;
	@OneToMany(mappedBy="building", cascade=CascadeType.ALL)
	public List<Room> getRooms() {
		return theRooms;
	}
	public void setRooms(List<Room> aRooms) {
		theRooms = aRooms;
	}
	/**
	 * Комнаты
	 */
	private List<Room> theRooms;
	/**
	 * Количество этажей
	 */
	@Comment("Количество этажей")
	
	public int getFloors() {
		return theFloors;
	}
	public void setFloors(int aFloors) {
		theFloors = aFloors;
	}
	/**
	 * Количество этажей
	 */
	private int theFloors;
}
