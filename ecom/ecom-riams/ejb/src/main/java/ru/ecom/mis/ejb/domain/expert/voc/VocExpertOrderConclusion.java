package ru.ecom.mis.ejb.domain.expert.voc;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Обоснование направления на ВК")
@AIndexes(value = { @AIndex(properties = { "typeCode" },table="VocExpertOrderConclusion") } )
@Getter
@Setter
public class VocExpertOrderConclusion extends VocBaseEntity{
	/** Неактуальный */
	private Boolean noActuality;
	/** Тип ВК */
	private String typeCode;
}
