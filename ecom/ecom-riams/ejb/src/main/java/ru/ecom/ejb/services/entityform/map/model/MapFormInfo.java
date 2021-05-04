package ru.ecom.ejb.services.entityform.map.model;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.entityform.map.model.forclass.EntityFormPersistanceAnnotation;
import ru.ecom.ejb.services.entityform.map.model.forclass.ParentAnnotation;
import ru.ecom.ejb.services.entityform.map.model.forclass.WebTrailAnnotation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import java.io.Serializable;

@Getter
@Setter
public class MapFormInfo implements Serializable {


	public String getClassname() {
		return "ru/ecom/ejb/services/entityform/MapEntityForm";
	}

	/** Куда сохранять */
	private EntityFormPersistanceAnnotation entityFormPersistance;
	
	/** Префикс политики безопасности */
	private String securityPrefix;

	/** Родительская форма */
	private ParentAnnotation parent;
	
	/** Web Trail */
	private WebTrailAnnotation webTrail;
	
	/** Название */
	private String name;
	/** Комментарий */
	private String comment;
	
}
