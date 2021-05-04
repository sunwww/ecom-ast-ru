package ru.ecom.jaas.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "objectCode" }) })
@Getter
@Setter
public class VocPermission extends VocBaseEntity {
	/** Объект */
	@Comment("Объект")
	@OneToOne
	public VocObjectPermission getObject() {return object;}
	/** Объект */
	private VocObjectPermission object;
	/** Код объекта */
	private String objectCode;
}
