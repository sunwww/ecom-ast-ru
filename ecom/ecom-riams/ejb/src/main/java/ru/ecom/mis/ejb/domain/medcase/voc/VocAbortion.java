package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник абортов")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocAbortion extends VocBaseEntity {
	/** Аборт неуточненный (внебольничный) */
	private Boolean isUnsafe;
	/** Другие виды аборта (криминальный) */
	private Boolean isCriminal;
	/** Аборт по социальным показаниям */
	private Boolean isSocialIndicators;
	/** Аборт по медицинским показаниям */
	private Boolean isMedicalEvidence;
	/** Медицинский аборт (легальный) */
	private Boolean isLegal;
	/** Самопроизвольный аборт */
	private Boolean isSpontaneous;
	/** Медикаментозный метод */
	private Boolean isMedicalMethod;
	/** В связи с выявлением врожденных пороков развития плода */
	private Boolean isCongenitalMalformations;
	/** Первобеременная */
	private Boolean isPrimigravidas;
	/** Макс. кол-во недель */
	private Long maxNumberWeeks;
	/** Кол-во недель */
	private Long minNumberWeeks;
}
