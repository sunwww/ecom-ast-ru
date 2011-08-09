package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник типов стула
 * @author oegorova
 *
 */

@Comment("Справочник типов стула")
@Entity
@Table(schema="SQLUser")
public class VocStoolType extends VocBaseEntity {

}
