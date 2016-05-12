package ru.ecom.expomc.ejb.services.exportservice;


/**
 * Экспорт файла
 */
public interface IExportService {
    public void export(long aMonitorId, long aFileId, ExportForm aForm) ;
}
