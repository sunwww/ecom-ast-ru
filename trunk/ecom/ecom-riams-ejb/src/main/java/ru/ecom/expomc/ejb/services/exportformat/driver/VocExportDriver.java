package ru.ecom.expomc.ejb.services.exportformat.driver;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.expomc.ejb.services.exportformat.IExportFomatDriver;
import ru.ecom.expomc.ejb.services.exportformat.SaveXmlException;
import ru.nuzmsh.util.voc.VocValue;

/**
 * @author ikouzmin 14.03.2007 13:42:43
 */
public class VocExportDriver implements IExportFomatDriver {

    private Collection<VocValue> values;

    public VocExportDriver(EntityManager theManager, String params) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //params="AllowedChecksAllValues";
        Class clazz = Class.forName("ru.ecom.expomc.ejb.services.voc.allvalues."+
                params);

        IAllValue allValue = (IAllValue) clazz.newInstance();

        AllValueContext context = new AllValueContext(null, theManager, null);
        values = allValue.listAll(context);
    }

    public void saveXml(StringBuffer s) throws SaveXmlException {
        s.append("<result>\n");
        for (VocValue vocValue : values) {
            saveVocValue(s,vocValue);
        }
        s.append("</result>\n");
    }

    public void execute(int maxRecords) { }

    public void saveXml(Writer writer) throws SaveXmlException {
        StringBuffer s = new StringBuffer();
        saveXml(s);
        try {
            writer.write(s.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new SaveXmlException(e.getMessage(),e);
        }
    }

    public void saveVocValue(StringBuffer s, VocValue vocValue) {
        s.append("\t<row id='" +vocValue.getId()+"'>");
        s.append(vocValue.getName());
        s.append("</row>\n");
    }

}
