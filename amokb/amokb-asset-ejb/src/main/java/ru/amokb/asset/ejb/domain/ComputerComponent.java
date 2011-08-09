package ru.amokb.asset.ejb.domain;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Компонент компьютера
	 */
	@Comment("Компонент компьютера")
@Entity
@Table(schema="SQLUser")
public class ComputerComponent extends BaseEntity{
	/**
	 * Дата состояния
	 */
	@Comment("Дата состояния")
	
	public Date getStateDate() {
		return theStateDate;
	}
	public void setStateDate(Date aStateDate) {
		theStateDate = aStateDate;
	}
	/**
	 * Дата состояния
	 */
	private Date theStateDate;
	/**
	 * Компьютер
	 */
	@Comment("Компьютер")
	@ManyToOne
	public Computer getComputer() {
		return theComputer;
	}
	public void setComputer(Computer aComputer) {
		theComputer = aComputer;
	}
	/**
	 * Компьютер
	 */
	private Computer theComputer;
	/**
	 * Компонент
	 */
	@Comment("Компонент")
	@OneToOne
	public Component getComponent() {
		return theComponent;
	}
	public void setComponent(Component aComponent) {
		theComponent = aComponent;
	}
	/**
	 * Компонент
	 */
	private Component theComponent;
	/**
	 * Дата установки
	 */
	@Comment("Дата установки")
	
	public Date getEntranceDate() {
		return theEntranceDate;
	}
	public void setEntranceDate(Date aEntranceDate) {
		theEntranceDate = aEntranceDate;
	}
	/**
	 * Дата установки
	 */
	private Date theEntranceDate;
	/**
	 * Дата удаления
	 */
	@Comment("Дата удаления")
	
	public Date getRemoveDate() {
		return theRemoveDate;
	}
	public void setRemoveDate(Date aRemoveDate) {
		theRemoveDate = aRemoveDate;
	}
	/**
	 * Дата удаления
	 */
	private Date theRemoveDate;
}
