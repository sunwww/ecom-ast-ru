package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
@Entity
@Comment("Справочник служащих организации")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocPhoneMessageEmploye extends VocBaseEntity {
	/** Организация */
	@Comment("Организация")
	@OneToOne
	public VocPhoneMessageOrganization getOrganization() {
		return organization;
	}

	/** Организация */
	private VocPhoneMessageOrganization organization;
}
