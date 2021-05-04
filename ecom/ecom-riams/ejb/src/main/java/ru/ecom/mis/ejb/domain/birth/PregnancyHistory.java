package ru.ecom.mis.ejb.domain.birth;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class PregnancyHistory extends BaseEntity {
	/** Беременность */
	@Comment("Беременность")
	@OneToOne
	public Pregnancy getPregnancy() {return pregnancy;}

	/** Беременность */
	private Pregnancy pregnancy;
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return medCase;}

	@Transient
	public ConfinementCertificate getConfinementCertificate() {
		return pregnancy!=null ? 
				pregnancy.getConfinementCertificate():null;
	}
	/** СМО */
	private MedCase medCase;


}
