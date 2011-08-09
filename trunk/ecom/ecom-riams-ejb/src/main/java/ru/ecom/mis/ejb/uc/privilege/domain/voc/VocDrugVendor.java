package ru.ecom.mis.ejb.uc.privilege.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

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
public class VocDrugVendor extends VocBaseEntity{

}
