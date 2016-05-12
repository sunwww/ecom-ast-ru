package ru.ecom.mis.ejb.domain.birth;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.document.ejb.domain.certificate.ConfinementCertificate;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * История родов
 * @author stkacheva
 *
 */
@Comment("История родов")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "pregnancy" }) 
	}
)
public class PregnancyHistory extends BaseEntity {
	/** Беременность */
	@Comment("Беременность")
	@OneToOne
	public Pregnancy getPregnancy() {return thePregnancy;}
	public void setPregnancy(Pregnancy aPregnancy) {thePregnancy = aPregnancy;}

	/** Беременность */
	private Pregnancy thePregnancy;
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	@Transient
	public ConfinementCertificate getConfinementCertificate() {
		return thePregnancy!=null ? 
				thePregnancy.getConfinementCertificate():null;
	}
	/** СМО */
	private MedCase theMedCase;


}
