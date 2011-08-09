package ru.ecom.web.idemode.tagext;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.TagAttributeInfo;
import javax.servlet.jsp.tagext.TagInfo;
import javax.servlet.jsp.tagext.TagLibraryInfo;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

public class LoadTldHelper {

	private final static Logger LOG = Logger.getLogger(LoadTldHelper.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public TagLibraryInfo loadTld(InputStream aInputStream)  {
		try {
			Document doc = new SAXBuilder().build(
					new InputStreamReader(aInputStream, "utf-8")
			) ;
			
			
			Element root = doc.getRootElement() ;
			if (CAN_DEBUG)
				LOG.debug("addTld: root.getName() = " + root); 

			List list = root.getChildren() ;
			if (CAN_DEBUG)
				LOG.debug("addTld: list = " + list); 

			
			String name = root.getChildTextTrim("short-name", root.getNamespace());
			String uri = root.getChildTextTrim("uri", root.getNamespace());
			if (CAN_DEBUG)
				LOG.debug("addTld: name = " + name); 
			if (CAN_DEBUG)
				LOG.debug("addTld: uri = " + uri); 


			TagLibraryInfoImpl libInfo = new TagLibraryInfoImpl(name, uri) ;
			for(Object obj : root.getChildren())  {
				Element elm = (Element) obj ;
				if(elm.getName().equals("tag")) {
					libInfo.addTagInfo(loadTagInfo(elm, root.getNamespace(), libInfo));
				}
			}
			return libInfo ;
			
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Не поддерживается кодировка utf-8",e) ;
		} catch (JDOMException e) {
			throw new IllegalStateException("Ошибка разбора структуры: "+e.getMessage(),e) ;
		} catch (IOException e) {
			throw new IllegalStateException("Ошибка ввода-вывода: "+e.getMessage(),e) ;
		}
		
	}
	
	private TagInfo loadTagInfo(Element aElement, Namespace aNamespace, TagLibraryInfo aLibraryInfo) {
		String description = aElement.getChildTextTrim("description", aNamespace);
		String displayName = aElement.getChildTextTrim("display-name", aNamespace);
		String icon = aElement.getChildTextTrim("icon", aNamespace);
		String name = aElement.getChildTextTrim("name", aNamespace);
		String tagClass = aElement.getChildTextTrim("tag-class", aNamespace);
		String bodyContent = aElement.getChildTextTrim("body-content", aNamespace);
//		String  = aElement.getChildTextTrim("", aNamespace);
		//TagInfo i = new TagInfo() ;
		ArrayList<TagAttributeInfo> attrs = new ArrayList<TagAttributeInfo>(20) ;
		for(Object obj : aElement.getChildren()) {
			Element elm = (Element) obj ;
			if("attribute".equals(elm.getName())) {
				attrs.add(loadTagAttributeInfo(elm, aNamespace, tagClass));
			}
		}
		TagAttributeInfo[] attributes = attrs.toArray(new TagAttributeInfo[attrs.size()]) ;
		
		TagInfo ret = new TagInfo(name, tagClass, bodyContent, description
				, aLibraryInfo, null, attributes
				, displayName, icon, null, null) ;
		if (CAN_DEBUG)
			LOG.debug("loadTagInfo: ret = " + ret.getTagName()); 

		return ret ; 
	}
	
	
	private TagAttributeInfo loadTagAttributeInfo(Element aElement, Namespace aNamespace, String aTagClass) {
		String description = aElement.getChildTextTrim("description", aNamespace);
		String name = aElement.getChildTextTrim("name", aNamespace);
		boolean required = toBoolean(aElement.getChildTextTrim("required", aNamespace));
		boolean rtexprvalue = toBoolean(aElement.getChildTextTrim("rtexprvalue", aNamespace));
		String type = getAttributeType(aTagClass, name); 
		TagAttributeInfo ret = new TagAttributeInfoImpl(name, required, type, rtexprvalue, description) ;
		if (CAN_DEBUG)
			LOG.debug("  loadTagAttributeInfo: ret = " + ret); 

		return ret ;
	}
	
	private String getAttributeType(String aTagClass, String aProperty) {
		String ret = "String";
		
		try {
			Class clazz = Thread.currentThread().getContextClassLoader().loadClass(aTagClass);
			Method m = PropertyUtil.getGetterMethod(clazz, aProperty);
			Class type = m.getReturnType() ;
			ret = type.getSimpleName();
		} catch (Exception e) {
			LOG.warn("Ошибка при получении типа атрибута "+aProperty+ " у класса "+aTagClass, e) ;
		}
		return ret ;
	}
	
	private boolean toBoolean(String aStr) {
		return !StringUtil.isNullOrEmpty(aStr) ? Boolean.parseBoolean(aStr) : false ;  
	}
	
}
