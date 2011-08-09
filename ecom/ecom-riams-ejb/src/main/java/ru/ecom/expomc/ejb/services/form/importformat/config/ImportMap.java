package ru.ecom.expomc.ejb.services.form.importformat.config;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

/**
 * @author ikouzmin 09.03.2007 15:03:58
 */
public class ImportMap extends ImportKey {
    private final static Logger LOG = Logger.getLogger(ImportMap.class);


    public void load(Element element, EntityManager aManager) throws InvalidFkException, ClassNotFoundException, MissingAttributeException {
        super.load(element, aManager);
        theComment = element.getAttributeValue("comment");
        Attribute fkAttribute = element.getAttribute("fk");
        theSyncKey = null;
        // обновление, если не пустое
        if(!StringUtil.isNullOrEmpty(element.getAttributeValue("updateIfEmpty"))) {
        	theUpdateIfEmpty = Boolean.valueOf(element.getAttributeValue("updateIfEmpty"));
        }
        if (fkAttribute != null) {
            Matcher matcher = Pattern.compile("^([^:]*):(.*)$").matcher(fkAttribute.getValue());
            if (matcher.matches()) {
                theSyncKey = new ImportSyncKey();
                theSyncKey.setImportLogger(getImportLogger());
                theSyncKey.setProperty(matcher.group(2));
                theSyncKey.setSelect(getSelect());
                theSyncKey.createQuery(aManager, matcher.group(1));
            } else {
                LOG.error("Invalid fk attribute value");
                throw new InvalidFkException("Invalid fk attribute value");
            }
        }
    }

    /**
     * Комментарий
     */
    public String getComment() {
        return theComment;
    }

    public void setComment(String aComment) {
        theComment = aComment;
    }

    /**
     * Комментарий
     */
    private String theComment;

    private ImportSyncKey theSyncKey;

    public Object getValue(Element o, EntityManager aManager) {
        String value = "";
        try {
            if (theSyncKey != null) {
                inclev();
                String id = theSyncKey.findId(o);
                declev();
                return theSyncKey.openId(aManager,id);
            } else {
                Object element = XPath.selectSingleNode(o, getSelect());
                value = ImportSyncKey.getElementValue(element);
            }
        } catch (JDOMException e) {
        }
        return value;
    }

    public Object getValue(HashMap<String, Object> mapValues, EntityManager aManager) {
        String value = "";
        if (theSyncKey != null) {
            inclev();
            String id = theSyncKey.findId(mapValues);
            declev();
            return theSyncKey.openId(aManager,id);
        } else {
            String selectValue = getSelect();
            //LOG.info("SV:"+selectValue);
            if (selectValue==null) return "";
            if (mapValues==null) return "";
            Object oj = mapValues.get(selectValue);
            //LOG.info("OJ:"+oj);
            if (oj==null) return "";
            value = oj.toString();
        }
        return value;
    }
    
    /** Обновлять только если пустое */
	@Comment("Обновлять только если пустое")
	public boolean getUpdateIfEmpty() {
		return theUpdateIfEmpty;
	}

	public void setUpdateIfEmpty(boolean aUpdateIfEmpty) {
		theUpdateIfEmpty = aUpdateIfEmpty;
	}

	/** Обновлять только если пустое */
	private boolean theUpdateIfEmpty = true ;
}
