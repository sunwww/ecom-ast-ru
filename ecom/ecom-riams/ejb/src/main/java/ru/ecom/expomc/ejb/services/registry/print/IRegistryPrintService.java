package ru.ecom.expomc.ejb.services.registry.print;

import java.text.ParseException;

import ru.ecom.expomc.ejb.services.registry.CreateRegistryForm;

/**
 * Печать реестра
 */
public interface IRegistryPrintService {
//    void printReestrXls(String aFilename, long aPeriodId) throws RegistryPrintException ;
    void printReestr(long aMonitorId, long aFileId, CreateRegistryForm aForm) throws ParseException, RegistryPrintException;

}
