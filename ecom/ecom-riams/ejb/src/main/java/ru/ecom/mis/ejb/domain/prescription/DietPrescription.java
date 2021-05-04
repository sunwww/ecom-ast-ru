package ru.ecom.mis.ejb.domain.prescription;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class DietPrescription extends Prescription{
	
	/** Диета */
	@Comment("Диета")
	@OneToOne
	public Diet getDiet() {
		return diet;
	}

	/** Диета */
	private Diet diet;
	
	/** Диета (текст) */
	@Comment("Диета (текст)")
	@Transient
	public String getDietName() {
		return diet!=null? diet.getName (): "" ;
	}

	/** Описание назначения */
	@Comment("Описание назначения")
	@Transient
	public String getDescriptionInfo() {
		return getDietName() + ",";
	}

	
}
