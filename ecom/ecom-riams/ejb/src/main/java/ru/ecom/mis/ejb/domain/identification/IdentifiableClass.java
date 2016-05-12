package ru.ecom.mis.ejb.domain.identification;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**Идентифицируемый класс
 * 
 * @author azviagin
 *
 */

@Comment("Идентифицируемый класс")
@Entity
@Table(schema="SQLUser")
public class IdentifiableClass extends BaseEntity{
	
	/** Правила идентификации */
	@Comment("Правила идентификации")
	@OneToMany(mappedBy="identifiableClass", cascade=CascadeType.ALL)
	public List<IdentificationRule> getIdentificationRules() {
		return theIdentificationRules;
	}

	public void setIdentificationRules(List<IdentificationRule> aIdentificationRules) {
		theIdentificationRules = aIdentificationRules;
	}

	/** Правила идентификации */
	private List<IdentificationRule> theIdentificationRules;
	
	/** Идентифицируемые аттрибуты */
	@Comment("Идентифицируемые аттрибуты")
	@OneToMany(mappedBy="identifiableClass", cascade=CascadeType.ALL)
	public List<IdentifiableAttribute> getIdentifiableAttributes() {
		return theIdentifiableAttributes;
	}

	public void setIdentifiableAttributes(List<IdentifiableAttribute> aIdentifiableAttributes) {
		theIdentifiableAttributes = aIdentifiableAttributes;
	}

	/** Идентифицируемые аттрибуты */
	private List<IdentifiableAttribute> theIdentifiableAttributes;

	/** Название класса */
	@Comment("Название класса")
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Название класса */
	private String theName;
}
