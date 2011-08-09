package ru.ecom.expomc.ejb.services.impdoc;


public interface IImportDocumentService {

	/**
	 * 
	 * @param aDocumentId
	 * @return Количество удаленных
	 */
	int deleteAllValues(long aDocumentId) ;
}
