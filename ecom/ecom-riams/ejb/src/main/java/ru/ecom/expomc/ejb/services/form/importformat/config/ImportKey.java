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
        theProperty = element.getAttributeValue("property");
        if (StringUtil.isNullOrEmpty(theProperty)) throw new MissingAttributeException("атрибут property отсутствует");
        theSelect = element.getAttributeValue("select",theProperty);
    }


    /** Свойство в Hibernate Bean */
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Путь XPATH для свойства */
    public String getSelect() { return theSelect ; }
    public void setSelect(String aSelect) { theSelect = aSelect ; }

    /** Свойство в Hibernate Bean */
    private String theProperty ;

    /** Путь XPATH для свойства */
    private String theSelect ;


    protected void log(String message) { theImportLogger.log(message);  }
    protected void inclev() { theImportLogger.inclev(); }
    protected void declev() { theImportLogger.declev(); }
    public ImportLogger getImportLogger() { return theImportLogger ; }
    public void setImportLogger(ImportLogger aImportLogger) { theImportLogger = aImportLogger ; }

    private ImportLogger theImportLogger ;
}
