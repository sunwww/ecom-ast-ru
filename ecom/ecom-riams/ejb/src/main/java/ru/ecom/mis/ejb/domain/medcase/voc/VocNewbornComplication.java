package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

/**
 * Справочник осложнений новорожденного: 01-Анемия, 02-Фатальный алкогольный синдром, 
 * 03-Синдром мекониевой пробки, 04-Легочная вентиляция до 30 минут, 
 * 05-Легочная вентиляция свыше 30 минут, 06-Другие, 00-Нет осложнений.
 * @author oegorova
 *
 */

@Entity
@Table(schema="SQLUser")
public class VocNewbornComplication extends VocBaseEntity {

}
