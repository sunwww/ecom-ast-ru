package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник препаратов крови
 * @author azviagin
 *
 */

@Comment("Справочник препаратов крови")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocBloodPreparation extends VocBaseEntity{
	
	/** Тип препарата */
	@Comment("Тип препарата")
	@OneToOne
	public VocBloodPreparationType getBloodPreparationType() {
		return bloodPreparationType;
	}

	/** Тип препарата */
	private VocBloodPreparationType bloodPreparationType;

}
