package ru.ecom.mis.ejb.domain.medcase.voc;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocServiceSubType extends VocBaseEntity {
	/** Код типа */
	private String serviceType;
	
	/** Тип биоматериала */
	private String biomaterial;
}