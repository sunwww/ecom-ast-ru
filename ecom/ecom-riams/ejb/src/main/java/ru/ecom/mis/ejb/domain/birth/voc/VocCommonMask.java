package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


// Общие оценки
@Entity
@Table(schema="SQLUser")
public class VocCommonMask extends VocBaseEntity{
	
	/** Балл */
	@Comment("Балл")
	public Integer getMinBall() {return theMinBall;}
	public void setMinBall(Integer aBall) {theMinBall = aBall;}

	/** Балл */
	@Comment("Балл")
	public Integer getMaxBall() {return theMaxBall;}
	public void setMaxBall(Integer aBall) {theMaxBall = aBall;}

	/** Балл */
	private Integer theMaxBall;
	/** Балл */
	private Integer theMinBall;

}