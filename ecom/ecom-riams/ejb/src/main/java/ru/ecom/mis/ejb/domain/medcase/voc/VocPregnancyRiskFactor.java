package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

/**
 * Справочник прочих факторов риска во время беременности: 01-Курение, 02-Употребление алкоголя
 * @author oegorova
 *
 */

@Entity
@Table(schema="SQLUser")
public class VocPregnancyRiskFactor extends VocBaseEntity {

}
