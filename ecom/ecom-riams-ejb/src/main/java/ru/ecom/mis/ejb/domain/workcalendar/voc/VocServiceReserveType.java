package ru.ecom.mis.ejb.domain.workcalendar.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип резерва обслуживания
 * @author azviagin
 *
 */
@Comment("Тип резерва обслуживания")
@Entity
@Table(schema="SQLUser")
public class VocServiceReserveType extends VocBaseEntity{

}
