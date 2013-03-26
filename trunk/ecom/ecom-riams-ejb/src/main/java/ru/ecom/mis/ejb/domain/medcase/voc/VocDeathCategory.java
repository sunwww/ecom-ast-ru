package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
 * Справочник категорий по случаю смерти
 */
@Entity
@Comment("Справочник категорий по случаю смерти")
@Table(schema="SQLUser")
public class VocDeathCategory extends VocBaseEntity {

}
