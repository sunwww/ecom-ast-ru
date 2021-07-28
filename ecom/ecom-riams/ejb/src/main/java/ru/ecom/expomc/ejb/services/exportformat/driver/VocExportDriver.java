package ru.ecom.expomc.ejb.services.exportformat.driver;

import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.expomc.ejb.services.exportformat.IExportFormatDriver;
import ru.ecom.expomc.ejb.services.exportformat.SaveXmlException;
import ru.nuzmsh.util.voc.VocValue;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

/**
 * @author ikouzmin 14.03.2007 13:42:43
 */
public class VocExportDriver implements IExportFormatDriver {

    private Collection<VocValue> values;

    public VocExportDriver(EntityManager manager, String params) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //params="AllowedChecksAllValues";
        Class clazz = Class.forName("ru.ecom.expomc.ejb.services.voc.allvalues."+
                params);

        IAllValue allValue = (IAllValue) clazz.newInstance();

        AllValueContext context = new AllValueContext(null, manager, null);
        values = allValue.listAll(context);
    }

    public void saveXml(StringBuilder s) {
        s.append("<result>\n");
        for (VocValue vocValue : values) {
            saveVocValue(s,vocValue);
        }
        s.append("</result>\n");
    }

    public void execute(int maxRecords) { }

    public void saveXml(Writer writer) throws SaveXmlException {
        StringBuilder s = new StringBuilder();
        saveXml(s);
        try {
            writer.write(s.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new SaveXmlException(e.getMessage(),e);
        }
    }

    public void saveVocValue(StringBuilder s, VocValue vocValue) {
        s.append("\t<row id='").append(vocValue.getId()).append("'>");
        s.append(vocValue.getName());
        s.append("</row>\n");
    }

}
