package ru.ecom.jaas.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class VocPermission extends VocBaseEntity {
	/** Объект */
	@Comment("Объект")
	@OneToOne
	public VocObjectPermission getObject() {return theObject;}
	public void setObject(VocObjectPermission aObject) {theObject = aObject;}

	/** Объект */
	private VocObjectPermission theObject;
}
