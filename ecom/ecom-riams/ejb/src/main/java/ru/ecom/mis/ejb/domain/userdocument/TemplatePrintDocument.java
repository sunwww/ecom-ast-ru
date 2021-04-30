package ru.ecom.mis.ejb.domain.userdocument;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Печатный шаблон протокола (по шаблону протокола)
 * @author user
 *
 */

@Entity
@Getter
@Setter
public class TemplatePrintDocument extends UserDocument {

	/** Шаблон протокола */
	private Long template;
}
