package ru.ecom.expomc.ejb.services.form.impdoc;

import java.util.Collection;

import ru.ecom.expomc.ejb.domain.impdoc.IImportData;

/**
 * Сервис для работы данными
 */
public interface IImportDataService {
    Collection<IImportData> listAll(long aTime) ;
    void join(long[] aImportedDataIds) ;
}
