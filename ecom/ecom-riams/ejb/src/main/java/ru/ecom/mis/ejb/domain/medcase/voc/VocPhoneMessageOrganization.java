package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник организаций куда происходят звонки по телеф. сообщениям")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocPhoneMessageOrganization extends VocBaseEntity {
	/** Телефон */
	private String phoneNumber;
}
