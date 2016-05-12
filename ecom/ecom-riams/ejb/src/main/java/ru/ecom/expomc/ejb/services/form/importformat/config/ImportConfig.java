package ru.ecom.expomc.ejb.services.form.importformat.config;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

/**
 * @author ikouzmin 09.03.2007 11:14:41
 */

/*
Sample XML

<import format="xml" name="OMC_LPU" version="1.0" comment="Лечебно-профилактические учреждения">
	+<entity> ...
</import>


*/


public class ImportConfig {
    private Document theDocument;

    private final static Logger LOG = Logger.getLogger(ImportConfig.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;


    //org.jdom.xpath.JaxenXPath dd;
    public void load(String configString) throws IOException, JDOMException {
        SAXBuilder saxBuilder = new SAXBuilder();
        if (!configString.startsWith("<?xml")) {
            configString = "<?xml version='1.0' encoding='UTF-8' ?>\n" +
                    configString;
        }
        theDocument = saxBuilder.build(new StringReader(configString));
    }

    public List<ImportEntity> getEntities() {
        List<ImportEntity> ret = new ArrayList<ImportEntity>();
        List<Element> entities = null;
        try {
            LOG.info("ImportDoc:"+theDocument+":");
            //theDocument.getRootElement().get
            entities = XPath.selectNodes(theDocument, "*/entity");
            for (Element entity : entities) {
                LOG.info("entity:"+entity+":");
                ImportEntity importEntity = new ImportEntity(entity);
                ret.add(importEntity);
            }
        } catch (JDOMException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return ret;
    }

    /*public void test(Logger LOG) {
        for (ImportEntity entity : getEntities()) {
            for (ImportKey importKey : entity.getKeys()) {
                LOG.info("key: "+importKey.getProperty()+" \t--to-- "+importKey.getSelect());
            }
            for (ImportMap importMap : entity.getMaps()) {
                LOG.info("map: "+importMap.getProperty()+" \t--to-- "+importMap.getSelect()+"  \t// "+ importMap.getComment());
            }
        }
    }
      */



}

