package ru.ecom.ejb.services.voc;

import ru.nuzmsh.util.voc.IVocService;

/**
 * Информация о зарегестрированных справочниках 
 *
 */
public interface IVocInfoService {

	
	IVocContextService getVocService(String aVocKey) ;
}
