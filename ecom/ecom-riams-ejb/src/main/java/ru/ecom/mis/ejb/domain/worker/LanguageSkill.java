package ru.ecom.mis.ejb.domain.worker;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.voc.VocLanguage;
import ru.ecom.mis.ejb.domain.worker.voc.VocLanguageSkillLevel;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Знание языка
 * @author azviagin
 *
 */
@Comment("Знание языка")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties={"person"})
})
public class LanguageSkill extends BaseEntity{
	
	/** Язык */
	@Comment("Язык")
	@OneToOne
	public VocLanguage getLanguage() {
		return theLanguage;
	}

	public void setLanguage(VocLanguage aLanguage) {
		theLanguage = aLanguage;
	}

	/** Язык */
	private VocLanguage theLanguage;
	
	/** Уровень знания */
	@Comment("Уровень знания")
	@OneToOne
	public VocLanguageSkillLevel getSkillLevel() {
		return theSkillLevel;
	}

	public void setSkillLevel(VocLanguageSkillLevel aSkillLevel) {
		theSkillLevel = aSkillLevel;
	}

	/** Уровень знания */
	private VocLanguageSkillLevel theSkillLevel;
	
	/** Персона */
	@Comment("Персона")
	@ManyToOne
	public Patient getPerson() {
		return thePerson;
	}

	public void setPerson(Patient aPerson) {
		thePerson = aPerson;
	}

	/** Персона */
	private Patient thePerson;
	
	/** Язык (текст) */
	@Comment("Язык (текст)")
	@Transient
	public String getLanguageText() {
		return theLanguage!=null?theLanguage.getName():"";
	}

}
