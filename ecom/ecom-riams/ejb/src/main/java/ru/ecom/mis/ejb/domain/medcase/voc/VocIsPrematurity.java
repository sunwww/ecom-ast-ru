package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

/**
 * Справочник недоношенности (доношенный-1, недоношенный-2)
 * @author oegorova
 *
 */
@Entity
@Table(schema="SQLUser")
public class VocIsPrematurity extends VocBaseEntity{

}
