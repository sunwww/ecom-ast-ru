package ru.ecom.mis.ejb.domain.patient;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Телефон персоны
 * @author azviagin
 *
 */
@Comment("Телефон персоны")
@Entity
@Table(schema="SQLUser")
public class PatientPhone extends BaseEntity{
	
	/** Телефон */
	@Comment("Телефон")
	@ManyToOne
	public Phone getPhone() {
		return thePhone;
	}

	public void setPhone(Phone aPhone) {
		thePhone = aPhone;
	}

	/** Телефон */
	private Phone thePhone;

}
