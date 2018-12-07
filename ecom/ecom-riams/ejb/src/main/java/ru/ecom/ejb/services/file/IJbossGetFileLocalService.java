package ru.ecom.ejb.services.file;

import java.io.File;

/**
 * Регистрация файла, чтобы потом получить из Tomcat
 */
public interface IJbossGetFileLocalService {

    /**
     * Регистрация файла
     * @param aFilename только имя файла. БЕЗ ПУТЕЙ
     */
    public File createFile(long aId, String aFilename) ;
}
