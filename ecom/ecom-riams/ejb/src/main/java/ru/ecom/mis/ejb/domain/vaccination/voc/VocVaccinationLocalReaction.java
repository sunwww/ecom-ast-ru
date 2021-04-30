package ru.ecom.mis.ejb.domain.vaccination.voc;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocVaccinationLocalReaction extends VocIdName {
	
	/** Вакцина */
	@Comment("Вакцина")
	@OneToMany(mappedBy="localReaction", cascade=CascadeType.ALL)
	public List<VaccineVocLocalReaction> getVaccine() {
		return vaccine;
	}

	/** Вакцина */
	private List<VaccineVocLocalReaction> vaccine;

}
