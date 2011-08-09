package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.amokb.asset.ejb.domain.voc.VocRAM;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Оперативная память
	 */
	@Comment("Оперативная память")
@Entity
@Table(schema="SQLUser")
public class RAM extends Component{
	/**
	 * Тип оперативной памяти
	 */
	@Comment("Тип оперативной памяти")
	@OneToOne
	public VocRAM getRamType() {
		return theRamType;
	}
	public void setRamType(VocRAM aRamType) {
		theRamType = aRamType;
	}
	/**
	 * Тип оперативной памяти
	 */
	private VocRAM theRamType;
	/**
	 * Емкость в МБ
	 */
	@Comment("Емкость в МБ")
	
	public int getStorage() {
		return theStorage;
	}
	public void setStorage(int aStorage) {
		theStorage = aStorage;
	}
	/**
	 * Емкость в МБ
	 */
	private int theStorage;
	/**
	 * Частота шины в МГц
	 */
	@Comment("Частота шины в МГц")
	
	public int getBusRate() {
		return theBusRate;
	}
	public void setBusRate(int aBusRate) {
		theBusRate = aBusRate;
	}
	/**
	 * Частота шины в МГц
	 */
	private int theBusRate;
}
