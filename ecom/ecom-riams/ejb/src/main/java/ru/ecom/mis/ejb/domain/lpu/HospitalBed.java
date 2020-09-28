package ru.ecom.mis.ejb.domain.lpu;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
public class HospitalBed extends HospitalRoom {
	/** Дополнительные */
	@Comment("Дополнительные")
	public Boolean getIsAddition() {return theIsAddition;}
	public void setIsAddition(Boolean aIsAddition) {theIsAddition = aIsAddition;}

	/** Дополнительные */
	private Boolean theIsAddition;

}
