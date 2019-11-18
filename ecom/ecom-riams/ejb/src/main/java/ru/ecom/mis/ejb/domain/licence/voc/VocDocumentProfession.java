package ru.ecom.mis.ejb.domain.licence.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Comment("Справочник профессий и производственных факторов")
@Entity
@Table(schema="SQLUser")
public class VocDocumentProfession extends VocBaseEntity {
	/** Производственный фактор */
	@Comment("Производственный фактор")
	public String getFactorOfProduction() {return theFactorOfProduction;}
	public void setFactorOfProduction(String aFactorOfProduction) {theFactorOfProduction = aFactorOfProduction;}
	private String theFactorOfProduction;
}