package ru.ecom.mis.ejb.domain.vaccination.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocVaccinationMaterial extends VocIdName {

	/**
	 * Вакцина
	 */
	@Comment("Вакцина")
	@OneToOne
	public Vaccine getVaccine() {
		return vaccine;
	}

	/**
	 * Вакцина
	 */
	private Vaccine vaccine;

}
