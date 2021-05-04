package ru.ecom.mis.ejb.domain.vaccination;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Серия вакцинного материала
 * @author azviagin
 *
 */
@Entity
@Comment("Серия вакцинного материала")
@Table(schema="SQLUser")
@Getter
@Setter
public class VaccineSeries extends BaseEntity {

	/**
	 * Серия
	 */
	private String series;

	/**
	 * Дата окончания годности
	 */
	private Date expirationDate;

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

	/**
	 * Недействительность
	 */
	private Boolean noActuality;

}
