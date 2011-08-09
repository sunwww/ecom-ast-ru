package ru.ecom.expomc.ejb.services.registry.voc;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocValue;

/**
 * Вывод всех экспортов
 */
public class ListDataExportAllValue implements IAllValue {

    private final static Logger LOG = Logger.getLogger(ListDataExportAllValue.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    private final String JBOSS_SERVER_DATA_EXPORT_DIR = System.getProperty("jboss.server.data.dir")+"/export";

    public Collection<VocValue> listAll(AllValueContext aContext) {
        File dir = new File(JBOSS_SERVER_DATA_EXPORT_DIR);
        File files[] = dir.listFiles() ;
        LinkedList<VocValue> list = new LinkedList<VocValue>();
        for (File file : files) {
            if(file.isFile()) {
                list.add(getVocValue(file)) ;
            }
        }
        return list;
    }


    private VocValue getVocValue(File aFile) {
        try {
            Document doc = new SAXBuilder().build(aFile) ;
            Element root = doc.getRootElement() ;
            String id = aFile.getName().substring(0, aFile.getName().length()-4) ;
            String name = root.getAttributeValue("comment") ;
            return new VocValue(id, StringUtil.isNullOrEmpty(name) ? id : name + " ("+id+")");
        } catch (Exception e) {
            LOG.error("Ошибка при обработке "+aFile,e) ;
            return new VocValue("",e.getMessage());
        }

    }
    public void destroy() {

    }
}
