package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class HospitalBed extends HospitalRoom {
	/** Дополнительные */
	@Comment("Дополнительные")
	public Boolean getIsAddition() {return theIsAddition;}
	public void setIsAddition(Boolean aIsAddition) {theIsAddition = aIsAddition;}

	/** Дополнительные */
	private Boolean theIsAddition;

}
