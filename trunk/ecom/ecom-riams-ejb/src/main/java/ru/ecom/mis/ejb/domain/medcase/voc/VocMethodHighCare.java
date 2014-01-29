package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class VocMethodHighCare extends VocBaseEntity {
	/** Код вида ВМП */
	@Comment("Код вида ВМП")
	public String getKindHighCare() {return theKindHighCare;}
	public void setKindHighCare(String aKindHighCare) {theKindHighCare = aKindHighCare;}

	/** Список диагнозов */
	@Comment("Список диагнозов")
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}

	/** Список диагнозов */
	private String theDiagnosis;
	/** Код вида ВМП */
	private String theKindHighCare;

}
