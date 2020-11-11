package ru.ecom.mis.ejb.domain.medcase.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Справочник КТ
 * @author milamesher
 *
 */

@Entity
@Comment("Компьютерная томография")
@Table(schema="SQLUser")
public class VocCT extends VocBaseEntity {
}