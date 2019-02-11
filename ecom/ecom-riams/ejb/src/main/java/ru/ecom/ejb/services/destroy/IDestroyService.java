package ru.ecom.ejb.services.destroy;

/**
 * Завершение работы
 */
public interface IDestroyService {

	/**
	 * Добавить слушателя
	 */
	void add(IDestroyable aDestroyable) ;
}
