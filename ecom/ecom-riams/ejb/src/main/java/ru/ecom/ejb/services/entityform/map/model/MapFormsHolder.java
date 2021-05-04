package ru.ecom.ejb.services.entityform.map.model;

import lombok.Getter;
import lombok.Setter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import ru.ecom.ejb.services.entityform.map.model.forclass.EntityFormPersistanceAnnotation;
import ru.ecom.ejb.services.entityform.map.model.forclass.ParentAnnotation;
import ru.ecom.ejb.services.entityform.map.model.forclass.WebTrailAnnotation;
import ru.nuzmsh.util.StringUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.TreeMap;

@Getter
@Setter
public class MapFormsHolder {
	
	public MapFormInfo getFormInfo(String aFormClassname) {
		return forms.get(aFormClassname);
	}
	
	public void putFormInfo(MapFormInfo aInfo) {
		if(forms.containsKey(aInfo.getName())) {
			throw new IllegalStateException("Описание формы "+aInfo.getName()+" встречается больше одного раза в файле mapforms.xml") ;
		}
		forms.put(aInfo.getName(), aInfo);
	}
	
	public void save(OutputStream aOut) throws IOException {
		throw new IllegalStateException("Еще не поддерживается") ;

	}

	public void load(InputStream aIn) throws UnsupportedEncodingException, JDOMException, IOException {
		Document doc = new SAXBuilder().build(new InputStreamReader(aIn, "utf-8")) ;
		forms.clear();
		for(Element formElm : (Collection<Element>)doc.getRootElement().getChildren()) {
			MapFormInfo form = new MapFormInfo() ;
			form.setName(formElm.getAttributeValue("name"));
			form.setSecurityPrefix(formElm.getAttributeValue("securityPrefix"));
			form.setComment(formElm.getAttributeValue("comment"));
			// persist
			Element persistElm = formElm.getChild("persist");
			if(persistElm!=null) {
				EntityFormPersistanceAnnotation persist = new EntityFormPersistanceAnnotation() ;
				persist.setClazz(persistElm.getAttributeValue("clazz"));
				form.setEntityFormPersistance(persist);
			}
			// webTrail
			Element webTrailElement = formElm.getChild("webTrail");
			if(webTrailElement!=null) {
				WebTrailAnnotation webTrail = new WebTrailAnnotation() ;
				webTrail.setComment(webTrailElement.getAttributeValue("comment"));
				webTrail.setView(webTrailElement.getAttributeValue("view")) ;
				webTrail.setNameProperties(toStringArray(webTrailElement.getAttributeValue("nameProperties"))) ;
				if(!StringUtil.isNullOrEmpty(webTrailElement.getAttributeValue("list"))) {
					webTrail.setList(webTrailElement.getAttributeValue("list"));
				}
				form.setWebTrail(webTrail);
			}
			// parent
			Element parentElement = formElm.getChild("parent") ;
			if(parentElement!=null) {
				ParentAnnotation parent = new ParentAnnotation() ;
				parent.setProperty(parentElement.getAttributeValue("property"));
				parent.setParentForm(parentElement.getAttributeValue("parentForm"));
				form.setParent(parent);
			}
			
			putFormInfo(form);
		}
	}

	private String[] toStringArray(String aValue) {
		ArrayList<String> list = new ArrayList<String>() ;
		StringTokenizer st = new StringTokenizer(aValue, " ,;:") ;
		while(st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		String[] ret = new String[list.size()];
		for (String s : list) {
			ret[0] = s;
		}
		return ret ;
	}
	private TreeMap<String, MapFormInfo> forms = new TreeMap<String, MapFormInfo>() ;
}
