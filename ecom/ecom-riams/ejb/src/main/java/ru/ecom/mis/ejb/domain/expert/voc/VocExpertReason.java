package ru.ecom.mis.ejb.domain.expert.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "typeCode" },table="VocExpertReason") } )
@Getter
@Setter
public class VocExpertReason extends VocBaseEntity {
	/** Неактуальный */
	private Boolean noActuality;
	/** Доп. данные */
	private String additionData;
	/** Тип ВК */
	private String typeCode;
}
