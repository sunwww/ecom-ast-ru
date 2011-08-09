package ru.ecom.mis.ejb.domain.contract;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Физическое лицо
	 */
	@Comment("Физическое лицо")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "patient" },table="ContractPerson")
		}
)
public class NaturalPerson extends ContractPerson{
	/**
	 * Пациента
	 */
	@Comment("Пациента")
	@OneToOne
	public Patient getPatient() {
		return thePatient;
	}
	public void setPatient(Patient aPatient) {
		thePatient = aPatient;
	}
	/**
	 * Пациента
	 */
	private Patient thePatient;
	
	@Transient
	public String getInformation() {
		return new StringBuilder().append("Физ. лицо: ").append(thePatient!=null ? thePatient.getFio() : "").toString() ; 
	}
}
