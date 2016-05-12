package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Справочник пола
 */
@Entity
@Table(schema="SQLUser")
public class VocSex extends VocIdNameOmcCode {
}
