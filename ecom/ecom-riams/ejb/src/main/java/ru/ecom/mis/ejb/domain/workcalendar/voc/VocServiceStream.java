package ru.ecom.mis.ejb.domain.workcalendar.voc;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

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

	private String promedCode;
	@Comment("Код в промеде")
	@Deprecated
	public String getPromedCode() {
		return promedCode;
	}
	public void setPromedCode(String promedCode) {
		this.promedCode = promedCode;
	}

	/** Считать цену стационара по ОМС тарифу */
	@Comment("Считать цену стационара по ОМС тарифу")
	//@Column(nullable=false, columnDefinition="boolean default true")
	public Boolean getIsCalcOmcHosp() {return theIsCalcOmcHosp;}
	public void setIsCalcOmcHosp(Boolean aIsCalcOmcHosp) {theIsCalcOmcHosp = aIsCalcOmcHosp;}
	/** Считать цену по ОМС тарифу */
	private Boolean theIsCalcOmcHosp ;
}
