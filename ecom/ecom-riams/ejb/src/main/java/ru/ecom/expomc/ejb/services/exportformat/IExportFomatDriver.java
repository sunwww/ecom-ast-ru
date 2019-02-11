/**
 *
 * @author ikouzmin 13.03.2007 16:13:11
 */
package ru.ecom.expomc.ejb.services.exportformat;

import java.io.Writer;

public interface IExportFomatDriver {
    void saveXml(StringBuilder s) throws SaveXmlException;
    void execute(int maxRecords);
    void saveXml(Writer writer) throws SaveXmlException;
}
