package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Справочник результатов госпитализации
 * 	выздоровление, улучшение, без перемен, ухудшение, здоров, умер
 */
@Entity
@Comment("Справочник результатов госпитализации")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocHospitalizationResult extends VocBaseEntity {
	/** Код федеральный по дневному стационару */
	private String codefds;
	/** Код федеральный по круглосуточному стационару */
	private String codefkl;
	/** Омс код */
	private String omcCode;
	/** В архиве */
	private Boolean isArchival;
}
