package ru.ecom.expomc.ejb.services.timeservice;

import java.sql.Date;

/**
 * Работа со справочниками по времени
 */
public interface IImportTimeService {
    /**
     * Поиск по коду
     * @param aClass класс документа
     * @param aCode  код
     * @param aActualDate дата актуальности
     * @return данные
     */
    public <T> T findByCode(java.lang.Class<T> aClass, String aCode, Date aActualDate) ;

    /**
     * Получение наименования по коду
     * @param aClass класс документа
     * @param aCode  код
     * @param aActualDate дата актуальности
     * @return наименование
     */
    public String getName(Class aClass, String aCode, Date aActualDate) ;


    /**
     * Поиск актуального времени
     * @param aClass       EntityBean
     * @param aActualDate  дата актуальности
     * @return идентификатор времени
     */
    public long findTime(Class aClass, java.util.Date aActualDate) ;

}
