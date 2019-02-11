package ru.ecom.ejb.services.entityform.map.model;

import ru.ecom.ejb.services.entityform.map.model.forclass.EntityFormPersistanceAnnotation;
import ru.ecom.ejb.services.entityform.map.model.forclass.ParentAnnotation;
import ru.ecom.ejb.services.entityform.map.model.forclass.WebTrailAnnotation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import java.io.Serializable;

public class MapFormInfo implements Serializable {

	/** Название */
	@Comment("Название")
	public String getName() {
		return theName;
	}

	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {
		return theComment;
	}

	public void setComment(String aComment) {
		theComment = aComment;
	}

	public String getClassname() {
		return //theName ; //"$$map$$hello123"; //theName + System.currentTimeMillis() ; 
                "ru/ecom/ejb/services/entityform/MapEntityForm";
	}
	
	public void setName(String aName) {
		theName = aName;
	}

	/** Куда сохранять */
	@Comment("Куда сохранять")
	public EntityFormPersistanceAnnotation getEntityFormPersistance() {
		return theEntityFormPersistance;
	}

	public void setEntityFormPersistance(EntityFormPersistanceAnnotation aEntityFormPersistance) {
		theEntityFormPersistance = aEntityFormPersistance;
	}

	/** Родительская форма */
	@Comment("Родительская форма")
	public ParentAnnotation getParent() {
		return theParent;
	}

	public void setParent(ParentAnnotation aParent) {
		theParent = aParent;
	}

	/** Web Trail */
	@Comment("Web Trail")
	public WebTrailAnnotation getWebTrail() {
		return theWebTrail;
	}

	public void setWebTrail(WebTrailAnnotation aWebTrail) {
		theWebTrail = aWebTrail;
	}

	/** Префикс политики безопасности */
	@Comment("Префикс политики безопасности")
	public String getSecurityPrefix() {
		return theSecurityPrefix;
	}

	public void setSecurityPrefix(String aSecurityPrefix) {
		theSecurityPrefix = aSecurityPrefix;
	}

	/** Куда сохранять */
	private EntityFormPersistanceAnnotation theEntityFormPersistance;
	
	/** Префикс политики безопасности */
	private String theSecurityPrefix;

	/** Родительская форма */
	private ParentAnnotation theParent;
	
	/** Web Trail */
	private WebTrailAnnotation theWebTrail;
	
	/** Название */
	private String theName;
	/** Комментарий */
	private String theComment;
	
}
