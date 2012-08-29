package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник абортов")
@Table(schema="SQLUser")
public class VocAbortion extends VocBaseEntity {
	/** Кол-во недель */
	@Comment("Кол-во недель")
	public Long getMinNumberWeeks() {return theMinNumberWeeks;}
	public void setMinNumberWeeks(Long aMinNumberWeeks) {theMinNumberWeeks = aMinNumberWeeks;}
	
	/** Макс. кол-во недель */
	@Comment("Макс. кол-во недель")
	public Long getMaxNumberWeeks() {return theMaxNumberWeeks;}
	public void setMaxNumberWeeks(Long aMaxNumberWeeks) {theMaxNumberWeeks = aMaxNumberWeeks;}

	/** Первобеременная */
	@Comment("Первобеременная")
	public Boolean getIsPrimigravidas() {return theIsPrimigravidas;}
	public void setIsPrimigravidas(Boolean aIsPrimigravidas) {theIsPrimigravidas = aIsPrimigravidas;}

	/** В связи с выявлением врожденных пороков развития плода */
	@Comment("В связи с выявлением врожденных пороков развития плода")
	public Boolean getIsCongenitalMalformations() {return theIsCongenitalMalformations;}
	public void setIsCongenitalMalformations(Boolean aCongenitalMalformations) {theIsCongenitalMalformations = aCongenitalMalformations;}

	/** Медикаментозный метод */
	@Comment("Медикаментозный метод")
	public Boolean getIsMedicalMethod() {return theIsMedicalMethod;}
	public void setIsMedicalMethod(Boolean aIsMedicalMethod) {theIsMedicalMethod = aIsMedicalMethod;}

	/** Самопроизвольный аборт */
	@Comment("Самопроизвольный аборт")
	public Boolean getIsSpontaneous() {return theIsSpontaneous;}
	public void setIsSpontaneous(Boolean aIsSpontaneous) {theIsSpontaneous = aIsSpontaneous;}

	/** Медицинский аборт (легальный) */
	@Comment("Медицинский аборт (легальный)")
	public Boolean getIsLegal() {return theIsLegal;}
	public void setIsLegal(Boolean aIsLegal) {theIsLegal = aIsLegal;}

	/** Аборт по медицинским показаниям */
	@Comment("Аборт по медицинским показаниям")
	public Boolean getIsMedicalEvidence() {return theIsMedicalEvidence;}
	public void setIsMedicalEvidence(Boolean aIsMedicalEvidence) {theIsMedicalEvidence = aIsMedicalEvidence;}

	/** Аборт по социальным показаниям */
	@Comment("Аборт по социальным показаниям")
	public Boolean getIsSocialIndicators() {return theIsSocialIndicators;}
	public void setIsSocialIndicators(Boolean aIsSocialIndicators) {theIsSocialIndicators = aIsSocialIndicators;}

	/** Другие виды аборта (криминальный) */
	@Comment("Другие виды аборта (криминальный)")
	public Boolean getIsCriminal() {return theIsCriminal;}
	public void setIsCriminal(Boolean aIsCriminal) {theIsCriminal = aIsCriminal;}

	/** Аборт неуточненный (внебольничный) */
	@Comment("Аборт неуточненный (внебольничный)")
	public Boolean getIsUnsafe() {return theIsUnsafe;}
	public void setIsUnsafe(Boolean aIsUnsafe) {theIsUnsafe = aIsUnsafe;}

	/** Аборт неуточненный (внебольничный) */
	private Boolean theIsUnsafe;
	/** Другие виды аборта (криминальный) */
	private Boolean theIsCriminal;
	/** Аборт по социальным показаниям */
	private Boolean theIsSocialIndicators;
	/** Аборт по медицинским показаниям */
	private Boolean theIsMedicalEvidence;
	/** Медицинский аборт (легальный) */
	private Boolean theIsLegal;
	/** Самопроизвольный аборт */
	private Boolean theIsSpontaneous;
	/** Медикаментозный метод */
	private Boolean theIsMedicalMethod;
	/** В связи с выявлением врожденных пороков развития плода */
	private Boolean theIsCongenitalMalformations;
	/** Первобеременная */
	private Boolean theIsPrimigravidas;
	/** Макс. кол-во недель */
	private Long theMaxNumberWeeks;
	/** Кол-во недель */
	private Long theMinNumberWeeks;
}
