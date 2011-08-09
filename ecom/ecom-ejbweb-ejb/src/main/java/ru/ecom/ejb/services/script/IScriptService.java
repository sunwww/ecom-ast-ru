package ru.ecom.ejb.services.script;

/**
 * Запуск скриптов как Stateless Service
 */
public interface IScriptService {

	/**
	 * Запуск метода сервиса на скриптовом языке
	 * @param aServiceName название сервиса
	 * @param aMethodName  название метода сервиса
	 * @param args         аргументы
	 * @return возвращение значения
	 */
	Object invoke(String aServiceName, String aMethodName, Object[] args);
}
