package ru.ecom.mis.ejb.domain.vaccination;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
public class VaccineSeries extends BaseEntity {

	/**
	 * Серия
	 */
	@Comment("Серия")
	public String getSeries() {
		return theSeries;
	}

	/**
	 * Серия
	 */
	public void setSeries(String a_Property) {
		theSeries = a_Property;
	}

	/**
	 * Серия
	 */
	private String theSeries;

	/**
	 * Дата окончания годности
	 */
	@Comment("Дата окончания годности")
	public Date getExpirationDate() {
		return theExpirationDate;
	}

	/**
	 * Дата окончания годности
	 */
	public void setExpirationDate(Date a_Property) {
		theExpirationDate = a_Property;
	}

	/**
	 * Дата окончания годности
	 */
	private Date theExpirationDate;

	/**
	 * Вакцина
	 */
	@Comment("Вакцина")
	@OneToOne
	public Vaccine getVaccine() {
		return theVaccine;
	}

	/**
	 * Вакцина
	 */
	public void setVaccine(Vaccine a_Property) {
		theVaccine = a_Property;
	}

	/**
	 * Вакцина
	 */
	private Vaccine theVaccine;

	/**
	 * Недействительность
	 */
	@Comment("Недействительность")
	public Boolean getNoActuality() {
		return theNoActuality;
	}

	/**
	 * Недействительность
	 */
	public void setNoActuality(Boolean a_Property) {
		theNoActuality = a_Property;
	}

	/**
	 * Недействительность
	 */
	private Boolean theNoActuality;

}
