package ru.ecom.poly.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocTypeProtocol extends VocBaseEntity {
	/** Печать заголовка */
	private Boolean isPrintTitle;
	/** Печать подписи зав. отделения */
	private Boolean isPrintAdministrator;
	/** Префикс к файлу */
	private String prefixPrint;
	/** Для роддома */
	private Boolean isForMaternity;
}
