package ru.ecom.mis.ejb.domain.vaccination;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VaccinationEstop extends BaseEntity{

	/** Вакцинация */
	@Comment("Вакцинация")
	@OneToOne
	public Vaccination getVaccination() {
		return vaccination;
	}

	/** Вакцинация */
	private Vaccination vaccination;
	
	/**
	 * Дата медотвода
	 */
	private Date estopDate;
	
	/**
	 * Тип медотвода
	 */
	@Comment("Тип медотвода")
	@OneToOne
	public VocVaccinationEstopType getEstopType() {
		return estopType;
	}

	/**
	 * Тип медотвода
	 */
	private VocVaccinationEstopType estopType;

	/**
	 * Комментарий
	 */
	private String comments;

	/**
	 * Дата окончания медотвода
	 */
	private Date dateFinish;

	/**
	 * Список нозологий
	 */
	@Comment("Список нозологий")
	@OneToMany
	public List<VaccinationNosology> getNosologyList() {
		return nosologyList;
	}

	/**
	 * Список нозологий
	 */
	private List<VaccinationNosology> nosologyList;

	/**
	 * Недействительность
	 */
	private Boolean noActuality;

	

}
