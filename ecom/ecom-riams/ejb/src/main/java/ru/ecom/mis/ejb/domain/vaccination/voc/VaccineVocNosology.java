package ru.ecom.mis.ejb.domain.vaccination.voc;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.vaccination.VaccinationNosology;
import ru.ecom.mis.ejb.domain.vaccination.Vaccine;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Нозология вакцины
 * @author oegorova
 *
 */

@Comment("Нозология вакцины")
@Entity
@Table(schema="SQLUser")
public class VaccineVocNosology extends BaseEntity{
	
	/** Нозология */
	@Comment("Нозология")
	@ManyToOne
	public VaccinationNosology getNosology() {
		return theNosology;
	}

	public void setNosology(VaccinationNosology aNosology) {
		theNosology = aNosology;
	}

	/** Нозология */
	private VaccinationNosology theNosology;
	
	/** Вацина */
	@Comment("Вацина")
	@ManyToOne
	public Vaccine getVaccine() {
		return theVaccine;
	}

	public void setVaccine(Vaccine aVaccine) {
		theVaccine = aVaccine;
	}

	/** Вацина */
	private Vaccine theVaccine;

}
