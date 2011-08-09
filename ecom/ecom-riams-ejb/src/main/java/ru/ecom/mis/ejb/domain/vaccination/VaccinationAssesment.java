package ru.ecom.mis.ejb.domain.vaccination;

import java.sql.Date;
import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
public class VaccinationAssesment extends BaseEntity {

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
	 * Дата оценки
	 */
	@Comment("Дата оценки")
	public Date getAssesmentDate() {
		return theAssesmentDate;
	}

	/**
	 * Дата оценки
	 */
	public void setAssesmentDate(Date a_Property) {
		theAssesmentDate = a_Property;
	}

	/**
	 * Дата оценки
	 */
	private Date theAssesmentDate;

	/**
	 * Время оценки
	 */
	@Comment("Время оценки")
	public Time getAssesmentTime() {
		return theAssesmentTime;
	}

	/**
	 * Время оценки
	 */
	public void setAssesmentTime(Time a_Property) {
		theAssesmentTime = a_Property;
	}

	/**
	 * Время оценки
	 */
	private Time theAssesmentTime;

	/**
	 * Кто оценивал
	 */
	@Comment("Кто оценивал")
	@OneToOne
	public WorkFunction getWorker() {
		return theWorker;
	}

	/**
	 * Кто оценивал
	 */
	public void setWorker(WorkFunction a_Property) {
		theWorker = a_Property;
	}

	/**
	 * Кто оценивал
	 */
	private WorkFunction theWorker;
	
//	/**
////	 * Список общих реакций
////	 */
//	@Comment("Список общих реакций")
//	@OneToMany
//	public List<VocVaccinationCommonReaction> getCommonReactionList() {
//		return theCommonReactionList;
//	}
//
//	/**
//	 * Список общих реакций
//	 */
//	public void setCommonReactionList(List<VocVaccinationCommonReaction> a_Property) {
//		theCommonReactionList = a_Property;
//	}
//
//	/**
//	 * Список общих реакций
//	 */
//	private List<VocVaccinationCommonReaction> theCommonReactionList;
	
	/**
	 * Комментарий к общей реакции
	 */
	@Comment("Комментарий к общей реакции")
	public String getCommonReactionComment() {
		return theCommonReactionComment;
	}

	/**
	 * Комментарий к общей реакции
	 */
	public void setCommonReactionComment(String a_Property) {
		theCommonReactionComment = a_Property;
	}

	/**
	 * Комментарий к общей реакции
	 */
	private String theCommonReactionComment;

//	/**
//	 * Список локальных реакций
//	 */
//	@Comment("Список локальных реакций")
//	@OneToMany
//	public List<VocVaccinationLocalReaction> getLocalReactionList() {
//		return theLocalReactionList;
//	}
//
//	/**
//	 * Список локальных реакций
//	 */
//	public void setLocalReactionList(List<VocVaccinationLocalReaction> a_Property) {
//		theLocalReactionList = a_Property;
//	}
//
//	/**
//	 * Список локальных реакций
//	 */
//	private List<VocVaccinationLocalReaction> theLocalReactionList;

	/**
	 * Комментарий к локальной реакции
	 */
	@Comment("Комментарий к локальной реакции")
	public String getLocalReactionComment() {
		return theLocalReactionComment;
	}

	/**
	 * Комментарий к локальной реакции
	 */
	public void setLocalReactionComment(String a_Property) {
		theLocalReactionComment = a_Property;
	}

	/**
	 * Комментарий к локальной реакции
	 */
	private String theLocalReactionComment;

}
