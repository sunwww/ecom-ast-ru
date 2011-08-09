package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник исходов госпитализации
 * 	выписан - 1, в т.ч. в дневной стационар - 2, в круглосуточный стационар - 3, 
 * 	переведен в другой стационар - 4
 */
@Entity
@Comment("Справочник исходов госпитализации")
@Table(schema="SQLUser")
public class VocHospitalizationOutcome extends VocBaseEntity {

}
