package ru.ecom.jaas.ejb.service;

public interface ISoftConfigService {
	void addConfigDefaults()  ;
	void saveContextHelp(String aUrl,String aContext) ;
	String getContextHelp(String aUrl) ;

	/**
	 * Получить значение системной настройки
	 * @param key ключ
	 * @return значение настройки
	 */
	String getConfigValue(String key) ;
}
