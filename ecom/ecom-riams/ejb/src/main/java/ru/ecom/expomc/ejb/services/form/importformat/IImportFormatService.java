/**
 *
 * @author ikouzmin 08.03.2007 21:12:31
 */
package ru.ecom.expomc.ejb.services.form.importformat;

import java.io.File;
import java.io.IOException;

import ru.ecom.expomc.ejb.services.importservice.ImportException;
import ru.ecom.expomc.ejb.services.importservice.ImportFileForm;

public interface IImportFormatService {
    void importFile(long aMonitorId, long importLogFileId,String aFilename, String originalFileName, ImportFileForm aImportForm) throws ImportException ;
    boolean isDebug();
    void setDebug(boolean aDebug);
    public void setLogFile(File aFile) throws IOException;
    public void setLogFileId(long fileId) throws IOException;
    public String getLogFileName();

    public boolean isUpdateModifiedOnly();
    public void setUpdateModifiedOnly(boolean aUpdateModifiedOnly);

    public boolean isVerifyAfterSave();
    public void setVerifyAfterSave(boolean aVerifyAfterSave);
}
