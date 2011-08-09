package ru.ecom.gwtservice.idemode;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.servlet.jsp.tagext.TagAttributeInfo;
import javax.servlet.jsp.tagext.TagInfo;
import javax.servlet.jsp.tagext.TagLibraryInfo;

import ru.ecom.gwt.idemode.client.service.EditTagMessage;
import ru.ecom.gwt.idemode.client.service.IIdeModeService;
import ru.ecom.gwt.idemode.client.service.IdeModeException;
import ru.ecom.gwt.idemode.client.service.TagAttributeInfoAdapter;
import ru.ecom.gwt.idemode.client.service.TagInfoAdapter;
import ru.ecom.gwt.idemode.client.service.TagValues;
import ru.ecom.web.idemode.JspFileHelper;
import ru.ecom.web.idemode.tagext.TagLibraryManager;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.PropertyUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class IdeModeServiceImpl extends RemoteServiceServlet implements
		IIdeModeService {

	public IdeModeServiceImpl() {
		theManager = new TagLibraryManager();
		theManager.addTld(ru.nuzmsh.web.tags.AbstractFieldTag.class, "msh");
		theManager.addTld(ru.ecom.web.tags.EntityWebTrailTag.class, "ecom");
		theJspFileHelper = new JspFileHelper();

		try {
			Properties prop = new Properties();
		} catch (Exception e) {

		}
		theJspFileHelper
				.setWorkspaceDir(new File("/home/esinev/workspace/ecom"));
	}

	private TagInfoAdapter convertTagInfo(TagInfo tagInfo) {
		// tag info
		TagInfoAdapter adapter = new TagInfoAdapter();
		adapter.setTagName(tagInfo.getTagName());
		adapter.setDescription(tagInfo.getInfoString());
		//System.out.println(1) ;

		// attributes
		for (TagAttributeInfo at : tagInfo.getAttributes()) {
			TagAttributeInfoAdapter atAdapter = new TagAttributeInfoAdapter();
			atAdapter.setName(at.getName());
			atAdapter.setInfo(at.getName());
			atAdapter.setRequired(at.isRequired());
			atAdapter.setTypeName(at.getTypeName());
			adapter.addAttribute(atAdapter);
		}

		return adapter;
	}

	public EditTagMessage getEditTagMessage(String aGuid, String aJspPath)
			throws IdeModeException {

		try {
			// get values by id
			Map<String, String> values = theJspFileHelper.getAttributeValues(
					aJspPath, aGuid);
			TagInfo tagInfo = theManager.getTagInfo(values.get("__prefix"),
					values.get("__tag"));

			// values
			TagValues tagValues = new TagValues();
			for (Entry<String, String> entry : values.entrySet()) {
				tagValues.setValue(entry.getKey(), entry.getValue());
			}

			// ret
			EditTagMessage ret = new EditTagMessage();
			ret.setTagInfo(convertTagInfo(tagInfo));
			ret.setValues(tagValues);
			return ret;
		} catch (Exception e) {
			throw new IdeModeException(e.getMessage(), e);
		}
	}

	public void saveTag(String aGuid, String aJspPath, TagValues aValues)
			throws IdeModeException {
		try {
			theJspFileHelper.setAttributes(aJspPath, aGuid,
					aValues.getValues(), theManager);
		} catch (Exception e) {
			throw new IdeModeException(e.getMessage(), e);
		}

	}

	public void deleteTag(String aJspPath, String aGuid)
			throws IdeModeException {
		try {
			theJspFileHelper.deleteTag(aJspPath, aGuid);
		} catch (Exception e) {
			throw new IdeModeException(e.getMessage(), e);
		}

	}

	public List listTags() {
		ArrayList<String[]> ret = new ArrayList<String[]>();
		for (TagLibraryInfo lib : theManager.getTagLibrariesInfos()) {
			String prefix = lib.getPrefixString();
			for (TagInfo tag : lib.getTags()) {
				boolean canAdd = false;
				for (TagAttributeInfo at : tag.getAttributes()) {
					if ("guid".equals(at.getName())) {
						canAdd = true;
						break;
					}
				}
				if (canAdd) {
					String name = tag.getTagName();
					String fullname = prefix + ":" + name;
					String description = name + " " + tag.getInfoString();
					ret.add(new String[] { fullname, description });
				}
			}
		}
		
		Collections.sort(ret, new StringArrayComparator()) ;
		return ret;
	}
	
	
	

	public TagInfoAdapter getTagInfo(String aFullTagName) {
		return convertTagInfo(getTagInfoInternal(aFullTagName));
	}

	private TagInfo getTagInfoInternal(String aFullTagName) {
		//System.out.println(8) ;
		StringTokenizer st = new StringTokenizer(aFullTagName, ": \t");
		String prefix = st.nextToken();
		String name = st.nextToken();
		return theManager.getTagInfo(prefix, name);
	}

	public void insertTag(String aInsertMode, String aTagFullnameBad,
			TagValues aValues, String aGuid, String aJspPath)
			throws IdeModeException {
		//System.out.println(9) ;
		try {
			JspFileHelper.InsertOption opt;
			if ("after".equals(aInsertMode)) {
				opt = JspFileHelper.InsertOption.AFTER;
			} else if ("before".equals(aInsertMode)) {
				opt = JspFileHelper.InsertOption.BEFORE;
			} else if ("into".equals(aInsertMode)) {
				opt = JspFileHelper.InsertOption.INTO;
			} else if ("over".equals(aInsertMode)) {
				opt = JspFileHelper.InsertOption.OVER;
			} else {
				throw new IllegalArgumentException(
						"Не поддерживается режим вставки: " + aInsertMode);
			}
			TagInfo tagInfo = getTagInfoInternal(aTagFullnameBad);
			theJspFileHelper.insertNewTag(opt, getPrefix(aTagFullnameBad),
					tagInfo, aValues.getValues(), aJspPath, aGuid);

			// LOG.info("insertTag() "+aValues) ;
			// theJspFileHelper.setAttributes(aJspPath, aGuid, aValues,
			// theManager);
		} catch (Exception e) {
			throw new IdeModeException(e.getMessage(), e);
		}

	}

	private String getPrefix(String aFullTagName) {
		//System.out.println(10) ;
		return new StringTokenizer(aFullTagName, ": \t").nextToken();
	}

	private final TagLibraryManager theManager;

	private final JspFileHelper theJspFileHelper;

	public List listFormProperties(String aFormClass) {
		//System.out.println(11) ;
		try {
			ArrayList<String[]> list = new ArrayList<String[]>();
			Class clazz = Thread.currentThread().getContextClassLoader()
					.loadClass(aFormClass);
			for (Method method : clazz.getMethods()) {
				if ((method.getName().startsWith("get")
						|| method.getName().startsWith("is")) && !isInternalFormMethod(method)) {
					Comment comment = method.getAnnotation(Comment.class);
					String name = PropertyUtil.getPropertyName(method);
					list.add(new String[] { name,
							comment != null ? comment.value() : name });
				}
			}
			Collections.sort(list, new StringArrayComparator()) ;
			return list;
		} catch (Exception e) {
			throw new IllegalStateException("Ошибка: " + e.getMessage(), e);
		}
	}

	private boolean isInternalFormMethod(Method aMethod) {
		//System.out.println(12) ;
		String m = aMethod.getName();
		return m.equals("getClass") || m.equals("getSaveType")
				|| m.equals("getSaveType")
				|| m.equals("isTypeCreate")
				|| m.equals("isViewOnly")
				|| m.equals("getDisabledFieldsIterator")
				|| m.equals("getDefaultFocusField")
				|| m.equals("getDisabledFields")
				|| m.equals("getPrivateFields")
				|| m.equals("isPrivateField")
				|| m.equals("getFormMessages")
				|| m.equals("getPage")
				|| m.equals("getValidationKey")
				|| m.equals("getValidatorResults")
				|| m.equals("getResultValueMap")
				|| m.equals("getServletWrapper")
				|| m.equals("getMultipartRequestHandler")
				|| m.equals("getPrivateValues")
				|| m.equals("getStrutsFormName")
				|| m.equals("getValue")
				;
	}

	public static class StringArrayComparator implements Comparator<String[]> {
		public int compare(String s1[], String[] s2) {
			//System.out.println(13) ;
			int ret = 0 ;
			if(s1!=null && s2!=null && s1.length>1 && s2.length>1 && s1[1]!=null) {
				ret = s1[1].compareTo(s2[1]);
			}
			return ret;
		}
	}
}
