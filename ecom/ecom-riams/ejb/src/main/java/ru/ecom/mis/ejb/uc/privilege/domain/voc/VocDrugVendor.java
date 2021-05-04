package ru.ecom.mis.ejb.uc.privilege.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник производителей лекарственных средств
 * @author azviagin
 *
 */
@Comment("Справочник производителей лекарственных средств")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocDrugVendor extends VocBaseEntity{
	/** Страна */
	private String country;
}
