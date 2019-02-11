package ru.ecom.mis.ejb.domain.medcase.listwatch;

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
public class PatientWatch extends BaseEntity{
	
	/** СЛС */
	@Comment("СЛС")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;	}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
	private MedCase theMedCase; 

	/** Лист наблюдения */
	@Comment("Лист наблюдения")
	@OneToOne
	public ListWatch getListWatch() {return thegetListWatch;}
	public void setListWatch(ListWatch aListWatch) {thegetListWatch = aListWatch;}
	/** Лист наблюдения */
	private ListWatch thegetListWatch; 
}