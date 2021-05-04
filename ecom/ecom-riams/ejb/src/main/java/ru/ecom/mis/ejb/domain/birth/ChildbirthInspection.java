package ru.ecom.mis.ejb.domain.birth;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class ChildbirthInspection extends Inspection {
	private BigDecimal uterusCervixOpening;

}
