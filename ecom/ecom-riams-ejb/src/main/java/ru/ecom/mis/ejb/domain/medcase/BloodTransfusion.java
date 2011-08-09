package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.mis.ejb.domain.medcase.voc.VocBloodPreparation;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBloodGroup;
import ru.ecom.mis.ejb.domain.medcase.voc.VocRhesusFactor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Переливание крови и кровезаменителей
 * @author azviagin
 *
 */

@Comment("Переливание крови и кровезаменителей")
@Entity
@Table(schema="SQLUser")
public class BloodTransfusion extends Transfusion{
	
	/** Препарат крови */
	@Comment("Препарат крови")
	@OneToOne
	public VocBloodPreparation getBloodPreparation() {return theBloodPreparation;}
	public void setBloodPreparation(VocBloodPreparation aBloodPreparation) {theBloodPreparation = aBloodPreparation;}

	/** Группа крови пациента */
	@Comment("Группа крови пациента")
	@OneToOne
	public VocBloodGroup getPatientBloodGroup() {return thePatientBloodGroup;}
	public void setPatientBloodGroup(VocBloodGroup aPatientBloodGroup) {thePatientBloodGroup = aPatientBloodGroup;}
	
	/** Резус-фактор пациента */
	@Comment("Резус-фактор пациента")
	@OneToOne
	public VocRhesusFactor getPatientRhesusFactor() {return thePatientRhesusFactor;}
	public void setPatientRhesusFactor(VocRhesusFactor aPatientRhesusFactor) {thePatientRhesusFactor = aPatientRhesusFactor;}
	
	/** Группа крови препарата */
	@Comment("Группа крови препарата")
	@OneToOne
	public VocBloodGroup getPreparationBloodGroup() {return thePreparationBloodGroup;}
	public void setPreparationBloodGroup(VocBloodGroup aPreparationBloodGroup) {thePreparationBloodGroup = aPreparationBloodGroup;}

	/** Резус-фактор препарата */
	@Comment("Резус-фактор препарата")
	@OneToOne
	public VocRhesusFactor getPreparationRhesusFactor() {return thePreparationRhesusFactor;}
	public void setPreparationRhesusFactor(VocRhesusFactor aPreparationRhesusFactor) {thePreparationRhesusFactor = aPreparationRhesusFactor;}

	/** Донор */
	@Comment("Донор")
	public String getDonor() {return theDonor;}
	public void setDonor(String aDonor) {theDonor = aDonor;}

	@Transient
	public String getInformation() {
		StringBuilder ret = new StringBuilder() ;
		if (theBloodPreparation!=null) {
			ret.append("Препарат крови: ").append(theBloodPreparation.getName()) ;
			if (theBloodPreparation.getBloodPreparationType()!=null) ret.append(" тип ").append(theBloodPreparation.getBloodPreparationType().getName()) ;
		}
		if (thePreparationBloodGroup!=null) ret.append(" группа ").append(thePreparationBloodGroup.getName()) ;
		if (thePreparationRhesusFactor!=null) ret.append(" резус-фактор ").append(thePreparationRhesusFactor.getName()) ;
		return ret.toString() ;
	}
	/** Донор */
	private String theDonor;
	/** Препарат крови */
	private VocBloodPreparation theBloodPreparation;
	/** Группа крови пациента */
	private VocBloodGroup thePatientBloodGroup;
	/** Резус-фактор пациента */
	private VocRhesusFactor thePatientRhesusFactor;
	/** Группа крови препарата */
	private VocBloodGroup thePreparationBloodGroup;
	/** Резус-фактор препарата */
	private VocRhesusFactor thePreparationRhesusFactor;
}
