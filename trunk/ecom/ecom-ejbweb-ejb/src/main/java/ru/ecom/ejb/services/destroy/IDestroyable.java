package ru.ecom.ejb.services.destroy;

/**
 * Поддержка завершения работы.
 * Очистка памяти
 */
public interface IDestroyable {

	void destroy(DestroyContext aContext) ;
}
