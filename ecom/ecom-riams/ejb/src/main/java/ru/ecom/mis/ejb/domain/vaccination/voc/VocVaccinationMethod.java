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
 * Метод вакцинации
 * @author azviagin
 *
 */
@Entity
@Comment("Вакцинный материал")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocVaccinationMethod extends VocIdName {
	
	/** Вакцина */
	@Comment("Вакцина")
	@OneToMany(mappedBy="method", cascade=CascadeType.ALL)
	public List<VaccineVocMethod> getVaccine() {
		return vaccine;
	}
	/** Вакцина */
	private List<VaccineVocMethod> vaccine;

}
