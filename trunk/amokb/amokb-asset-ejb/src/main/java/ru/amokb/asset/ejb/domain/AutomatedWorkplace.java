package ru.amokb.asset.ejb.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Автоматизированное рабочее место
	 */
	@Comment("Автоматизированное рабочее место")
@Entity
@Table(schema="SQLUser")
public class AutomatedWorkplace extends PermanentAsset{
	/**
	 * Вычислительная сеть
	 */
	@Comment("Вычислительная сеть")
	@ManyToOne
	public Network getNetwork() {
		return theNetwork;
	}
	public void setNetwork(Network aNetwork) {
		theNetwork = aNetwork;
	}
	/**
	 * Вычислительная сеть
	 */
	private Network theNetwork;
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
	@OneToMany(mappedBy="automatedWorkplace")
	public List<Equipment> getEquipments() {
		return theEquipments;
	}
	public void setEquipments(List<Equipment> aEquipments) {
		theEquipments = aEquipments;
	}
	/**
	 * Оборудование
	 */
	private List<Equipment> theEquipments;
	/**
	 * Ответственный пользователь
	 */
	@Comment("Ответственный пользователь")
	@OneToOne
	public Worker getResponsibleUser() {
		return theResponsibleUser;
	}
	public void setResponsibleUser(Worker aResponsibleUser) {
		theResponsibleUser = aResponsibleUser;
	}
	/**
	 * Ответственный пользователь
	 */
	private Worker theResponsibleUser;
	/** Территория */
	@Comment("Территория")
	@OneToOne
	public Territory getTerritory() {
		return theTerritory;
	}

	public void setTerritory(Territory aTerritory) {
		theTerritory = aTerritory;
	}

	/** Территория */
	private Territory theTerritory;
	
	/** Здание */
	@Comment("Здание")
	@OneToOne
	public Building getBuilding() {
		return theBuilding;
	}

	public void setBuilding(Building aBuilding) {
		theBuilding = aBuilding;
	}

	/** Здание */
	private Building theBuilding;
}
