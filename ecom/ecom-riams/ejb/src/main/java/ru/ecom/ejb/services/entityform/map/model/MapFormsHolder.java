package ru.ecom.ejb.services.entityform.map.model;

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

public class MapFormsHolder {
	
	public MapFormInfo getFormInfo(String aFormClassname) {
		return theForms.get(aFormClassname);
	}
	
	public void putFormInfo(MapFormInfo aInfo) {
		if(theForms.containsKey(aInfo.getName())) {
			throw new IllegalStateException("Описание формы "+aInfo.getName()+" встречается больше одного раза в файле mapforms.xml") ;
		}
		theForms.put(aInfo.getName(), aInfo);
	}
	
	public void save(OutputStream aOut) throws IOException {
		throw new IllegalStateException("Еще не поддерживается") ;
/*		Document doc = new Document() ;
		
		Element root = new Element("forms") ;
		for(MapFormInfo form : theForms.values()) {
			Element formElm = new Element("form") ;
			formElm.setAttribute("name", form.getName());
			formElm.setAttribute("securityPrefix", form.getSecurityPrefix());
			
			// persist
			if(form.getEntityFormPersistance()!=null) {
				Element persistElm = new Element("persist") ;
				persistElm.setAttribute("clazz", form.getEntityFormPersistance().getClazz());
				formElm.addContent(persistElm);
			}
			
			root.addContent(formElm);
		}
		doc.setRootElement(root);
		
		XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
		OutputStreamWriter out = new OutputStreamWriter(aOut, "utf-8") ;
		xmlOut.output(doc, out) ; */
	}

	public void load(InputStream aIn) throws UnsupportedEncodingException, JDOMException, IOException {
		Document doc = new SAXBuilder().build(new InputStreamReader(aIn, "utf-8")) ;
		theForms.clear();
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
		for(int i=0;i<list.size();i++) {
			ret[0] = list.get(i);
		}
		return ret ;
	}
	private TreeMap<String, MapFormInfo> theForms = new TreeMap<String, MapFormInfo>() ;
}
