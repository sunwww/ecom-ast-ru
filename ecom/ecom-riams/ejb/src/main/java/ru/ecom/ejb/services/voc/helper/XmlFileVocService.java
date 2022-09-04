package ru.ecom.ejb.services.voc.helper;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import ru.ecom.ejb.services.voc.IVocContextService;
import ru.ecom.ejb.services.voc.IVocServiceManagement;
import ru.ecom.ejb.services.voc.VocContext;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class XmlFileVocService implements IVocContextService, IVocServiceManagement {
    private static final Logger LOG = Logger.getLogger(XmlFileVocService.class);
    final String fileDiaryParameter;
    EjbEcomConfig ecomConfig = EjbEcomConfig.getInstance();


    public XmlFileVocService(String aFilename) {
        fileDiaryParameter = aFilename;
    }

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public List<VocValue> loadParameterType(VocContext aContext) {
        List<VocValue> list = new LinkedList<>();
        try {
            loadFile(fileDiaryParameter, list, aContext);
        } catch (Exception e) {
        }

        return list;
    }

    private void loadFile(String aResourceString, List<VocValue> aList, VocContext aContext) {

        try (InputStream in = getInputStream(aResourceString)) {
            Document doc = new SAXBuilder().build(in);
            Element parConfigElement = doc.getRootElement();
            for (Object o : parConfigElement.getChildren()) {
                Element parElement = (Element) o;
                if ("parameter".equals(parElement.getName())) {
                    VocValue vocValue = put(parElement, aContext);
                    if (vocValue != null) aList.add(vocValue);
                } else {
                    LOG.warn("Нет поддержки элемента " + parElement.getName());
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    private VocValue put(Element aElement, VocContext aContext) {
        String key = aElement.getAttributeValue("id");
        if (StringUtil.isNullOrEmpty(key)) {
            throw new IllegalArgumentException("Нет атрибута id");
        }
        String name = aElement.getAttributeValue("name");
        String roles = aElement.getAttributeValue("roles");
        if (StringUtil.isNullOrEmpty(roles) || aContext.getSessionContext().isCallerInRole(roles)) {
            if (StringUtil.isNullOrEmpty(key)) {
                throw new IllegalArgumentException("Нет атрибута name");
            }
            return new VocValue(key, name);
        } else {
            return null;
        }
    }

    private InputStream getInputStream(String aResourceString) throws FileNotFoundException {
        return ecomConfig.getInputStream(aResourceString, "", true);
    }

    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
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

    public Collection<VocValue> findVocValueByQuery(String aVocName, String aQuery, int aCount, VocAdditional aAdditional, VocContext aContext) {
        String query = aQuery.toUpperCase();
        String findedId =
                StringUtil.isNullOrEmpty(aQuery)
                        ?
                        listAll(aContext).stream()
                                .filter(value ->
                                        (value.getName() != null && value.getName().toUpperCase().contains(query))
                                                ||
                                                (value.getId() != null && value.getId().toUpperCase().contains(query))
                                )
                                .findFirst()
                                .map(VocValue::getId)
                                .orElse(null)
                        : null;

        return findVocValueNext(aVocName, findedId, aCount, aAdditional, aContext);
    }

    public Collection<VocValue> findVocValuePrevious(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
        LinkedList<VocValue> ret = new LinkedList<>();
        boolean finded = StringUtil.isNullOrEmpty(aId);
        LinkedList<VocValue> reverted = new LinkedList<>();
        for (VocValue value : listAll(aContext)) {
            reverted.add(0, value);
        }
        for (VocValue value : reverted) {
            if (!finded && value.getId().equals(aId)) {
                finded = true;
            }
            if (finded) {
                ret.add(0, value);
                if (ret.size() > aCount) break;
            }
        }
        return ret;
    }

    public Collection<VocValue> findVocValueNext(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
        LinkedList<VocValue> ret = new LinkedList<>();
        boolean finded = StringUtil.isNullOrEmpty(aId);
        for (VocValue value : listAll(aContext)) {
            if (!finded && value.getId().equals(aId)) {
                finded = true;
            }
            if (finded) {
                ret.add(value);
                if (ret.size() > aCount) break;
            }
        }
        return ret;
    }

    private Collection<VocValue> listAll(VocContext aContext) {
        return loadParameterType(aContext);
    }


}
