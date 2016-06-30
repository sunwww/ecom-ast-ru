package ru.ecom.mis.ejb.domain.userdocument;

import javax.persistence.Entity;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Печатный шаблон протокола (по шаблону протокола)
 * @author user
 *
 */

@Entity
public class TemplatePrintDocument extends UserDocument {

	/** Шаблон протокола */
	@Comment("Шаблон протокола")
	public Long getTemplate() {return theTemplate;}
	public void setTemplate(Long aTemplate) {theTemplate = aTemplate;}
	/** Шаблон протокола */
	private Long theTemplate;
}
