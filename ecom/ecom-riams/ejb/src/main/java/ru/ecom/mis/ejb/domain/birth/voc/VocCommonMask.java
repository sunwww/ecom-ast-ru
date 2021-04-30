package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


// Общие оценки
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocCommonMask extends VocBaseEntity{
	/** Балл */
	private Integer maxBall;
	/** Балл */
	private Integer minBall;

}