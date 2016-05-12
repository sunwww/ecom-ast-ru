package ru.ecom.mis.ejb.domain.expert.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Comment("Справочник заключений по КЭР. Направляется на...")
public class VocExpertConclusionSent extends VocBaseEntity {
	/** Обоснование  */
	@Comment("Обоснование ")
	@OneToOne
	public VocExpertConclusion getConclusion() {return theConclusion;}
	public void setConclusion(VocExpertConclusion aConclusion) {theConclusion = aConclusion;}

	/** Обоснование  */
	private VocExpertConclusion theConclusion;
	
	/** Неактуальный */
	@Comment("Неактуальный")
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}

	/** Неактуальный */
	private Boolean theNoActuality;
	
	/** Доп. данные */
	@Comment("Доп. данные")
	public String getAdditionData() {
		return theAdditionData;
	}

	public void setAdditionData(String aAdditionData) {
		theAdditionData = aAdditionData;
	}

	/** Доп. данные */
	private String theAdditionData;
	
	/** Код обоснования */
	@Comment("Код обоснования")
	public String getConclusionCode() {return theConclusionCode;}
	public void setConclusionCode(String aConclusionCode) {theConclusionCode = aConclusionCode;}

	/** Код обоснования */
	private String theConclusionCode;
}
