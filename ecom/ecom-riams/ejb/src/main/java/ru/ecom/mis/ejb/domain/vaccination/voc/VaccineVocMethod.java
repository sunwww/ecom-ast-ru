package ru.ecom.mis.ejb.domain.vaccination.voc;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.vaccination.Vaccine;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Метод введения вакцины
 * @author oegorova
 *
 */

@Comment("Метод введения вакцины")
@Entity
@Table(schema="SQLUser")
public class VaccineVocMethod extends BaseEntity{
	
	/** Метод вакцинации */
	@Comment("Метод вакцинации")
	@ManyToOne
	public VocVaccinationMethod getMethod() {
		return theMethod;
	}

	public void setMethod(VocVaccinationMethod aMethod) {
		theMethod = aMethod;
	}

	/** Метод вакцинации */
	private VocVaccinationMethod theMethod;
	
	/** Вакцина */
	@Comment("Вакцина")
	@ManyToOne
	public Vaccine getVaccine() {
		return theVaccine;
	}

	public void setVaccine(Vaccine aVaccine) {
		theVaccine = aVaccine;
	}

	/** Вакцина */
	private Vaccine theVaccine;

}
