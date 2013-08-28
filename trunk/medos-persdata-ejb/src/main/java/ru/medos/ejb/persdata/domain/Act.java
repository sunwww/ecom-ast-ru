package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Акт
	 */
	@Comment("Акт")
@Entity
@Table(schema="SQLUser")
public class Act extends JournalData{
	/**
	 * Номер акта
	 */
	@Comment("Номер акта")
	
	public String getActNumber() {
		return theActNumber;
	}
	public void setActNumber(String aActNumber) {
		theActNumber = aActNumber;
	}
	/**
	 * Номер акта
	 */
	private String theActNumber;
}
