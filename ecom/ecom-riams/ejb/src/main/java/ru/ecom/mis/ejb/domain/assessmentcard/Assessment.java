package ru.ecom.mis.ejb.domain.assessmentcard;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Assessment extends VocBaseEntity{
	
	/** Минимальное кол-во баллов */
	private Integer minBall;

	/** Максимальное количество баллов */
	private Integer maxBall;

	/** Тип карты оценки */
	private Long assessmentCard;
}
