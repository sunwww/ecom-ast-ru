/**
 *
 * @author ikouzmin 08.03.2007 21:12:31
 */
package ru.ecom.expomc.ejb.services.form.importformat;

import ru.ecom.expomc.ejb.services.importservice.ImportException;
import ru.ecom.expomc.ejb.services.importservice.ImportFileForm;

import java.io.File;
import java.io.IOException;

public interface IImportFormatService {
    void importFile(long aMonitorId, long importLogFileId,String aFilename, String originalFileName, ImportFileForm aImportForm) throws ImportException ;
    boolean isDebug();
    void setDebug(boolean aDebug);
    void setLogFile(File aFile) throws IOException;
    void setLogFileId(long fileId) throws IOException;
    String getLogFileName();
    boolean isUpdateModifiedOnly();
    void setUpdateModifiedOnly(boolean aUpdateModifiedOnly);
    boolean isVerifyAfterSave();
    void setVerifyAfterSave(boolean aVerifyAfterSave);
}
