package ru.ecom.expomc.ejb.services.exportformat;

import java.io.Writer;

/**
 * @author ikouzmin 13.03.2007 16:36:07
 */
public class AbstractExportFormatDriver implements IExportFomatDriver {

    public void saveXml(StringBuffer s) throws SaveXmlException {

    }

    public void execute(int maxRecords) {

    }

    public void saveXml(Writer writer) throws SaveXmlException {
        
    }

}
