package ru.ecom.mis.ejb.domain.vaccination;

import java.sql.Date;
import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Оценка вакцинации
 * @author azviagin
 *
 */
@Entity
@Comment("Оценка вакцинации")
@Table(schema="SQLUser")
@Getter
@Setter
public class VaccinationAssesment extends BaseEntity {

	/** Вакцинация */
	@Comment("Вакцинация")
	@OneToOne
	public Vaccination getVaccination() {
		return vaccination;
	}

	/** Вакцинация */
	private Vaccination vaccination;
	
	/**
	 * Дата оценки
	 */
	private Date assesmentDate;

	/**
	 * Время оценки
	 */
	private Time assesmentTime;

	/**
	 * Кто оценивал
	 */
	@Comment("Кто оценивал")
	@OneToOne
	public WorkFunction getWorker() {
		return worker;
	}


	/**
	 * Кто оценивал
	 */
	private WorkFunction worker;
	

	/**
	 * Комментарий к общей реакции
	 */
	private String commonReactionComment;

	/**
	 * Комментарий к локальной реакции
	 */
	private String localReactionComment;

}
