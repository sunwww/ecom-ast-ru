package ru.ecom.poly.ejb.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAcuityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPrimaryDiagnosis;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;


@Entity
@Getter
@Setter
public class VocIllnesPrimary extends VocBaseEntity {
	/**
	 * Первичность
	 */
	@Comment("Первичность")
	@OneToOne
	public VocPrimaryDiagnosis getPrimary() {
		return primary;
	}

	/**
	 * Устарел
	 */
	private Boolean deprecated;
	/**
	 * Острота
	 */
	private VocAcuityDiagnosis illnesType;
	/**
	 * Первичность
	 */
	private VocPrimaryDiagnosis primary;
	/**
	 * Код ОМС (справочник V027)
	 */
	private String omcCode;

	/**
	 * Острота
	 */
	@Comment("Острота")
	@OneToOne
	public VocAcuityDiagnosis getIllnesType() {
		return illnesType;
	}


}
