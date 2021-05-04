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
 * Общие реакции по вакцинам
 * @author oegorova
 *
 */

@Comment("Общие реакции по вакцинам")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VaccineVocCommonReaction extends BaseEntity{
	
	/** Вакцина */
	@Comment("Вакцина")
	@ManyToOne
	public Vaccine getVaccine() {
		return vaccine;
	}

	/** Вакцина */
	private Vaccine vaccine;
	
	/** Общая реакция */
	@Comment("Общая реакция")
	@ManyToOne
	public VocVaccinationCommonReaction getCommonReaction() {
		return commonReaction;
	}

	/** Общая реакция */
	private VocVaccinationCommonReaction commonReaction;
	


}
