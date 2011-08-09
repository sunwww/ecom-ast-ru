package ru.ecom.mis.ejb.domain.vaccination.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.ecom.mis.ejb.domain.vaccination.Vaccine;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Вакцинный материал
 * @author azviagin
 *
 */
@Entity
@Comment("Вакцинный материал")
@Table(schema="SQLUser")
public class VocVaccinationMaterial extends VocIdName {

	/**
	 * Вакцина
	 */
	@Comment("Вакцина")
	@OneToOne
	public Vaccine getVaccine() {
		return theVaccine;
	}

	/**
	 * Вакцина
	 */
	public void setVaccine(Vaccine a_Property) {
		theVaccine = a_Property;
	}

	/**
	 * Вакцина
	 */
	private Vaccine theVaccine;

}
