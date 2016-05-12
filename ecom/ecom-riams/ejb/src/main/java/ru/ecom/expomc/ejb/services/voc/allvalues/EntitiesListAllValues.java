package ru.ecom.expomc.ejb.services.voc.allvalues;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.services.voc.helper.ArrayAllValue;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Список свойств у документа
 */
public class EntitiesListAllValues extends ArrayAllValue {

    private final static Logger LOG = Logger.getLogger(EntitiesListAllValues.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    private void loadResource(String aResource) throws IOException, JDOMException, ClassNotFoundException {
        LOG.info(new StringBuilder().append("load resource: Loading ").append(aResource).append(" ...").toString());
        InputStream in = getClass().getResourceAsStream(aResource);
        StringBuilder sb = new StringBuilder("Registered entities: ");
        try {
            Document doc = new SAXBuilder().build(in);
            Element rootElement = doc.getRootElement();
            List<Element> persistenceUnits = rootElement.getChildren("persistence-unit") ;
            for (Element persistenceUnit : persistenceUnits) {
                List<Element> classes = persistenceUnit.getChildren("class") ;
                for (Element clazz : classes) {
                    String name = reg(clazz.getTextTrim()) ;
                    sb.append(name) ;
                    sb.append(", ") ;
                }
            }

        } finally {
            in.close();
        }
        sb.deleteCharAt(sb.length()-2) ;
        sb.append('.') ;
        LOG.info(sb) ;

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, JDOMException {
        new EntitiesListAllValues() ;
    }
    public EntitiesListAllValues() throws IOException, ClassNotFoundException, JDOMException {
        for (Class clazz : theEntityHelper.listAllEntities()) {
            reg(clazz.getName()) ;
        }
    }

    private String  reg(String aClassName) throws ClassNotFoundException {
        return reg(theClassLoaderHelper.loadClass(aClassName));
    }

    private String reg(Class aClass) {

        String entityName = theEntityHelper.getEntityName(aClass);
        String text = entityName;
        //LOG.info(new StringBuilder().append(" reg ").append(aClass.getSimpleName()).append(" ...").toString());
        Comment comment = (Comment) aClass.getAnnotation(Comment.class);
        if (comment != null) {
            text = comment.value();
        }
        addValue(aClass.getName(), text);
        return aClass.getSimpleName() ;
    }

    private final ClassLoaderHelper theClassLoaderHelper = ClassLoaderHelper.getInstance();
    private final EntityHelper theEntityHelper = EntityHelper.getInstance();

}
