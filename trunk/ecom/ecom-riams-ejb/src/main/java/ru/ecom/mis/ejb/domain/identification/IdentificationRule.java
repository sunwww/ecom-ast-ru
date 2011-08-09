package ru.ecom.mis.ejb.domain.identification;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.identification.voc.VocIdentificationRuleType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Правило идентификации
 * @author azviagin
 *  
 *
 */

@Comment("Правило идентификации")
@Entity
@Table(schema="SQLUser")
public class IdentificationRule extends BaseEntity{
	
	/** Уровень неоднозначности
	 * (в случае количественного правила содержит количество неоднозначных аттрибутов,
	 * достаточных для идентификации;
	 * в случае суммового правила содержит сумму однозначности идентифицированных аттрибутов,
	 * ниже которой идентификация отрицательна) 
	 * */
	@Comment("Уровень неоднозначности")
	public int getAmbiguityLevel() {
		return theAmbiguityLevel;
	}

	public void setAmbiguityLevel(int aAmbiguityLevel) {
		theAmbiguityLevel = aAmbiguityLevel;
	}

	/** Уровень неоднозначности */
	private int theAmbiguityLevel;
	
	/** Уровень однозначности 
	 * (в случае количественного правила содержит количество однозначных аттрибутов,
	 * достаточных для идентификации;
	 * в случае суммового правила содержит сумму однозначности идентифицированных аттрибутов,
	 * ниже которой идентификация сомнительна)
	 * */
	@Comment("Уровень однозначности")
	public int getUnambiguityLevel() {
		return theUnambiguityLevel;
	}

	public void setUnambiguityLevel(int aUnambiguityLevel) {
		theUnambiguityLevel = aUnambiguityLevel;
	}

	/** Уровень однозначности */
	private int theUnambiguityLevel;
	
	/** Тип правила идентификации */
	@Comment("Тип правила идентификации")
	@OneToOne
	public VocIdentificationRuleType getIdentificationRuleType() {
		return theIdentificationRuleType;
	}

	public void setIdentificationRuleType(VocIdentificationRuleType aIdentificationRuleType) {
		theIdentificationRuleType = aIdentificationRuleType;
	}

	/** Тип правила идентификации */
	private VocIdentificationRuleType theIdentificationRuleType;
	
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
