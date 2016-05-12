package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.MedPolicy;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


@Entity
@Table(name="MedCase_MedPolicy",schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "medCase" }) })
public class MedCaseMedPolicy extends BaseEntity{
	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@ManyToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	
	/** Медицинский полис */
	@Comment("Медицинский полис")
	@OneToOne
	public MedPolicy getPolicies() {
		return thePolicies;
	}

	public void setPolicies(MedPolicy aMedPolicy) {
		thePolicies = aMedPolicy;
	}

	/** Медицинский полис */
	private MedPolicy thePolicies;
	/** Случай медицинского обслуживания */
	private MedCase theMedCase;
}
