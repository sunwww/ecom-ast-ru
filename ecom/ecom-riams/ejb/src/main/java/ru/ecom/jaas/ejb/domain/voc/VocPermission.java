package ru.ecom.jaas.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "objectCode" }) })
public class VocPermission extends VocBaseEntity {
	/** Объект */
	@Comment("Объект")
	@OneToOne
	public VocObjectPermission getObject() {return theObject;}
	public void setObject(VocObjectPermission aObject) {theObject = aObject;}
	
	/** Объект */
	private VocObjectPermission theObject;
	
	/** Код объекта */
	@Comment("Код объекта")
	public String getObjectCode() {return theObjectCode;}
	public void setObjectCode(String aObjectCode) {theObjectCode = aObjectCode;}

	/** Код объекта */
	private String theObjectCode;
}
