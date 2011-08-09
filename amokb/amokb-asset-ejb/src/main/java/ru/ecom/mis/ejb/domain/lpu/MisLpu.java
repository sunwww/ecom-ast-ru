package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
public class MisLpu extends BaseEntity {
	/** Название */
	@Comment("Название")
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}
	
	/** Родитель */
	@Comment("Родитель")
	@OneToOne
	public MisLpu getParent() {
		return theParent;
	}

	public void setParent(MisLpu aParent) {
		theParent = aParent;
	}

	/** Родитель */
	private MisLpu theParent;
	/** Название */
	private String theName;

}
