package ru.ecom.mis.ejb.service.vocabulary;

import java.util.Collection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import ru.ecom.ejb.services.query.WebQueryResult;

/**
 * Интерфейс сервиса для работы со справочниками
 * @author stkacheva
 */
public interface IVocabularyService {
	public Collection<VocEntityInfo> listVocEntities();
	public int getCount(String clazz) ;
	public String exportVocExtDisp(long[] aVocExpDisps) throws TransformerException, ParserConfigurationException ;
	public void importVocExtDisp(long aMonitorId,boolean aClear, List<WebQueryResult> aService, List<WebQueryResult> aRisks, List<WebQueryResult> aExtDisps) ; 
}
