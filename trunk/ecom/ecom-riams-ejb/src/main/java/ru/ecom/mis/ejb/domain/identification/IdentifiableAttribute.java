package ru.ecom.mis.ejb.domain.identification;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Идентифицируемый аттрибут
 * @author azviagin
 *
 */

@Comment("Идентифицируемый аттрибут")
@Entity
@Table(schema="SQLUser")
public class IdentifiableAttribute extends BaseEntity{
	
	/** Однозначность */
	@Comment("Однозначность")
	public int getUnambiguity() {
		return theUnambiguity;
	}

	public void setUnambiguity(int aUnambiguity) {
		theUnambiguity = aUnambiguity;
	}

	/** Однозначность */
	private int theUnambiguity;
	
	/** Название атрибута */
	@Comment("Название атрибута")
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Название атрибута */
	private String theName;
	
	/** Идентифицируемый класс */
	@Comment("Идентифицируемый класс")
	@ManyToOne
	public IdentifiableClass getIdentifiableClass() {
		return theIdentifiableClass;
	}

	public void setIdentifiableClass(IdentifiableClass aIdentifiableClass) {
		theIdentifiableClass = aIdentifiableClass;
	}

	/** Идентифицируемый класс */
	private IdentifiableClass theIdentifiableClass;

}
