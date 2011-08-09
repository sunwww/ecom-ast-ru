package ru.ecom.mis.ejb.domain.workcalendar.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип рабочего календаря
 * (постоянный, временный и т.д.)
 * @author azviagin
 *
 */
@Comment("Тип рабочего календаря")
@Entity
@Table(schema="SQLUser")
public class VocWorkCalendarType extends VocBaseEntity{

}
