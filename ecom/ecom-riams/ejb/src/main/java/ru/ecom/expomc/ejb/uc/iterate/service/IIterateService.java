package ru.ecom.expomc.ejb.uc.iterate.service;

import ru.ecom.ejb.services.monitor.MonitorId;

/**
 * Сервис для перебора
 */
public interface IIterateService {

	/**
	 * Запуск перебора
	 * @param aMonitorId монитор
	 * @param aIterateId идентификатор перебора
	 */
	public void executeIterate(MonitorId aMonitorId, long aIterateId) ;

}
