package ru.ecom.mis.ejb.domain.workcalendar.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Показания к госпитализации (для беременные
 * @author vtsybulin
 *
 */
@Comment("Показания к госпитализации")
@Entity
@Table(schema="SQLUser")
public class VocIndicationHospitalization  extends VocBaseEntity{

}
