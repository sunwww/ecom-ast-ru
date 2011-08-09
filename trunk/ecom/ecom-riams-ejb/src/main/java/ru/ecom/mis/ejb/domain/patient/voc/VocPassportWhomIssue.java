package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Кем выдано
 */
@Entity
@Table(schema="SQLUser")
public class VocPassportWhomIssue extends VocIdName {
}
