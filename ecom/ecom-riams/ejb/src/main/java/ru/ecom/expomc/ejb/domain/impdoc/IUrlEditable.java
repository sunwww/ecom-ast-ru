package ru.ecom.expomc.ejb.domain.impdoc;

/**
 * Редактировать исходные данных по URL
 */
public interface IUrlEditable {
	/** URL для редактирования */
	public String getUrlEdit() ;
	public void setUrlEdit(String aUrlEdit) ;
}
