package ru.ecom.mis.ejb.domain.vaccination.voc;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Метод вакцинации
 * @author azviagin
 *
 */
@Entity
@Comment("Вакцинный материал")
@Table(schema="SQLUser")
public class VocVaccinationMethod extends VocIdName {
	
	/** Вакцина */
	@Comment("Вакцина")
	@OneToMany(mappedBy="method", cascade=CascadeType.ALL)
	public List<VaccineVocMethod> getVaccine() {
		return theVaccine;
	}

	public void setVaccine(List<VaccineVocMethod> aVaccine) {
		theVaccine = aVaccine;
	}

	/** Вакцина */
	private List<VaccineVocMethod> theVaccine;

}
