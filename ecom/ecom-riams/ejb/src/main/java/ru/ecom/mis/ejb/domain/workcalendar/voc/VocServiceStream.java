package ru.ecom.mis.ejb.domain.workcalendar.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.contract.voc.VocFinanceSource;
import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Поток обслуживания
 * @author azviagin
 *
 */

@Comment("Поток обслуживания")
@Entity
@Table(schema="SQLUser")
public class VocServiceStream extends VocIdNameOmcCode {
	
	/** Необходимо подтверждение оплаты */
	@Comment("Необходимо подтверждение оплаты")
	public Boolean getIsPaidConfirmation() {return theIsPaidConfirmation;}
	public void setIsPaidConfirmation(Boolean aIsPaidConfirmation) {theIsPaidConfirmation = aIsPaidConfirmation;}
	/** Необходимо подтверждение оплаты */
	private Boolean theIsPaidConfirmation;

	/** Источник финансирования */
	@Comment("Источник финансирования")
	public String getFinanceSource() {return theFinanceSource;}
	public void setFinanceSource(String aFinanceSource) {theFinanceSource = aFinanceSource;}

	/** Двойная печать выписок по поликлинике */
	@Comment("Двойная печать выписок по поликлинике")
	public Boolean getIsDoublePrintPolic() {return theIsDoublePrintPolic;}
	public void setIsDoublePrintPolic(Boolean aIsDoublePrintPolic) {theIsDoublePrintPolic = aIsDoublePrintPolic;}

	/** Двойная печать выписок по поликлинике */
	private Boolean theIsDoublePrintPolic;
	/** Источник финансирования */
	private String theFinanceSource;
	
	/** Вычислять договор */
	@Comment("Вычислять договор")
	public Boolean getIsCalcDogovor() {return theIsCalcDogovor;}
	public void setIsCalcDogovor(Boolean aIsCalcDogovor) {theIsCalcDogovor = aIsCalcDogovor;}

	/** Вычислять договор */
	private Boolean theIsCalcDogovor;
}
