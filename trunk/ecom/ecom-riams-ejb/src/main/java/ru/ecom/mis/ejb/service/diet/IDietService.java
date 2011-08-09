package ru.ecom.mis.ejb.service.diet;

import java.sql.Date;

/**
 * Интерфейс сервиса для работы с шаблоном диет питания
 * @author STkacheva
 */
public interface IDietService {
	/**
	 * Добавить все блюда в существующие меню
	 * @param aIdTemplateMenu - ИД шаблона меню
	 * @param aIdParent - ИД текущего меню
	 * @return true - при успешном сохранении, false - при ошибке при сохранении
	 */
	public boolean saveInExistsMenu(Long aIdTemplateMenu,Long aIdParent) ;
	
	/**
	 * Добавить все блюда в новое меню-заказ
	 * @param aIdTemplateMenu - ИД шаблона меню
	 * @param aIdParent - ИД диеты
	 * @param aDateFrom - дата меню-раскладки
	 * @param aLpu - отделение
	 * @param aPortionAmount - кол-во порций
	 * @return true - при успешном сохранении, false - при ошибке при сохранении
	 */
	public boolean saveInNewMenu(Long aIdTemplateMenu, Date aDateFrom, Long aLpu
			, Integer aPortionAmount) ;
	
	/**
	 * Добавить все блюда в новый шаблон меню-заказа
	 * @param aIdTemplateMenu - ИД шаблона меню
	 * @param aIdParent - ИД диеты
	 * @return true - при успешном сохранении, false - при ошибке при сохранении
	 */
	public boolean saveInNewTemplateMenu(Long aIdTemplateMenu, Long aIdDiet, Long aIdServiceStream
			, Long aIdWeekDay, Date aDateFrom, Date aDateTo) ;

	/**
	 * Получить описание шаблона меню-заказа
	 * @param aIdTemplateMenu - ИД листа назначения
	 * @return описание листа назначений
	 */
	public String getDescriptionMenu(Long aIdTemplateMenu) ;
	/**
	 * Получить описание шаблона зависимого меню-заказа
	 * @param aIdTemplateMenu - ИД листа назначения
	 * @return описание листа назначений
	 */
	public String getDescriptionChildMenu(Long aIdTemplateMenu) ;
}
