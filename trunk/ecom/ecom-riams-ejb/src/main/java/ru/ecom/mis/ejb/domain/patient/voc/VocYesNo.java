package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Справочник выбора ДА-НЕТ
 */

@Entity
@Table(schema="SQLUser")
public class VocYesNo extends VocIdName {
}
