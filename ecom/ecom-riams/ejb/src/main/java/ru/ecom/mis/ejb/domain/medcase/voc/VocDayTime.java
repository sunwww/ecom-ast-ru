package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * справочник времени суток
 * @author oegorova
 *
 */

@Comment("Справочник времени суток")
@Entity
@Table(schema="SQLUser")
public class VocDayTime  extends VocBaseEntity{

}
