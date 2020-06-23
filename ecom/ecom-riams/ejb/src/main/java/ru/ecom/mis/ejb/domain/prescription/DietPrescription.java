package ru.ecom.mis.ejb.domain.prescription;

import ru.ecom.mis.ejb.domain.diet.Diet;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Назначение на диету
 * @author azviagin
 *
 */

@Comment("Назначение на диету")
@Entity
public class DietPrescription extends Prescription{
	
	/** Диета */
	@Comment("Диета")
	@OneToOne
	public Diet getDiet() {
		return theDiet;
	}

	public void setDiet(Diet aDiet) {
		theDiet = aDiet;
	}

	/** Диета */
	private Diet theDiet;
	
	/** Диета (текст) */
	@Comment("Диета (текст)")
	@Transient
	public String getDietName() {
		return theDiet!=null? theDiet.getName (): "" ;
	}

	/** Описание назначения */
	@Comment("Описание назначения")
	@Transient
	public String getDescriptionInfo() {
		return getDietName() + ",";
	}

	
}
