package ru.ecom.ejb.services.entityform;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Форма с ид Long
 */
public interface IEntityForm {
    /** Порядковый номер */
	@Comment("Порядковый номер")
	public Integer getSn();
	public void setSn(Integer aSn);
}
