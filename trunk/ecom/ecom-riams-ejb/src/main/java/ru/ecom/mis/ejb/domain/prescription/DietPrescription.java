package ru.ecom.mis.ejb.domain.prescription;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.mis.ejb.domain.diet.Diet;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Назначение на диету
 * @author azviagin
 *
 */

@Comment("Назначение на диету")
@Entity
@Table(schema="SQLUser")
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
		StringBuilder sb=new StringBuilder();
		sb.append(getDietName());
		sb.append(",");
		return sb.toString();
	}

	
}
