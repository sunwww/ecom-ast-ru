package ru.ecom.diary.ejb.service.protocol;

import java.io.Serializable;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@SuppressWarnings("serial")
public class ParameterType implements Serializable{
	/** Название */
	@Comment("Название")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** ID */
	@Comment("ID")
	public Long getId() {return theId;}
	public void setId(Long aType) {theId = aType;}

	/** Тип */
	@Comment("Тип")
	public String getType() {return theType;	}
	public void setType(String aType) {theType = aType;}

	/** Тип */
	private String theType;
	/** ID */
	private Long theId;
	/** Название */
	private String theName;

}
