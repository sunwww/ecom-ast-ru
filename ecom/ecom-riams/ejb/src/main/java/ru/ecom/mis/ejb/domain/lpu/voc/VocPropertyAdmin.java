package ru.ecom.mis.ejb.domain.lpu.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Вид собственности
 */
@Entity
@Comment("Вид собственности")
@Table(schema="SQLUser")
public class VocPropertyAdmin extends VocBaseEntity{
}
