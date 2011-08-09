package ru.ecom.web.idemode;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.jsp.tagext.TagAttributeInfo;
import javax.servlet.jsp.tagext.TagInfo;
import javax.servlet.jsp.tagext.TagLibraryInfo;

import org.apache.log4j.Logger;

import ru.ecom.web.idemode.tagext.TagLibraryManager;

public class IdeModeServiceJs {

	private final static Logger LOG = Logger.getLogger(IdeModeServiceJs.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

	public IdeModeServiceJs() {
		LOG.info("INIT()") ;
		theManager = new TagLibraryManager() ;
		theManager.addTld(ru.nuzmsh.web.tags.AbstractFieldTag.class, "msh");
		theManager.addTld(ru.ecom.web.tags.EntityWebTrailTag.class,  "ecom");
		theJspFileHelper = new JspFileHelper() ;
		theJspFileHelper.setWorkspaceDir(new File("/home/esinev/workspace/ecom"));
		
	}
	
	public EditTagMessage getEditTagMessage(String aGuid, String aJspPath) throws Exception {
		
		Map<String,String> values = theJspFileHelper.getAttributeValues(aJspPath, aGuid);

		TagInfo tagInfo = theManager.getTagInfo(values.get("__prefix")
				, values.get("__tag"));
		
		EditTagMessage msg = new EditTagMessage(tagInfo) ;
		
		
		msg.getValues().putAll(values);
//		for(Map.Entry<String, String> entry : values.entrySet()) {
//		}
		return msg ;
	}
	
	public String[][] listTags() {
		ArrayList<String[]> ret = new ArrayList<String[]>() ;
		for(TagLibraryInfo lib : theManager.getTagLibrariesInfos()) {
			String prefix = lib.getPrefixString() ;
			for(TagInfo tag : lib.getTags()) {
				boolean canAdd = false ;
				for(TagAttributeInfo at : tag.getAttributes()) {
					if("guid".equals(at.getName())) {
						canAdd = true ;
						break; 
					}
				}
				if(canAdd) {
					String name = tag.getTagName() ;
					String fullname = prefix+":"+name ;
					String description = fullname + " "+tag.getInfoString();
					ret.add(new String[]{fullname, description}) ;
				}
			}
		}
		String[][] ar = new String[ret.size()][2] ;
		for(int i=0; i<ret.size(); i++) {
			ar[i] = ret.get(i);
		}
		return ar ;
	}
	
//	private String normalizeTagFullname(String aStr) {
//		StringTokenizer st = new StringTokenizer(aStr, ": \t") ;
//		String prefix = st.nextToken() ;
//		String name = st.nextToken() ;
//		return prefix+":"+name ;
//	}
	
	public TagInfo getTagInfo(String aFullTagName) {
		StringTokenizer st = new StringTokenizer(aFullTagName, ": \t") ;
		String prefix = st.nextToken() ;
		String name = st.nextToken() ;
		return theManager.getTagInfo(prefix, name);
	}
	
	private String getPrefix(String aFullTagName) {
		return new StringTokenizer(aFullTagName, ": \t").nextToken() ;
	}
	
	public void saveTag(String aGuid, String aJspPath, Map<String,String> aValues) throws Exception {
		try {
			theJspFileHelper.setAttributes(aJspPath, aGuid, aValues, theManager);
		} catch (Exception e) {
			e.printStackTrace() ;
			throw e ;
		}
	}
	
	public void insertTag(String aInsertMode
				, String aTagFullnameBad
				, Map<String,String> aValues
				, String aGuid
				, String aJspPath
				) throws Exception {
		try {
			JspFileHelper.InsertOption opt ;
			if("after".equals(aInsertMode)) {
				opt = JspFileHelper.InsertOption.AFTER ;
			} else if("before".equals(aInsertMode)) {
				opt = JspFileHelper.InsertOption.BEFORE ;
			} else if("into".equals(aInsertMode)) {
				opt = JspFileHelper.InsertOption.INTO ;
			} else {
				throw new IllegalArgumentException("Не поддерживается режим вставки: "+aInsertMode) ;
			}
			TagInfo tagInfo = getTagInfo(aTagFullnameBad);
			theJspFileHelper.insertNewTag(opt, getPrefix(aTagFullnameBad), tagInfo, aValues, aJspPath, aGuid);
			
			LOG.info("insertTag() "+aValues) ;
			//theJspFileHelper.setAttributes(aJspPath, aGuid, aValues, theManager);
		} catch (Exception e) {
			e.printStackTrace() ;
			throw e ;
		}
	}
	
	public void addGuids(String aJspPath) throws Exception {
		theJspFileHelper.addGuids(aJspPath, theManager);
	}
	
	public void deleteTag(String aJspPath, String aGuid) throws Exception {
		theJspFileHelper.deleteTag(aJspPath, aGuid);
	}
	
	public void addFromTemplate(String aNewFormName) throws Exception {
		theJspFileHelper.addFromTemplate(aNewFormName);
	}
	
	private final TagLibraryManager theManager ;
	private final JspFileHelper theJspFileHelper ;
}
