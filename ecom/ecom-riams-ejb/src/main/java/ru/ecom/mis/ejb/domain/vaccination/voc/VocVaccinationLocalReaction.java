package ru.ecom.mis.ejb.domain.vaccination.voc;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Локальные реакции вакцинации
 * @author azviagin
 *
 */
@Entity
@Comment("Локальные реакции вакцинации")
@Table(schema="SQLUser")
public class VocVaccinationLocalReaction extends VocIdName {
	
	/** Вакцина */
	@Comment("Вакцина")
	@OneToMany(mappedBy="localReaction", cascade=CascadeType.ALL)
	public List<VaccineVocLocalReaction> getVaccine() {
		return theVaccine;
	}

	public void setVaccine(List<VaccineVocLocalReaction> aVaccine) {
		theVaccine = aVaccine;
	}

	/** Вакцина */
	private List<VaccineVocLocalReaction> theVaccine;

}
