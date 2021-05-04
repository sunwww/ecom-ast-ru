package ru.ecom.mis.ejb.domain.vaccination.voc;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VaccineVocMethod extends BaseEntity{
	
	/** Метод вакцинации */
	@Comment("Метод вакцинации")
	@ManyToOne
	public VocVaccinationMethod getMethod() {
		return method;
	}

	/** Метод вакцинации */
	private VocVaccinationMethod method;
	
	/** Вакцина */
	@Comment("Вакцина")
	@ManyToOne
	public Vaccine getVaccine() {
		return vaccine;
	}

	/** Вакцина */
	private Vaccine vaccine;

}
