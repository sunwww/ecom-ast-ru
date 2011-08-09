package ru.ecom.mis.ejb.domain.workcalendar.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник порядка недели в месяце (первая, вторая, третья, четвертая, последняя)
	 */
	@Comment("Справочник порядка недели в месяце (первая, вторая, третья, четвертая, последняя)")
@Entity
@Table(schema="SQLUser")
public class VocWeekMonthOrder extends VocBaseEntity{
}
