package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Справочник по группам инвалидности (I, II, III)
 */
@Entity
@Table(schema="SQLUser")
public class VocInvalidGroup  extends VocIdName {
}
