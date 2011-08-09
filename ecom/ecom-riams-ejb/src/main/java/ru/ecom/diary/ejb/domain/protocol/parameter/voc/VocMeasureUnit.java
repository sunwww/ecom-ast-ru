package ru.ecom.diary.ejb.domain.protocol.parameter.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

/**
 * Справочник единиц измерения
 * @author stkacheva
 *
 */
@Entity
@Table(schema="SQLUser")
public class VocMeasureUnit extends VocBaseEntity {

}
