package ru.ecom.jaas.ejb.service;

import java.io.Serializable;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

public class PolicyForm implements Serializable{
	public 	PolicyForm(String aKey,String aName,String aComment) {
		theKey = aKey ;
		theName = aName;
		theComment = aComment ;
	}
	/** Ключ */
	@Comment("Ключ")
	public String getKey() {return theKey;}
	public void setKey(String aKey) {theKey = aKey;}

	/** Название */
	@Comment("Название")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Существует в базе? */
	@Comment("Существует в базе?")
	public Boolean getIsExist() {return theIsExist;}
	public void setIsExist(Boolean aIsExist) {theIsExist = aIsExist;}

	/** Id в файле */
	@Comment("Id в файле")
	public Long getIdInFile() {return theIdInFile;}
	public void setIdInFile(Long aIdInFile) {theIdInFile = aIdInFile;}

	/** Id в файле */
	private Long theIdInFile;
	/** Существует в базе? */
	private Boolean theIsExist;
	/** Комментарий */
	private String theComment;
	/** Название */
	private String theName;
	/** Ключ */
	private String theKey;

}
