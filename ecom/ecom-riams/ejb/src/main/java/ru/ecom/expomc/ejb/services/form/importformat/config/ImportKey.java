package ru.ecom.expomc.ejb.services.form.importformat.config;

import javax.persistence.EntityManager;

import org.jdom.Element;

import ru.ecom.expomc.ejb.services.form.importformat.ImportLogger;
import ru.nuzmsh.util.StringUtil;

/**
 * @author ikouzmin 09.03.2007 15:19:05
 */
public class ImportKey {
    public void load(Element element, EntityManager aManager) throws InvalidFkException, ClassNotFoundException, MissingAttributeException {
        property = element.getAttributeValue("property");
        if (StringUtil.isNullOrEmpty(property)) throw new MissingAttributeException("атрибут property отсутствует");
        select = element.getAttributeValue("select",property);
    }


    /** Свойство в Hibernate Bean */
    public String getProperty() { return property ; }
    public void setProperty(String aProperty) { property = aProperty ; }

    /** Путь XPATH для свойства */
    public String getSelect() { return select ; }
    public void setSelect(String aSelect) { select = aSelect ; }

    /** Свойство в Hibernate Bean */
    private String property ;

    /** Путь XPATH для свойства */
    private String select ;


    protected void log(String message) { importLogger.log(message);  }
    protected void inclev() { importLogger.inclev(); }
    protected void declev() { importLogger.declev(); }
    public ImportLogger getImportLogger() { return importLogger ; }
    public void setImportLogger(ImportLogger aImportLogger) { importLogger = aImportLogger ; }

    private ImportLogger importLogger ;
}
