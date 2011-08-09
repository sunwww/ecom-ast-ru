package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.amokb.asset.ejb.domain.voc.VocCpuSocket;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Процессор
	 */
	@Comment("Процессор")
@Entity
@Table(schema="SQLUser")
public class CPU extends Component{
	/**
	 * Количество ядер
	 */
	@Comment("Количество ядер")
	
	public int getCoreAmount() {
		return theCoreAmount;
	}
	public void setCoreAmount(int aCoreAmount) {
		theCoreAmount = aCoreAmount;
	}
	/**
	 * Количество ядер
	 */
	private int theCoreAmount;
	/**
	 * Частота в МГц
	 */
	@Comment("Частота в МГц")
	
	public int getRate() {
		return theRate;
	}
	public void setRate(int aRate) {
		theRate = aRate;
	}
	/**
	 * Частота в МГц
	 */
	private int theRate;
	/**
	 * Разъем
	 */
	@Comment("Разъем")
	
	public String getSocket() {
		return theSocket;
	}
	public void setSocket(String aSocket) {
		theSocket = aSocket;
	}
	/**
	 * Разъем
	 */
	private String theSocket;
	/**
	 * Тип разъема
	 */
	@Comment("Тип разъема")
	@OneToOne
	public VocCpuSocket getSocketType() {
		return theSocketType;
	}
	public void setSocketType(VocCpuSocket aSocketType) {
		theSocketType = aSocketType;
	}
	/**
	 * Тип разъема
	 */
	private VocCpuSocket theSocketType;
}
