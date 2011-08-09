package ru.ecom.ejb.services.util;

import java.io.IOException;
import java.io.InputStream;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Подключение к базе по-умолчанию
 */
public class ApplicationDataSourceHelper {

    private final static Logger LOG = Logger.getLogger(ApplicationDataSourceHelper.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    private ApplicationDataSourceHelper() {
    }

    public static ApplicationDataSourceHelper getInstance() {
        return new ApplicationDataSourceHelper();
    }

    /**
     * Получение DataSource из persistence.xml jta-data-source
     */
    public DataSource findDataSource() throws NamingException {
        InitialContext ctx = new InitialContext();
        try {
            String dsString = getDataSourceFromPersistenceXml() ;
            if (CAN_DEBUG) LOG.debug("DataSource = " + dsString);
            return (DataSource) ctx.lookup(dsString);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            ctx.close();

        }
    }

    private String getDataSourceFromPersistenceXml() throws IOException, JDOMException {
        String aResource = "/META-INF/persistence.xml";
        InputStream in = getClass().getResourceAsStream(aResource);
        try {
            Document doc = new SAXBuilder().build(in);
            Element rootElement = doc.getRootElement();
            Element unit = rootElement.getChild("persistence-unit");
            Element jtaDataSource = unit.getChild("jta-data-source");
            return jtaDataSource.getTextTrim();
        } finally {
            in.close();
        }
    }
}
