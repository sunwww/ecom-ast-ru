package ru.ecom.mis.ejb.domain.workcalendar.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник рабочих недель (5-ти дневная, 6-дневная, 7-дневная)
	 */
	@Comment("Справочник рабочих недель (5-ти дневная, 6-дневная, 7-дневная)")
@Entity
@Table(schema="SQLUser")
public class VocWorkWeek extends VocBaseEntity{
}
