package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

/**
 * Справочник акушерских процедур: 01-Амниоцентез, 02-Мониторинг плода, 03-Стимуляция плода, 
 * 04-Ультрасонография, 05-Другие, 00-Нет осложнений
 * @author oegorova
 *
 */
@Entity
@Table(schema="SQLUser")
public class VocObstetricProcedure extends VocBaseEntity {

}
