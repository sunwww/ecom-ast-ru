package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник подтипов услуг")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "serviceType" }) })
public class VocServiceSubType extends VocBaseEntity {
	/** Код типа */
	@Comment("Код типа")
	public String getServiceType() {return theServiceType;}
	public void setServiceType(String aServiceType) {theServiceType = aServiceType;}

	/** Код типа */
	private String theServiceType;
}
