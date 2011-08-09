package ru.amokb.asset.ejb.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Внешний носитель информации
	 */
	@Comment("Внешний носитель информации")
@Entity
@Table(schema="SQLUser")
public class ExternalDataMedium extends PermanentAsset{
	/**
	 * Емкость в Гб
	 */
	@Comment("Емкость в Гб")
	
	public BigDecimal getCapacity() {
		return theCapacity;
	}
	public void setCapacity(BigDecimal aCapacity) {
		theCapacity = aCapacity;
	}
	/**
	 * Емкость в Гб
	 */
	private BigDecimal theCapacity;
}
