package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник исходов визита")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocVisitOutcome extends VocBaseEntity {
	/** Код фед. ск.помощи */
	private String codefamb;
	/** Код фед. поликлинический */
	private String codefpl;
}
