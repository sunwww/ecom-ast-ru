package ru.ecom.poly.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип протокола
 * @author stkacheva
 * 1 - анамнез заболеваня
 * 2 - консультация
 * 3 - жалобы
 * 4 - рекомендации
 * 5 -ЭРЛ
 */
@Table(schema="SQLUser")
@Entity
@Comment("Тип протокола")
public class VocTypeProtocol extends VocBaseEntity {

}
