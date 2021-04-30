package ru.ecom.mis.ejb.domain.medcase.listwatch;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/** 
 * Пациенты, которых надо наблюдать 
 */

@Comment("Пациенты, которых надо наблюдать")
@Entity 
@Table(schema="SQLUser")
@Getter
@Setter
public class PatientWatch extends BaseEntity{
	
	/** СЛС */
	@Comment("СЛС")
	@OneToOne
	public MedCase getMedCase() {return medCase;	}
	private MedCase medCase;

	/** Лист наблюдения */
	@Comment("Лист наблюдения")
	@OneToOne
	public ListWatch getListWatch() {return listWatch;}
	/** Лист наблюдения */
	private ListWatch listWatch;
}