package ru.ecom.diary.ejb.domain.protocol.parameter.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник единиц измерения
 * @author stkacheva
 *
 */
@Entity
@Table(schema="SQLUser")
@Comment("Справочник единиц измерения")
public class VocMeasureUnit extends VocBaseEntity {

}
