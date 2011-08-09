package ru.ecom.mis.ejb.service.bypass;

/**
 *
 */
public interface IBypassService {

    /**
     * Экспорт файла по участку
     */
    public void printByArea(long aMonitorId, long aLpuAreaId, long aFileId) ;

    /**
     * Экспорт файла по адресу участка
     */
    public void printByAreaAddress(long aMonitorId, long aLpuAreaAddressTextId, long aFileId) ;

}
