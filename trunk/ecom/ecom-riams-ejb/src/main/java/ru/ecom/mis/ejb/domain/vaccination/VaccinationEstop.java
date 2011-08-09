package ru.ecom.mis.ejb.domain.vaccination;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.vaccination.voc.VocVaccinationEstopType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Медотвод
 * @author azviagin
 *
 */
@Entity
@Comment("Медотвод")
@Table(schema="SQLUser")
public class VaccinationEstop extends BaseEntity{

	/** Вакцинация */
	@Comment("Вакцинация")
	@OneToOne
	public Vaccination getVaccination() {
		return theVaccination;
	}

	public void setVaccination(Vaccination aVaccination) {
		theVaccination = aVaccination;
	}

	/** Вакцинация */
	private Vaccination theVaccination;
	
	/**
	 * Дата медотвода
	 */
	@Comment("Дата медотвода")
	public Date getEstopDate() {
		return theEstopDate;
	}

	/**
	 * Дата медотвода
	 */
	public void setEstopDate(Date a_Property) {
		theEstopDate = a_Property;
	}

	/**
	 * Дата медотвода
	 */
	private Date theEstopDate;
	
	/**
	 * Тип медотвода
	 */
	@Comment("Тип медотвода")
	@OneToOne
	public VocVaccinationEstopType getEstopType() {
		return theEstopType;
	}

	/**
	 * Тип медотвода
	 */
	public void setEstopType(VocVaccinationEstopType a_Property) {
		theEstopType = a_Property;
	}

	/**
	 * Тип медотвода
	 */
	private VocVaccinationEstopType theEstopType;

	/**
	 * Комментарий
	 */
	@Comment("Комментарий")
	public String getComments() {
		return theComments;
	}

	/**
	 * Комментарий
	 */
	public void setComments(String a_Property) {
		theComments = a_Property;
	}

	/**
	 * Комментарий
	 */
	private String theComments;

	/**
	 * Дата окончания медотвода
	 */
	@Comment("Дата окончания медотвода")
	public Date getDateFinish() {
		return theDateFinish;
	}

	/**
	 * Дата окончания медотвода
	 */
	public void setDateFinish(Date a_Property) {
		theDateFinish = a_Property;
	}

	/**
	 * Дата окончания медотвода
	 */
	private Date theDateFinish;

	/**
	 * Список нозологий
	 */
	@Comment("Список нозологий")
	@OneToMany
	public List<VaccinationNosology> getNosologyList() {
		return theNosologyList;
	}

	/**
	 * Список нозологий
	 */
	public void setNosologyList(List<VaccinationNosology> a_Property) {
		theNosologyList = a_Property;
	}

	/**
	 * Список нозологий
	 */
	private List<VaccinationNosology> theNosologyList;

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
