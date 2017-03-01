package ru.ecom.mis.ejb.domain.patient;

import javax.persistence.Entity;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
public class PatientList extends BaseEntity{

	/** Тип списка */
	@Comment("Тип списка")
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}
	/** Тип списка */
	private Long theType;
	
	/** Название списка */
	@Comment("Название списка")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	/** Название списка */
	private String theName;
	
	/** Цвет сообщения */
	@Comment("Цвет сообщения")
	public String getColorName() {return theColorName;}
	public void setColorName(String aColorName) {theColorName = aColorName;}
	/** Цвет сообщения */
	private String theColorName;
	
	
	/** Цвет текста */
	@Comment("Цвет текста")
	public String getColorText() {return theColorText;}
	public void setColorText(String aColorText) {theColorText = aColorText;}

	
	/** Отображать цвет при поиске */
	@Comment("Отображать цвет при поиске")
	public Boolean getIsViewWhenSeaching() {return theIsViewWhenSeaching;}
	public void setIsViewWhenSeaching(Boolean aIsViewWhenSeaching) {theIsViewWhenSeaching = aIsViewWhenSeaching;}

	/** Отображать цвет в WebTrail */
	@Comment("Отображать цвет в WebTrail")
	public Boolean getIsViewInWebTrail() {return theIsViewInWebTrail;}
	public void setIsViewInWebTrail(Boolean aIsViewInWebTrail) {theIsViewInWebTrail = aIsViewInWebTrail;}

	/** Отображать цвет в WebTrail */
	private Boolean theIsViewInWebTrail;
	/** Отображать цвет при поиске */
	private Boolean theIsViewWhenSeaching;
	/** Цвет текста */
	private String theColorText;
	/** Сообщение */
	@Comment("Сообщение")
	public String getMessage() {return theMessage;}
	public void setMessage(String aMessage) {theMessage = aMessage;}
	/** Сообщение */
	private String theMessage;
}
