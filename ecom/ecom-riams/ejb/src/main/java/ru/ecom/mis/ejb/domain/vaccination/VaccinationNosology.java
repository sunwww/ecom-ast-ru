package ru.ecom.mis.ejb.domain.vaccination;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.vaccination.voc.VaccineVocNosology;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Вакцинируемая нозология
 * @author azviagin
 *
 */
@Entity
@Comment("Вакцинируемая нозология")
@Table(schema="SQLUser")
@Getter
@Setter
public class VaccinationNosology extends BaseEntity{


	/**
	 * Название
	 */
	private String name;

	/**
	 * Список вакцин
	 */
	@Comment("Список вакцин")
	@OneToMany(mappedBy="nosology", cascade=CascadeType.ALL)
	public List<VaccineVocNosology> getVaccineList() {
		return vaccineList;
	}

	/**
	 * Список вакцин
	 */
	private List<VaccineVocNosology> vaccineList;

}
