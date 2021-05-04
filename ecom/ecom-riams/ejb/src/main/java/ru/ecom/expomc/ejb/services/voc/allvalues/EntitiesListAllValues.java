package ru.ecom.expomc.ejb.services.voc.allvalues;

import org.apache.log4j.Logger;
import org.jdom.JDOMException;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.services.voc.helper.ArrayAllValue;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import java.io.IOException;

/**
 * Список свойств у документа
 */
public class EntitiesListAllValues extends ArrayAllValue {

    public static void main(String[] args) throws IOException, ClassNotFoundException, JDOMException {
        new EntitiesListAllValues() ;
    }
    public EntitiesListAllValues() throws IOException, ClassNotFoundException, JDOMException {
        for (Class clazz : entityHelper.listAllEntities()) {
            reg(clazz.getName()) ;
        }
    }

    private String  reg(String aClassName) throws ClassNotFoundException {
        return reg(classLoaderHelper.loadClass(aClassName));
    }

    private String reg(Class aClass) {

        String entityName = entityHelper.getEntityName(aClass);
        String text = entityName;
        //LOG.info(new StringBuilder().append(" reg ").append(aClass.getSimpleName()).append(" ...").toString());
        Comment comment = (Comment) aClass.getAnnotation(Comment.class);
        if (comment != null) {
            text = comment.value();
        }
        addValue(aClass.getName(), text);
        return aClass.getSimpleName() ;
    }

    private final ClassLoaderHelper classLoaderHelper = ClassLoaderHelper.getInstance();
    private final EntityHelper entityHelper = EntityHelper.getInstance();

}
