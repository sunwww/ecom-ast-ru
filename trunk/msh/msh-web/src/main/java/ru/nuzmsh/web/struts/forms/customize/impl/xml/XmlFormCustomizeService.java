package ru.nuzmsh.web.struts.forms.customize.impl.xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ru.nuzmsh.commons.auth.ILoginInfo;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.struts.forms.customize.FormCustomizeException;
import ru.nuzmsh.web.struts.forms.customize.FormElementInfo;
import ru.nuzmsh.web.struts.forms.customize.FormInfo;
import ru.nuzmsh.web.struts.forms.customize.IFormCustomizeService;

/**
 *
 */
public class XmlFormCustomizeService implements IFormCustomizeService {

    private final static Log LOG = LogFactory.getLog(XmlFormCustomizeService.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;



    /** Каталог для хранения настроек */
    public String getConfigDir() { return theConfigDir ; }
    public void setConfigDir(String aConfigDir) { theConfigDir = aConfigDir ; }

    public void start() {
        theHash = new TreeMap<String, TreeMap<String, FormElementInfo>>();
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
//        FormElementInfo info = theHash.get(sb.toString()) ;
        //if (CAN_TRACE) LOG.trace("findFormElementInfo(): key = " + sb);
        StringBuilder sb = new StringBuilder(aFormName).append('.').append(aElementName);
        if (CAN_TRACE) LOG.trace(new StringBuilder().append("findFormElementInfo(): ").append(sb).append(" = ").append(info).toString());
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
        if (CAN_TRACE) LOG.trace("saving " +aFormName+"." + aInfo);

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
                elementsHash = new TreeMap<String, FormElementInfo>();
                theHash.put(aFormName, elementsHash) ;
            }
            elementsHash.put(aInfo.getName(), aInfo) ;
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

    private void saveAll() throws IOException, UnsupportedEncodingException {
        XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
        FileOutputStream tmpOut = new FileOutputStream(theConfigDir+"/formsCustomize.xml") ;
        OutputStreamWriter fileOut = new OutputStreamWriter(tmpOut, "utf-8");
//        fileOut.write("<?xml version='1.0' encoding='utf-8'?>\n");
        Element forms = new Element("forms");
        Document doc = new Document(forms);
        for (String formKey : theHash.keySet()) {
            TreeMap<String, FormElementInfo> elementsHash = theHash.get(formKey) ;
            Element form = new Element("form");
            form.setAttribute("name",formKey) ;
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
        fileOut.close() ;
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
        if (CAN_TRACE) LOG.trace("canSave()" + aInfo);
        boolean canSave = false ;
        do {
            if(aInfo==null)  { break ; }
            if(!StringUtil.isNullOrEmpty(aInfo.getDefaultValue())) { canSave = true ; break ; }
            if(!StringUtil.isNullOrEmpty(aInfo.getLabel()))        { canSave = true ; break ; }
            if(aInfo.isVisible()!=null)                            { canSave = true ; break ; }
        } while(false) ;
        if (CAN_TRACE) LOG.trace("  canSave = " + canSave);
        return canSave ;
    }

    public Collection<FormInfo> listCustomizedForms() throws FormCustomizeException {
        LinkedList<FormInfo> ret = new LinkedList<FormInfo>();
        for (String key : theHash.keySet()) {
            FormInfo formInfo = new FormInfo();
            formInfo.setName(key);
            ret.add(formInfo) ;
        }
        return ret ;
    }

    public Collection<FormElementInfo> listCustomizedElements(String aFormName) throws FormCustomizeException {
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
