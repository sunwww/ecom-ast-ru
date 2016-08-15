package ru.ecom.mis.ejb.domain.assessmentcard;

import javax.persistence.Entity;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
public class Assessment extends VocBaseEntity{
	/** Минимальное кол-во баллов */
	@Comment("Минимальное кол-во баллов")
	public Integer getMinBall() {return theMinBall;}
	public void setMinBall(Integer aMinBall) {theMinBall = aMinBall;}
	/** Минимальное кол-во баллов */
	private Integer theMinBall;

	/** Максимальное количество баллов */
	@Comment("Максимальное количество баллов")
	public Integer getMaxBall() {return theMaxBall;}
	public void setMaxBall(Integer aMaxBall) {theMaxBall = aMaxBall;}
	/** Максимальное количество баллов */
	private Integer theMaxBall;

	/** Тип карты оценки */
	@Comment("Тип карты оценки")
	public Long getAssessmentCard() {return theAssessmentCard;}
	public void setAssessmentCard(Long aAssessmentCard) {theAssessmentCard = aAssessmentCard;}
	/** Тип карты оценки */
	private Long theAssessmentCard;
}
