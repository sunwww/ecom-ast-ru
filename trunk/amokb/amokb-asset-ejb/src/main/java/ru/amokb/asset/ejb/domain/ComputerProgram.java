package ru.amokb.asset.ejb.domain;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Программа компьютера
	 */
	@Comment("Программа компьютера")
@Entity
@Table(schema="SQLUser")
public class ComputerProgram extends BaseEntity{
	/**
	 * Программа
	 */
	@Comment("Программа")
	@OneToOne
	public Program getProgram() {
		return theProgram;
	}
	public void setProgram(Program aProgram) {
		theProgram = aProgram;
	}
	/**
	 * Программа
	 */
	private Program theProgram;
	/**
	 * 
	 */
	@Comment("")
	@ManyToOne
	public Computer getComputer() {
		return theComputer;
	}
	public void setComputer(Computer aComputer) {
		theComputer = aComputer;
	}
	/**
	 * 
	 */
	private Computer theComputer;
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
