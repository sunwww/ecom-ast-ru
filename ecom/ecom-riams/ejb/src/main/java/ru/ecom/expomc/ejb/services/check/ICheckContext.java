package ru.ecom.expomc.ejb.services.check;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.NoResultException;

import ru.ecom.expomc.ejb.domain.format.Format;

/**
 * Параметры для проверки
 */
public interface ICheckContext {

    /** Дата актуальности данных */
    Date getActualDate() ;

    /**
     * Получить значение
     * @param aFieldName название поля
     * @return значение
     */
    Object     get             (String aFieldName) ;
    String     getString       (String aFieldName) ;
    BigDecimal getBigDecimal   (String aFieldName) ;
    Date       getDate         (String aFieldName) ;

    /**
     * Получение формата
     */
    Format getFormat() ;

    /**
     * Поиск по домену
     * @param aDocument идентификатор документа
     * @param aDocumentCodeProperty свойство
     * @param aValue значение для поиска
     * @return что нашлось, null - если не найдено
     */
    Object findDomain(long aDocument, String aDocumentCodeProperty, Object aValue) throws CheckException, NoResultException;

    /**
     * Получение объекта записи
     * @return объект
     */
    Object getEntry();

    ICheckLog getLog() ;

    Object findActual(Class aClass, String aProperty, Object aValue);
}
