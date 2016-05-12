package ru.ecom.mis.ejb.domain.workcalendar.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип календарного периода
 * (год, квартал, месяц, неделя)
 * @author azviagin
 *
 */
@Comment("Тип календарного периода")
@Entity
@Table(schema="SQLUser")
public class VocPeriodType extends VocBaseEntity{

}
