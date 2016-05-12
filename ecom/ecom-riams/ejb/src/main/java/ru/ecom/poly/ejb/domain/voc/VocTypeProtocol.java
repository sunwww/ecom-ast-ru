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
	/** Печать подписи зав. отделения */
	@Comment("Печать подписи зав. отделения")
	public Boolean getIsPrintAdministrator() {return theIsPrintAdministrator;}
	public void setIsPrintAdministrator(Boolean aIsPrintAdministrator) {theIsPrintAdministrator = aIsPrintAdministrator;}
	
	/** Печать заголовка */
	@Comment("Печать заголовка")
	public Boolean getIsPrintTitle() {
		return theIsPrintTitle;
	}

	public void setIsPrintTitle(Boolean aIsPrintTitle) {
		theIsPrintTitle = aIsPrintTitle;
	}

	/** Печать заголовка */
	private Boolean theIsPrintTitle;
	/** Печать подписи зав. отделения */
	private Boolean theIsPrintAdministrator;
	
	/** Префикс к файлу */
	@Comment("Префикс к файлу")
	public String getPrefixPrint() {return thePrefixPrint;}
	public void setPrefixPrint(String aPrefixPrint) {thePrefixPrint = aPrefixPrint;}

	/** Префикс к файлу */
	private String thePrefixPrint;
}
