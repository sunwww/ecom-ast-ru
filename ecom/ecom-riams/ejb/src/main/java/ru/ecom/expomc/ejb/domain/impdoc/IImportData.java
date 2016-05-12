package ru.ecom.expomc.ejb.domain.impdoc;

/**
 * Данные импорта
 */
public interface IImportData {
    /**
     * Получение времени импорта ImportTime
     *
     */
    long getTime() ;
    void setTime(long aTime) ;
}
