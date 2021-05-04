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
 * Общие реакции вакцинации
 * @author azviagin
 *
 */
@Entity
@Comment("Общие реакции вакцинации")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocVaccinationCommonReaction extends VocIdName {
	
	/** Вакцина */
	@Comment("Вакцина")
	@OneToMany(mappedBy="commonReaction", cascade=CascadeType.ALL)
	public List<VaccineVocCommonReaction> getVaccine() {
		return vaccine;
	}
	/** Вакцина */
	private List<VaccineVocCommonReaction> vaccine;

}
