package ru.ecom.mis.ejb.service.vocabulary;

import java.util.Collection;

/**
 * Интерфейс сервиса для работы со справочниками
 * @author stkacheva
 */
public interface IVocabularyService {
	public Collection<VocEntityInfo> listVocEntities();
	public int getCount(String clazz) ;
}
