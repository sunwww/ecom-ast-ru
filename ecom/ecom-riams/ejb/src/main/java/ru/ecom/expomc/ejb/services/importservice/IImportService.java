package ru.ecom.expomc.ejb.services.importservice;

/**
 * Импорт
 */
public interface IImportService {

    /**
     * Импорт файла
     * @param aFilename      DBF файл для импорта
     * @return результат импорта
     */
    ImportFileResult importFile(String aOriginalFilename, long aMonitorId, String aFilename, ImportFileForm aImportForm) throws ImportException ;

}
