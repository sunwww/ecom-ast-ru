package ru.ecom.expomc.ejb.services.form.importformat.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ikouzmin 09.03.2007 15:03:58
 */
@Setter
@Getter
public class ImportMap extends ImportKey {
    private static final Logger LOG = Logger.getLogger(ImportMap.class);


    public void load(Element element, EntityManager aManager) throws InvalidFkException, ClassNotFoundException, MissingAttributeException {
        super.load(element, aManager);
        comment = element.getAttributeValue("comment");
        Attribute fkAttribute = element.getAttribute("fk");
        syncKey = null;
        // обновление, если не пустое
        if(!StringUtil.isNullOrEmpty(element.getAttributeValue("updateIfEmpty"))) {
        	updateIfEmpty = Boolean.valueOf(element.getAttributeValue("updateIfEmpty"));
        }
        if (fkAttribute != null) {
            Matcher matcher = Pattern.compile("^([^:]*):(.*)$").matcher(fkAttribute.getValue());
            if (matcher.matches()) {
                syncKey = new ImportSyncKey();
                syncKey.setImportLogger(getImportLogger());
                syncKey.setProperty(matcher.group(2));
                syncKey.setSelect(getSelect());
                syncKey.createQuery(aManager, matcher.group(1));
            } else {
                LOG.error("Invalid fk attribute value");
                throw new InvalidFkException("Invalid fk attribute value");
            }
        }
    }

    /**
     * Комментарий
     */
    private String comment;

    private ImportSyncKey syncKey;

    public Object getValue(Element o, EntityManager aManager) {
        String value = "";
        try {
            if (syncKey != null) {
                inclev();
                String id = syncKey.findId(o);
                declev();
                return syncKey.openId(aManager,id);
            } else {
                Object element = XPath.selectSingleNode(o, getSelect());
                value = ImportSyncKey.getElementValue(element);
            }
        } catch (JDOMException e) {
        }
        return value;
    }

    public Object getValue(HashMap<String, Object> mapValues, EntityManager aManager) {
        String value;
        if (syncKey != null) {
            inclev();
            String id = syncKey.findId(mapValues);
            declev();
            return syncKey.openId(aManager,id);
        } else {
            String selectValue = getSelect();
            if (selectValue==null) return "";
            if (mapValues==null) return "";
            Object oj = mapValues.get(selectValue);
            if (oj==null) return "";
            value = oj.toString();
        }
        return value;
    }

	/** Обновлять только если пустое */
	private boolean updateIfEmpty = true ;
}
