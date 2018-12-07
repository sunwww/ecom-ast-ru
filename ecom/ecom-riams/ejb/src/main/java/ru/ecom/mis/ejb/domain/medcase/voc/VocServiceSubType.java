package ru.ecom.mis.ejb.domain.medcase.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

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
	
	/** Тип биоматериала */
	@Comment("Тип биоматериала")
	public String getBiomaterial() {return theBiomaterial;}
	public void setBiomaterial(String aBiomaterial) {theBiomaterial = aBiomaterial;}

	/** Тип биоматериала */
	private String theBiomaterial;
}