package ru.ecom.mis.ejb.domain.workcalendar.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * День недели
 * @author azviagin
 *
 */
@Comment("День недели")
@Entity
@Table(schema="SQLUser")
public class VocWeekDay extends VocBaseEntity{

}
