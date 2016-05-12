package ru.ecom.expomc.ejb.domain.omcvoc;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип отделения
 */
@Comment("Тип отделения")
@Entity
@Table(name = "OMC_DEPTYPE",schema="SQLUser")
@NamedQueries(@NamedQuery(name = "OmcDepType.findByTimeAndCode", query = "from OmcDepType where time=:time and code=:code"))
public class OmcDepType extends OmcAbstractVoc {

	/** Среднее количество койко-дней */
	@Comment("Среднее количество койко-дней")
	@AFormatFieldSuggest("SRDSTAC")
	public BigDecimal getAverageDays() {
		return theAverageDays;
	}

	public void setAverageDays(BigDecimal aAverageDays) {
		theAverageDays = aAverageDays;
	}

	/** Среднее количество койко-дней */
	private BigDecimal theAverageDays;
}
