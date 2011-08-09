package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * 
	 */
	@Comment("")
@Entity
@Table(schema="SQLUser")
public class TransitoryAsset extends Asset{
	/**
	 * Модель
	 */
	@Comment("Модель")
	
	public String getModel() {
		return theModel;
	}
	public void setModel(String aModel) {
		theModel = aModel;
	}
	/**
	 * Модель
	 */
	private String theModel;
}
