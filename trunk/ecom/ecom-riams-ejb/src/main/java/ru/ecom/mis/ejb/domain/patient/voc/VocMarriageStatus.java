package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

/**
 * Справочник семейного статуса
 * @author oegorova
 *
 */
@Entity
@Table(schema="SQLUser")
public class VocMarriageStatus extends VocBaseEntity {

}
