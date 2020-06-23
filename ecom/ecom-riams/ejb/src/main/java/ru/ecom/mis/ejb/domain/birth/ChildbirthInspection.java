package ru.ecom.mis.ejb.domain.birth;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * Осмотр роженицы
 * @author azviagin
 *
 */

@Comment("Осмотр роженицы")
@Entity
public class ChildbirthInspection extends Inspection {
	
	/** Открытие шейки матки (см) */
	@Comment("Открытие шейки матки (см)")
	public BigDecimal getUterusCervixOpening() {
		return theUterusCervixOpening;
	}
	public void setUterusCervixOpening(BigDecimal aUterusCervixOpening) {
		theUterusCervixOpening = aUterusCervixOpening;
	}
	private BigDecimal theUterusCervixOpening;

}
