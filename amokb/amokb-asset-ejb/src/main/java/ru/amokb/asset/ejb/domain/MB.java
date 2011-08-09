package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.amokb.asset.ejb.domain.voc.VocCpuSocket;
import ru.amokb.asset.ejb.domain.voc.VocRAM;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Материнская плата
	 */
	@Comment("Материнская плата")
@Entity
@Table(schema="SQLUser")
public class MB extends Component{
	/**
	 * Тип разъема процессора
	 */
	@Comment("Тип разъема процессора")
	@OneToOne
	public VocCpuSocket getCpuSocketType() {
		return theCpuSocketType;
	}
	public void setCpuSocketType(VocCpuSocket aCpuSocketType) {
		theCpuSocketType = aCpuSocketType;
	}
	/**
	 * Тип разъема процессора
	 */
	private VocCpuSocket theCpuSocketType;
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
	/**
	 * Количество слотов RAM
	 */
	@Comment("Количество слотов RAM")
	
	public int getRamSlots() {
		return theRamSlots;
	}
	public void setRamSlots(int aRamSlots) {
		theRamSlots = aRamSlots;
	}
	/**
	 * Количество слотов RAM
	 */
	private int theRamSlots;
	/**
	 * Тип RAM
	 */
	@Comment("Тип RAM")
	@OneToOne
	public VocRAM getRamType() {
		return theRamType;
	}
	public void setRamType(VocRAM aRamType) {
		theRamType = aRamType;
	}
	/**
	 * Тип RAM
	 */
	private VocRAM theRamType;
}
