package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник исходов госпитализации
 * 	выписан - 1, в т.ч. в дневной стационар - 2, в круглосуточный стационар - 3, 
 * 	переведен в другой стационар - 4
 */
@Entity
@Comment("Справочник исходов госпитализации")
@Table(schema="SQLUser")
public class VocHospitalizationOutcome extends VocBaseEntity {
	/** Не отображать при выписке */
	@Comment("Не отображать при выписке")
	public Boolean getIsNotViewDischarge() {return theIsNotViewDischarge;}
	public void setIsNotViewDischarge(Boolean aIsNotViewDischarge) {theIsNotViewDischarge = aIsNotViewDischarge;}

	/** Не отображать при поступлении */
	@Comment("Не отображать при поступлении")
	public Boolean getIsNotViewAdmission() {return theIsNotViewAdmission;}
	public void setIsNotViewAdmission(Boolean aIsNotViewAdmission) {theIsNotViewAdmission = aIsNotViewAdmission;}

	/** Псих.код */
	@Comment("Псих.код")
	public String getPsychCode() {return thePsychCode;}
	public void setPsychCode(String aPsychCode) {thePsychCode = aPsychCode;}

	/** Псих.код */
	private String thePsychCode;
	/** Не отображать при поступлении */
	private Boolean theIsNotViewAdmission;
	/** Не отображать при выписке */
	private Boolean theIsNotViewDischarge;

}
