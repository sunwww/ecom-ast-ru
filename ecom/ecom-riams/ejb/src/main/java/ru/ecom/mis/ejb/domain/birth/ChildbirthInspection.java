package ru.ecom.mis.ejb.domain.birth;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Осмотр роженицы
 * @author azviagin
 *
 */

@Comment("Осмотр роженицы")
@Entity
@Table(schema="SQLUser")
public class ChildbirthInspection extends Inspection{
	
	/** Открытие шейки матки (см) */
	@Comment("Открытие шейки матки (см)")
	public BigDecimal getUterusCervixOpening() {
		return theUterusCervixOpening;
	}

	public void setUterusCervixOpening(BigDecimal aUterusCervixOpening) {
		theUterusCervixOpening = aUterusCervixOpening;
	}

	/** Открытие шейки матки (см) */
	private BigDecimal theUterusCervixOpening;

}
