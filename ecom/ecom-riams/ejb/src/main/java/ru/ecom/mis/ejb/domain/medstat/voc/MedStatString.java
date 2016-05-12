package ru.ecom.mis.ejb.domain.medstat.voc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Строка отчета Медстат
 * @author azviagin
 *
 */

@Comment("Строка отчета Медстат")
@Entity
@Table(name="str",schema="SQLUser")
@AIndexes({
@AIndex(unique = false, properties="code")
})
public class MedStatString extends BaseEntity{
	
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
	
	/** Код года */
	@Comment("Код года")
	@Column(name="gt")
	public Integer getYearCode() {
		return theYearCode;
	}

	public void setYearCode(Integer aYearCode) {
		theYearCode = aYearCode;
	}

	/** Код года */
	private Integer theYearCode;
	
	/** Статистический период */
	@Comment("Статистический период")
	@OneToOne
	@JoinColumn(name="stat_period_id")
	public MedStatPeriod getStatPeriod() {
		return theStatPeriod;
	}

	public void setStatPeriod(MedStatPeriod aStatPeriod) {
		theStatPeriod = aStatPeriod;
	}

	/** Статистический период */
	private MedStatPeriod theStatPeriod; 
	

}
