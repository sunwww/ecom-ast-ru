package ru.ecom.mis.ejb.domain.workcalendar.voc;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocServiceStream extends VocIdNameOmcCode {
	
	/** Необходимо подтверждение оплаты */
	private Boolean isPaidConfirmation;

	/** Двойная печать выписок по поликлинике */
	private Boolean isDoublePrintPolic;
	/** Источник финансирования */
	private String financeSource;
	
	/** Вычислять договор */
	private Boolean isCalcDogovor;

	@Deprecated
	private String promedCode;

	/** Считать цену по ОМС тарифу */
	private Boolean isCalcOmcHosp ;
}
