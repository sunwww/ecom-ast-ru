package ru.ecom.mis.ejb.domain.contract;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
	/**
	 * Физическое лицо
	 */
	@Comment("Физическое лицо")
@Entity
@AIndexes(value = {
		@AIndex(properties = { "patient" },table="ContractPerson")
		}
)
public class NaturalPerson extends ContractPerson {
	/**
	 * Пациента
	 */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {
		return thePatient;
	}
	public void setPatient(Patient aPatient) {
		thePatient = aPatient;
	}
	private Patient thePatient;
	
	@Transient
	public String getInformation() {
		return "Физ. лицо: " + (thePatient != null ? thePatient.getFio() : "");
	}

	public NaturalPerson(){}
	public NaturalPerson(Patient aPatient){thePatient=aPatient;}
}
