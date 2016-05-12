package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
@Entity
@Comment("Справочник служащих организации")
@Table(schema="SQLUser")
public class VocPhoneMessageEmploye extends VocBaseEntity {
	/** Организация */
	@Comment("Организация")
	@OneToOne
	public VocPhoneMessageOrganization getOrganization() {
		return theOrganization;
	}

	public void setOrganization(VocPhoneMessageOrganization aOrganization) {
		theOrganization = aOrganization;
	}

	/** Организация */
	private VocPhoneMessageOrganization theOrganization;
}
