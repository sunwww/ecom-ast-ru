package ru.ecom.web.idemode.tagext;

import ru.nuzmsh.util.PropertyUtil;

import javax.servlet.jsp.tagext.TagAttributeInfo;
import javax.servlet.jsp.tagext.TagInfo;
import javax.servlet.jsp.tagext.TagLibraryInfo;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TagLibraryManager {

	public void addTld(Class aClass, String aName) {
		String filename = "/META-INF/"+aName+".tld" ;
		InputStream in = aClass.getResourceAsStream(filename);
		if(in==null) {
			throw new IllegalStateException("Не удалось загрузить "+filename+" из класса "+aClass) ;
		}
		try {
			theLibraries.put(aName, theLoadTldHelper.loadTld(in)) ;
		} finally {
			try { in.close() ; } catch (Exception e) {}
		}
	}

	public Collection<TagLibraryInfo> getTagLibrariesInfos() {
		return theLibraries.values();
	}
	
	public boolean isGuidSupported(String aPrefix, String aName) {
		TagInfo info = getTagInfo(aPrefix, aName);
		if(info.getAttributes()==null) return false ;
		for(TagAttributeInfo attr : info.getAttributes()) {
			if("guid".equals(attr.getName())) {
				return true ;
			}
		}
		return false ;
	}
	
	public Map<String,String> getDefaultValues(TagInfo aTagInfo) {
		HashMap<String,String> ret = new HashMap<>() ;
		try {
			Class clazz = Thread.currentThread().getContextClassLoader().loadClass(aTagInfo.getTagClassName()) ;
			Object tag = clazz.newInstance();
			for(TagAttributeInfo attr : aTagInfo.getAttributes()) {
				String property = attr.getName() ;
				try {
					Object objValue = PropertyUtil.getPropertyValue(tag, property);
					if(objValue!=null) {
						String strValue = (String)PropertyUtil.convertValue(objValue.getClass(), String.class, objValue);
						ret.put(property, strValue);
					}
				} catch (Exception e) {
					// FIXME
				}
			}
			return ret ;
		} catch(Exception e) {
			throw new RuntimeException(e) ;
		}
	}
	
	public TagInfo getTagInfo(String aPrefix, String aName) {
		TagLibraryInfo info = theLibraries.get(aPrefix);
		if("tags".equals(aPrefix)) {
			// FIXME для tags
			return new TagInfo(aName, aName, aName, aName, info, null, null);
		}
		if(info==null) {
			throw new IllegalArgumentException("Нет tld с "+aPrefix+" для "+aName);
		} 
		TagInfo ret = null ;
		for(TagInfo tag : info.getTags()) {
			if(aName.equals(tag.getTagName())) {
				ret = tag ;
				break ;
			}
		}
		if(ret==null) {
			throw new IllegalArgumentException("Нет тэга "+aPrefix+":"+aName) ;
		}
		return ret ;
	}
		
	public void addTld(File aFile, String aPrefix) throws FileNotFoundException {
		try (FileInputStream in = new FileInputStream(aFile)) {
			TagLibraryInfo info = theLoadTldHelper.loadTld(in) ;
			theLibraries.put(aPrefix, info) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		TagLibraryManager manager = new TagLibraryManager() ;
		manager.addTld(ru.nuzmsh.web.tags.AbstractFieldTag.class, "msh");
		manager.getTagInfo("msh", "table");
	}
	
	private final HashMap<String, TagLibraryInfo> theLibraries = new HashMap<>();
	private final LoadTldHelper theLoadTldHelper = new LoadTldHelper() ;
}
