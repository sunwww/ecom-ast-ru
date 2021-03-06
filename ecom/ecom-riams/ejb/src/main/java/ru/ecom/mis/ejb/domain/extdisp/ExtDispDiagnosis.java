package ru.ecom.mis.ejb.domain.extdisp;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

	/**
	 * Диагноз дополнительной диспансеризации
	 */
	@Comment("Диагноз дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="visit")
    }) 
public class ExtDispDiagnosis extends BaseEntity{
	/**
	 * Визит
	 */
	@Comment("Визит")
	@ManyToOne
	public ExtDispVisit getVisit() {
		return theVisit;
	}
	public void setVisit(ExtDispVisit aVisit) {
		theVisit = aVisit;
	}
	/**
	 * Визит
	 */
	private ExtDispVisit theVisit;
	/**
	 * МКБ
	 */
	@Comment("МКБ")
	@OneToOne
	public VocIdc10 getIdc() {
		return theIdc;
	}
	public void setIdc(VocIdc10 aIdc) {
		theIdc = aIdc;
	}
	/**
	 * МКБ
	 */
	private VocIdc10 theIdc;
	/**
	 * Подозрение на заболевание
	 */
	@Comment("Подозрение на заболевание")
	
	public Boolean getIsSuspicion() {
		return theIsSuspicion;
	}
	public void setIsSuspicion(Boolean aIsSuspicion) {
		theIsSuspicion = aIsSuspicion;
	}
	/**
	 * Подозрение на заболевание
	 */
	private Boolean theIsSuspicion;
	/**
	 * Название
	 */
	@Comment("Название")
	
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	/**
	 * Название
	 */
	private String theName;
}
