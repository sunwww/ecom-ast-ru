package ru.ecom.expomc.ejb.services.form.importformat.config;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import ru.ecom.ejb.util.EntityNameUtil;
import ru.ecom.expomc.ejb.services.form.importformat.ImportLogger;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ikouzmin 09.03.2007 14:16:31
 */

/*

 <entity entityClass="omcvoc.OmcLpu" match="/result/row">
        <sync-record>
                <key property="code" select="code" />
                <key property="name" select="name" />
        </sync-record>
        <maps>
                <map property="code" select="code" comment="Код ЛПУ" />
                <map property="name" select="name" comment="Наименование" />
                <map property="address" select="address" comment="Адрес ЛПУ" />
                <map property="director" select="director" comment="Директор" />
                <map property="phone" select="phone" comment="Телефон" />
                <map property="inn" select="inn" comment="ИНН" />
                <map property="okpo" select="okpo" comment="ОКПО" />
                <map property="okonh" select="okonh" comment="ОКОНХ" />
                <map property="mail" select="mail" comment="Эл. почта АОТФОМС" />
                <map property="vedPodchin" select="vedPodchin" comment="" />
        </maps>
</entity>

*/
public class ImportEntity {
    private static final Logger LOG = Logger.getLogger(ImportEntity.class) ;

    ImportEntity() {
    }

    ImportEntity(Element entity) {
        load(entity);
    }

    public void load(Element entity) {
        theEntity = entity;
    }

    


    List<ImportSyncKey> getKeyQueries(EntityManager aManager) {
        List<ImportSyncKey> list = new ArrayList<>();
        List<Element> keys ;
        log("Построение запросов синхронизации основной записи");
        inclev();
        try {
            String entityClassName = getEntityClassName();
            keys = XPath.selectNodes(theEntity,"sync-record/key");
            for (Element key : keys) {
                ImportSyncKey importSyncKey = new ImportSyncKey();
                importSyncKey.setImportLogger(theImportLogger);
                importSyncKey.load(key,aManager);
                importSyncKey.createQuery(aManager,entityClassName);

                list.add(importSyncKey);
            }
        } catch (JDOMException | InvalidFkException | ClassNotFoundException | MissingAttributeException e) {
        } finally{
            declev();
        }
        return list;
    }

    public ImportSyncKeyList getKeyList(EntityManager aManager) {
        return new ImportSyncKeyList(this,aManager);

    }

    public List<ImportMap> getMaps(EntityManager aManager) throws Exception {
        List<ImportMap> list = new ArrayList<>();
        List<Element> maps;
        log("Построение запросов синхронизации внешних ключей");
        inclev();
        try {
            maps = XPath.selectNodes(theEntity,"maps/map");
            for (Element map : maps) {
                ImportMap importMap = new ImportMap();
                importMap.setImportLogger(theImportLogger);
                importMap.load(map,aManager);
                list.add(importMap);
            }
        } catch (JDOMException e) {
        } finally{
            declev();
        }

        return list;
    }

    public String getEntityClassName() {
        return theEntity.getAttributeValue("entityClass","");
    }

    public String getEntityName() throws ClassNotFoundException {
        Class entityClass = Class.forName(getEntityClassName());
        return EntityNameUtil.getInstance().getEntityName(entityClass);
    }

    public String getMatch() {
        return theEntity.getAttributeValue("match","*/result/row");
    }

    public String getTableName() {
        return theEntity.getAttributeValue("table","");
    }

    public String getFormat() {
        if (theEntity.getAttribute("match")!=null) return "xml";
        if (theEntity.getAttribute("table")!=null) return "dbf";
        throw new IllegalStateException("Uknown format: no match or table artribute found ");
    }

    public long getCount(Object xDocument) throws JDOMException {
        String countMatch = theEntity.getAttributeValue("count","count("+getMatch()+")");
        XPath path = XPath.newInstance(countMatch);
        Number number = path.numberValueOf(xDocument);
        return number.longValue();
    }

    public boolean isDebug() {
        return theEntity.getAttribute("debug")!=null;
    }

    public long getDebugCount() {
        Attribute attribute = theEntity.getAttribute("debug");
        if (attribute != null) {
            String value = attribute.getValue();
            if (value.equals(""))
                return Long.MAX_VALUE;
            else
                return Long.parseLong(value);
        }
        return 0;
    }



    private void log(String message) { theImportLogger.log(message);  }
    private void inclev() { theImportLogger.inclev(); }
    private void declev() { theImportLogger.declev(); }
    public ImportLogger getImportLogger() { return theImportLogger ; }
    public void setImportLogger(ImportLogger aImportLogger) { theImportLogger = aImportLogger ; }

    private ImportLogger theImportLogger ;
    private Element theEntity;
}
