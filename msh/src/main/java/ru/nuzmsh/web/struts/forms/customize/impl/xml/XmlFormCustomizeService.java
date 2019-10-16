package ru.nuzmsh.web.struts.forms.customize.impl.xml;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import ru.nuzmsh.commons.auth.ILoginInfo;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.struts.forms.customize.FormElementInfo;
import ru.nuzmsh.web.struts.forms.customize.FormInfo;
import ru.nuzmsh.web.struts.forms.customize.IFormCustomizeService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 *
 */
public class XmlFormCustomizeService implements IFormCustomizeService {

    private static final Logger LOG = Logger.getLogger(XmlFormCustomizeService.class) ;

    /** Каталог для хранения настроек */
    public String getConfigDir() { return theConfigDir ; }
    public void setConfigDir(String aConfigDir) { theConfigDir = aConfigDir ; }

    public void start() {
        theHash = new TreeMap<>();
        try {
            Element root = getRootElement(theConfigDir+"/formsCustomize.xml") ;
            List<Element> forms = root.getChildren() ;
            for (Element formElement : forms) {
                List<Element> elements = formElement.getChildren() ;
                String name = getAttributeString(formElement, "name") ;
                for (Element element : elements) {
                    FormElementInfo info = new FormElementInfo(getAttributeString(element, "name"));
                    info.setRequired(getAttributeBoolean(element,"required"));
                    info.setLabel(getAttributeString(element,"label"));
                    info.setDefaultValue(getAttributeString(element,"default"));
                    info.setVisible(getAttributeBoolean(element,"visible"));
                    saveFormElementInfoNoSave(null, name, info);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Ошибка получения данных из xml",e) ;
        }
    }



    public void reload() {
        stop() ;
        start() ;
    }

    public void stop() {
        for (TreeMap<String, FormElementInfo> map : theHash.values()) {
            map.clear();
        }
        theHash.clear();
        theHash = null ;
    }

    public FormElementInfo findFormElementInfo(ILoginInfo aLoginInfo, String aFormName, String aElementName) {
        if(aFormName==null) throw new IllegalArgumentException("Нет названия формы") ;
        if(aElementName==null) throw new IllegalArgumentException("Нет названия элемента") ;

        TreeMap<String, FormElementInfo> elementsHash = theHash.get(aFormName) ;
        FormElementInfo info ;
        if(elementsHash!=null) {
            info = elementsHash.get(aElementName) ;
        } else {
            info = null ;
        }
        return info ;
    }

    private Element getRootElement(String aFilename) {
        try {
            Document doc = new SAXBuilder().build(aFilename) ;
            return doc.getRootElement() ;
        } catch (Exception e) {
            LOG.warn("Ошибка загрузки файла: "+aFilename, e);
            return new Element("forms") ;
        }

    }


    private void saveFormElementInfoNoSave(ILoginInfo aLoginInfo, String aFormName, FormElementInfo aInfo) {
        if(aInfo!=null && !canSave(aInfo)) { // удаляем из базы
            TreeMap<String, FormElementInfo> elementsHash = theHash.get(aFormName) ;
            if(elementsHash!=null) {
                if(elementsHash.get(aInfo.getName())!=null) {
                    elementsHash.remove(aInfo.getName()) ;
                }
                if(elementsHash.isEmpty()) {
                    theHash.remove(aFormName) ;
                }
            }
        } else {
            TreeMap<String, FormElementInfo> elementsHash = theHash.get(aFormName) ;
            if(elementsHash==null) {
                elementsHash = new TreeMap<>();
                theHash.put(aFormName, elementsHash) ;
            }
            elementsHash.put(aInfo!=null ? aInfo.getName() : null, aInfo) ;
        }
    }

    public void saveFormElementInfo(ILoginInfo aLoginInfo, String aFormName, FormElementInfo aInfo) {
        saveFormElementInfoNoSave(aLoginInfo, aFormName, aInfo);
        try {
            saveAll() ;
        } catch (IOException e) {
            LOG.error("Ошибка сохранения данных форм",e);
        }
    }

    private void saveAll() throws IOException {
        XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
        FileOutputStream tmpOut = new FileOutputStream(theConfigDir+"/formsCustomize.xml") ;
        try (OutputStreamWriter fileOut = new OutputStreamWriter(tmpOut, "utf-8")){
            Element forms = new Element("forms");
            Document doc = new Document(forms);
            for (Map.Entry<String, TreeMap<String, FormElementInfo>> entrySet : theHash.entrySet()) {
                TreeMap<String, FormElementInfo> elementsHash = entrySet.getValue();
                Element form = new Element("form");
                form.setAttribute("name",entrySet.getKey()) ;
                for (FormElementInfo info : elementsHash.values()) {
                    if(canSave(info)) {
                        form.addContent(createXmlElementFromElement(info)) ;
                    }
                }
                if(form.getChildren()!=null && !form.getChildren().isEmpty()) {
                    forms.addContent(form) ;
                }
            }
            xmlOut.output(doc, fileOut);
        }
    }

    private Element createXmlElementFromElement(FormElementInfo info) {
        Element elm = new Element("field");
        if(info.getName()!=null) {
            elm.setAttribute("name", info.getName()) ;
        }
        if(info.getLabel()!=null) {
            elm.setAttribute("label", info.getLabel()) ;
        }
        if(info.getDefaultValue()!=null) {
            elm.setAttribute("default", info.getDefaultValue()) ;
        }

        if(info.isVisible()!=null) {
            String isVisible = (info.isVisible()!=null) ? info.isVisible().toString() : "" ;
            elm.setAttribute("visible", isVisible) ;
        }
        return elm ;
    }

    private boolean canSave(FormElementInfo aInfo) {
        return !StringUtil.isNullOrEmpty(aInfo.getDefaultValue()) || !StringUtil.isNullOrEmpty(aInfo.getLabel()) ||aInfo.isVisible()!=null ;
    }

    public Collection<FormInfo> listCustomizedForms()  {
        LinkedList<FormInfo> ret = new LinkedList<>();
        for (String key : theHash.keySet()) {
            FormInfo formInfo = new FormInfo();
            formInfo.setName(key);
            ret.add(formInfo) ;
        }
        return ret ;
    }

    public Collection<FormElementInfo> listCustomizedElements(String aFormName) {
        TreeMap<String,FormElementInfo> elementsHash = theHash.get(aFormName) ;
        return elementsHash!=null ? elementsHash.values() : null ;
    }

    private Boolean getAttributeBoolean(Element aElement, String aAttribute) {
        String value = aElement.getAttributeValue(aAttribute) ;
        Boolean ret ;
        if(value==null) {
            ret = null ;
        } else {
            try {
                ret = Boolean.valueOf(value) ;
            } catch (Exception e) {
                ret = null ;
            }
        }
        return ret ;

    }

    private String getAttributeString(Element aElement, String aAttribute) {
        String value = aElement.getAttributeValue(aAttribute) ;
        String ret ;
        if(value==null) {
            ret = null ;
        } else {
            try {
                ret = value.trim() ;
                if("".equals(ret)) {
                    ret = null ;
                }
            } catch (Exception e) {
                ret = null ;
            }
        }
        return ret ;

    }

    /** Каталог для хранения настроек */
    private String theConfigDir ;

//    private TreeMap<String,FormElementInfo> theHash ;
    private TreeMap<String, TreeMap<String, FormElementInfo>> theHash ;
}
