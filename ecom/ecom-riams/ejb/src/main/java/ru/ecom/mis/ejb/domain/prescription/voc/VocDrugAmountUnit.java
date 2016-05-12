package ru.ecom.mis.ejb.domain.prescription.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник единиц измерения количества лекарства
 * @author azviagin
 *
 */

@Comment("Справочник единиц измерения количества лекарства")
@Entity
@Table(schema="SQLUser")
public class VocDrugAmountUnit extends VocBaseEntity{

}
