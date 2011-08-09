package ru.ecom.mis.ejb.uc.privilege.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Метод введения лекарства
 * @author azviagin
 *
 */

@Comment("Метод введения лекарства")
@Entity
@Table(schema="SQLUser")
public class VocDrugMethod extends VocBaseEntity{

}
