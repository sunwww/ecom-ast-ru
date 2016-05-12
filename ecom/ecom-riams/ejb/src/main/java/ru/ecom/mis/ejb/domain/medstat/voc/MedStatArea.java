package ru.ecom.mis.ejb.domain.medstat.voc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Территория отчета Медстат
 * @author azviagin
 *
 */

@Comment("Территория отчета Медстат")
@Entity
@Table(name="kodif",schema="SQLUser")
@AIndexes({
@AIndex(unique = false, properties="code")
})
public class MedStatArea extends BaseEntity{
	
	/** Код */
	@Comment("Код")
	@Column(name="a1")
	public String getCode() {
		return theCode;
	}

	public void setCode(String aCode) {
		theCode = aCode;
	}

	/** Код */
	private String theCode;
	
	/** Название */
	@Comment("Название")
	@Column(name="a2")
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Название */
	private String theName;
	
	/** Пустое */
	@Comment("Пустое")
	public String getEmpty() {
		return theEmpty;
	}

	public void setEmpty(String aEmpty) {
		theEmpty = aEmpty;
	}

	/** Пустое */
	private String theEmpty;

}
