package ru.ecom.expomc.ejb.uc.filltime.service;

import java.util.List;

import ru.ecom.ejb.services.monitor.MonitorId;

/**
 * Сервис для работы с заполнениями 
 */
public interface IFillTimeService {

	/**
	 * Запуск заполнения
	 * @param aFillTime идентификатор заполнения
	 * @return индентификатор ImportTime {@link #ru.ecom.expomc.ejb.domain.impdoc.ImportTime}
	 */
	long fill(long aFillTime, MonitorId aMonitorId);

	/**
	 * Пробный запуск заполнения
	 * @param aFillTime идентификатор заполнения
	 * @param aProperty свойство
	 * @param aCode     код преобразования
	 * @return результат
	 */
	String testInvoke(long aFillTime, String aProperty, String aCode) ;
	
	/**
	 * Вывод свойств по формату
	 * @param aFillTime заполнение
	 * @return свойства как в формате
	 */
	List<PropertyByFieldRow> listByFormat(long aFillTime) ;
	
}
