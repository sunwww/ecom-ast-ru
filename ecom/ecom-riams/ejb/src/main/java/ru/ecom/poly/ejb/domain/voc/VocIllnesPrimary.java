package ru.ecom.poly.ejb.domain.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAcuityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPrimaryDiagnosis;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;


@Entity
public class VocIllnesPrimary extends VocBaseEntity {
	/** Первичность */
	@Comment("Первичность")
	@OneToOne
	public VocPrimaryDiagnosis getPrimary() {return thePrimary;}

	public void setPrimary(VocPrimaryDiagnosis aPrimary){thePrimary = aPrimary;}
	
	/** Острота */
	@Comment("Острота")
	@OneToOne
	public VocAcuityDiagnosis getIllnesType() {return theIllnesType;}

	public void setIllnesType(VocAcuityDiagnosis aIllnesType) {theIllnesType = aIllnesType;}
	
	/** Устарел */
	@Comment("Устарел")
	public Boolean getDeprecated() {return theDeprecated;}
	public void setDeprecated(Boolean aDeprecated) {theDeprecated = aDeprecated;}

	/** Устарел */
	private Boolean theDeprecated;

	/** Острота */
	private VocAcuityDiagnosis theIllnesType;
	/** Первичность */
	private VocPrimaryDiagnosis thePrimary;

	/** Код ОМС */
	@Comment("Код ОМС")
	public String getOmcCode() {return theOmcCode;}
	public void setOmcCode(String aOmcCode) {theOmcCode = aOmcCode;}
	/** Код ОМС */
	private String theOmcCode ;


}
