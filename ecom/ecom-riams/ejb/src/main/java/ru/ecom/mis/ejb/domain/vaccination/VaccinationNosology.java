package ru.ecom.mis.ejb.domain.vaccination;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
public class VaccinationNosology extends BaseEntity{

	/**
	 * Название
	 */
	@Comment("Название")
	public String getName() {
		return theName;
	}

	/**
	 * Название
	 */
	public void setName(String a_Property) {
		theName = a_Property;
	}

	/**
	 * Название
	 */
	private String theName;

	/**
	 * Список вакцин
	 */
	@Comment("Список вакцин")
	@OneToMany(mappedBy="nosology", cascade=CascadeType.ALL)
	public List<VaccineVocNosology> getVaccineList() {
		return theVaccineList;
	}

	/**
	 * Список вакцин
	 */
	public void setVaccineList(List<VaccineVocNosology> a_Property) {
		theVaccineList = a_Property;
	}

	/**
	 * Список вакцин
	 */
	private List<VaccineVocNosology> theVaccineList;

}
