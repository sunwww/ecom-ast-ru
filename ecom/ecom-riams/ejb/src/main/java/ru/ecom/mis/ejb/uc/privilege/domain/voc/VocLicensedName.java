package ru.ecom.mis.ejb.uc.privilege.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник патентованных наименований лекарственных средств
 * @author azviagin
 *
 */
@Comment("Справочник патентованных наименований лекарственных средств")
@Entity
@Table(schema="SQLUser")
public class VocLicensedName extends VocBaseEntity{

}
