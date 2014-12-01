package ru.ecom.ejb.services.voc.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.voc.IVocConfigXmlService;
import ru.ecom.ejb.services.voc.IVocContextService;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.nuzmsh.util.StringUtil;

/**
 *
 */
public class XmlVocValueLoader {

	private final static Logger LOG = Logger.getLogger(XmlVocValueLoader.class) ;
//    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    public void load(Map<String, IVocContextService> aHash) throws IOException, JDOMException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        loadFile(aHash, "/META-INF/voc.xml");
    }

    private void loadFile(Map<String, IVocContextService> aHash, String aResourceString) throws IOException, JDOMException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        LOG.info(new StringBuilder().append("Loading ").append(aResourceString).append(" ...").toString());
        InputStream in = getInputStream(aResourceString) ; 
        try {
            Document doc = new SAXBuilder().build(in);
            Element vocConfigElement = doc.getRootElement();
            for (Object o : vocConfigElement.getChildren()) {
                Element vocElement = (Element) o;
                if("voc".equals(vocElement.getName())) {
                    put(aHash, vocElement);
                } else if("vocFile".equals(vocElement.getName())) {
                    loadFile(aHash, vocElement.getTextTrim());
                } else {
                    LOG.warn("Нет поддержки элемента "+vocElement.getName());
                }
            }
        } finally {
            in.close();
        }
        LOG.info("Done.") ;

    }

    private InputStream getInputStream(String aResourceString) throws FileNotFoundException {
    	return theEcomConfig.getInputStream(aResourceString, EjbEcomConfig.VOC_DIR_PREFIX);
    }
    
    private static String fixedCharString(String aInitial) {
        StringBuilder sb = new StringBuilder();
        int count = 20 - aInitial.length() ;
        for(int i=0; i<count; i++) {
            sb.append(" ") ;
        }
        return sb.toString();
    }
    private void put(Map<String, IVocContextService> aHash, Element aElement) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String key = aElement.getAttributeValue("id");
        if (StringUtil.isNullOrEmpty(key)) {
            throw new IllegalArgumentException("Нет атрибута id");
        }
        String type = aElement.getAttributeValue("type");
        LOG.info(new StringBuilder().append(" Registering ").append(key).append(fixedCharString(key)).append(" (").append(type).append(")...").toString());
        if ("AllValueHelper".equals(type)) {
            Element iAllValueElement = aElement.getChild("IAllValue");
            if (iAllValueElement == null) throw new IllegalStateException("Нет элемента IAllValue у справочника " + key);
            String clazz = iAllValueElement.getTextTrim();
            if (StringUtil.isNullOrEmpty(clazz)) throw new IllegalStateException("Пустой элемент IAllValue");
            IAllValue iAllValueObject = (IAllValue) theClassLoaderHelper.loadClass(clazz).newInstance() ;
            AllValueHelper allValueHelper = new AllValueHelper(iAllValueObject);
            put(aHash, key, allValueHelper);
        } else if("AllValueHelperEntityVoc".equals(type)) {
            Element vocValueElement = aElement.getChild("vocValue") ;
            if(vocValueElement==null) throw new IllegalStateException("Нет элемента vocValue у "+key) ;

            AllValueHelper allValueHelper = new AllValueHelper(new EntityVocAllValue(
                    vocValueElement.getAttributeValue("entity")
                    , vocValueElement.getAttributeValue("id")
                    , vocValueElement.getAttributeValue("names")
                    , vocValueElement.getAttributeValue("sortBy")
            ));
            put(aHash, key, allValueHelper) ;
        } else if("IVocService".equals(type)) {

//            Element iVocServiceElement = aElement.getChild("IVocService");
//            if(iVocServiceElement==null) throw new IllegalStateException("Нет элемента IVocService") ;
            String className = aElement.getAttributeValue("class") ;
            if(StringUtil.isNullOrEmpty(className)) throw new IllegalStateException("Нет аттрибута class в справочнике "+key) ;
            Class clazz = theClassLoaderHelper.loadClass(className) ;
            IVocContextService service = (IVocContextService) clazz.newInstance() ;
            if(service instanceof IVocConfigXmlService) {
                IVocConfigXmlService iVocConfigXmlService = (IVocConfigXmlService) service ;
                try {
                    iVocConfigXmlService.config(aElement);
                } catch (Exception e) {
                    throw new IllegalStateException("Ошибка инициализации справочника "+key+": "+e.getMessage(), e) ;
                }
            }
            put(aHash, key, service) ;
        } else if("EntityVocService".equals(type)) {
            Element elm = aElement.getChild("EntityVocService") ;
            String className = elm.getAttributeValue("entity") ;
            String[] names = getAsArray(elm.getAttributeValue("names")) ;
            String[] queried = getAsArray(elm.getAttributeValue("queried")) ;
            String parent = elm.getAttributeValue("parent") ;
            if(StringUtil.isNullOrEmpty(parent)) {
                parent = elm.getAttributeValue("parentProperty") ;
            }
            String queryAppend = elm.getAttributeValue("queryAppend") ;
            String queryConvertType = elm.getAttributeValue("queryConvertType") ;
            EntityVocService service = new EntityVocService(className, names, queried,parent, queryAppend,queryConvertType);
            put(aHash, key, service) ;
        } else if("NativeVocService".equals(type)){
        	Element elm = aElement.getChild("NativeVocService") ;
        	//String[] names = getAsArray(elm.getAttributeValue("names")) ;
        	String names = elm.getAttributeValue("names") ;
        	String nameId = elm.getAttributeValue("nameId") ;
        	String from = elm.getAttributeValue("from") ;
        	String join = elm.getAttributeValue("join") ;
        	String queried = elm.getAttributeValue("queried") ;
        	String queryAppend = elm.getAttributeValue("queryAppend") ;
        	String parent = elm.getAttributeValue("parent") ;
        	if(StringUtil.isNullOrEmpty(parent)) {
        		parent = elm.getAttributeValue("parentProperty") ;
        	}
        	String order = elm.getAttributeValue("order");
        	//String split = elm.getAttributeValue("queriedSplit");
        	String splitFields = elm.getAttributeValue("queriedSplitCount");
        	String splitParent = elm.getAttributeValue("parentSplitCount");
        	String groupBy = elm.getAttributeValue("groupBy");
        	NativeVocService service = new NativeVocService(from, names,nameId, join, queryAppend,queried, parent, order,splitFields,splitParent,groupBy) ;
        	put(aHash, key, service) ;
        } else if("XmlFileVocService".equals(type)){
        	Element elm = aElement.getChild("XmlFileVocService") ;
            if (elm == null) throw new IllegalStateException("Нет элемента XmlFileVocService у справочника " + key);
        	//String[] names = getAsArray(elm.getAttributeValue("names")) ;
        	String filename = elm.getAttributeValue("filename") ;
        	String parent = elm.getAttributeValue("parent") ;
        	if(StringUtil.isNullOrEmpty(parent)) {
                parent = elm.getAttributeValue("parentProperty") ;
            }
        	//Element iAllValueElement = aElement.getChild("IAllValue");
            if (StringUtil.isNullOrEmpty(filename)) throw new IllegalStateException("Пустой элемент filename");
            XmlFileVocService service = new XmlFileVocService(filename) ;
            
            put(aHash, key, service) ;
        } else if("TreeNativeVocService".equals(type)) {
        	Element elm = aElement.getChild("TreeNativeVocService") ;
        	String[] fields = getAsArray(elm.getAttributeValue("names")) ;
        	String from = elm.getAttributeValue("from") ;
        	String treeField = elm.getAttributeValue("treeField") ;
        	String join = elm.getAttributeValue("join") ;
        	String queried = elm.getAttributeValue("queried") ;
        	String parent = elm.getAttributeValue("parent") ;
        	if(StringUtil.isNullOrEmpty(parent)) {
                parent = elm.getAttributeValue("parentProperty") ;
            }
        	String order = elm.getAttributeValue("order") ;
        	TreeNativeVocService service = new TreeNativeVocService(treeField, from, fields, join, queried, parent, order) ;
        	put(aHash, key, service) ;
        	
        } else {
            throw new IllegalStateException("Не поддерживается тип " + type);
        }
    }
    
    private static void put(Map<String, IVocContextService> aHash, String aKey, IVocContextService aService) {
    	if(aHash.containsKey(aKey)) {
    		LOG.info("OVERRIDE "+aKey) ;
    	}
    	aHash.put(aKey, aService);
    }

    private static String[] getAsArray(String aStr) {
        StringTokenizer st = new StringTokenizer(aStr, " ;,:");
        LinkedList<String> list = new LinkedList<String>();
        while(st.hasMoreTokens()) {
            list.add(st.nextToken()) ;
        }
        String[] ret = new String[list.size()];
        for(int i=0; i<list.size(); i++) {
            ret[i] = list.get(i) ;
        }
        return ret ;
    }
    
    ClassLoaderHelper theClassLoaderHelper = ClassLoaderHelper.getInstance();
    EjbEcomConfig theEcomConfig = EjbEcomConfig.getInstance(); 
}
