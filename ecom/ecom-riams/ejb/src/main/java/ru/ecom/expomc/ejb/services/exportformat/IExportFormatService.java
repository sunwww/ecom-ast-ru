/**
 * @author ikouzmin 02.03.2007 10:39:20
 */

package ru.ecom.expomc.ejb.services.exportformat;

import javax.xml.transform.Result;
import javax.xml.transform.TransformerException;
import java.io.Writer;

public interface IExportFormatService {
    String getResultSet(Long anId);

    void getResultSet(Long anId, Writer writer) throws SaveXmlException;

    int getMaxRecords();

    void setMaxRecords(int aMaxRecords);

    void transform(Long anId, Result outStream) throws TransformerException;

    String getTransformedXml(Long anId) throws TransformerException;

    void exportAsXml(Long anId, long aFileId, long aMonitorId);

    void createStandardXsl(Long anId);
}
