package ru.ecom.expomc.ejb.services.registry.voc;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Вывод всех экспортов
 */
public class ListDataExportAllValue implements IAllValue {

    private static final Logger LOG = Logger.getLogger(ListDataExportAllValue.class) ;

    private final String JBOSS_SERVER_DATA_EXPORT_DIR = System.getProperty("jboss.server.data.dir")+"/export";

    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, AllValueContext aContext) throws VocServiceException {
    	String ret = null;
        if (aId != null) {
            for (VocValue value : listAll(aContext)) {
                if (aId.equals(value.getId())) {
                    ret = value.getName();
                }
            }
        }
        return ret;
    }
    public Collection<VocValue> listAll(AllValueContext aContext) {
        File dir = new File(JBOSS_SERVER_DATA_EXPORT_DIR);
        File[] files = dir.listFiles() ;
        LinkedList<VocValue> list = new LinkedList<>();
        for (File file : Objects.requireNonNull(files)) {
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
