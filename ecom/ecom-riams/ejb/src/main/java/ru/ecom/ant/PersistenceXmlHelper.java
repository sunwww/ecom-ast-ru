package ru.ecom.ant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ru.ecom.ejb.services.util.EntityHelper;

public class PersistenceXmlHelper {

	public static void main(String[] args) {
		String template = args[0] ;
		PersistenceXmlHelper copy = new PersistenceXmlHelper(template) ;
		
		for(int i=1; i<args.length; i++) {
			copy.add(args[i]) ;
		}
	}
	
	public PersistenceXmlHelper(String aTemplateFilename) {
		theTemplateFile = new File(aTemplateFilename) ;
	}
	
	
	public void addClassname(String aClass) {
		theClasses.add(aClass);
	}
	
	public void add(String aFilenameToAdd) {
		
		try {
			List<String> classes = listAllEntitiesClassnames(new FileInputStream(aFilenameToAdd)) ;
			for(String clazz : classes) {
				System.out.println(clazz) ;
				theClasses.add(clazz) ;
			}
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
	}
	
	private final Set<String> theClasses = new TreeSet<String>() ;
	
	public void write(String aFilename) {
		try {
			Document templateDocument = loadDocument(theTemplateFile) ;
			Element unit = templateDocument.getRootElement().getChild("persistence-unit") ;
			Element properties = unit.getChild("properties") ;
			if(properties==null) {
				throw new IllegalStateException("Нет элемента persistence-unit/properties") ;
			}
			for(String clazz : theClasses) {
				Element elm = new Element("class") ;
				elm.addContent(clazz) ;
				unit.addContent(5, elm) ;
				
				addCache(properties, clazz) ;
			}
			
			
			Format format = Format.getPrettyFormat() ;
			format.setEncoding("utf-8") ;
			format.setIndent("  ") ;
			XMLOutputter out = new XMLOutputter(format);
			FileWriter fout = new FileWriter(aFilename) ;
			out.output(templateDocument, fout) ;
			fout.close() ;
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
	}

	private boolean isRoot(Class aClass) {
		Class parent = aClass.getSuperclass() ;
		return !parent.isAnnotationPresent(Entity.class) ;
	}
	
    private void addCache(Element aParent, String aClassName) {
		try {
			Class clazz = getClass().forName(aClassName);
			
			if(!theEntityHelper.isCacheable(clazz)) return ;
			
			if(clazz==null) throw new IllegalStateException("Не найден класс "+aClassName) ;
			String name = "hibernate.ejb.classcache."+clazz.getName();
			String value = "transactional" ;
			Element elm = new Element("property") ;
			elm.setAttribute("name", name) ;
			elm.setAttribute("value", value) ;
			if(isRoot(clazz)) {
				aParent.addContent(elm);
			}
			// FIXME disable cache (TEMP)
//			for(Method m : clazz.getDeclaredMethods()) {
//				if(m.isAnnotationPresent(OneToMany.class) || m.isAnnotationPresent(ManyToMany.class)) {
//					Element colElm = new Element("property") ;
//					colElm.setAttribute("name", "hibernate.ejb.collectioncache."
//							+clazz.getName()+"."+PropertyUtil.getPropertyName(m)) ;
//					colElm.setAttribute("value", value);		
//					aParent.addContent(colElm);
//				}
//			}
			
		} catch (Exception e) {
			throw new IllegalStateException("Ошибка при обработке "+aClassName+": "+e,e) ;
		}
		
	}

	public List<String> listAllEntitiesClassnames(InputStream in) {
        try {
            List<String> list = new LinkedList<String>();
            try {
                Document doc = new SAXBuilder().build(in);
                Element rootElement = doc.getRootElement();
                List<Element> persistenceUnits = rootElement.getChildren("persistence-unit");
                for (Element persistenceUnit : persistenceUnits) {
                    List<Element> classes = persistenceUnit.getChildren("class");
                    for (Element clazz : classes) {
                        String className = clazz.getTextTrim();
                        //Class entityClass = theClassLoaderHelper.loadClass(className);
                        list.add(className);
                    }
                }

            } finally {
                in.close();
            }
            return list;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
	
	private static Document loadDocument(File aFile) throws JDOMException, IOException {
		if(!aFile.exists()) throw new FileNotFoundException("Файл "+aFile.getAbsolutePath()+" не найден") ;
		FileInputStream in = new FileInputStream(aFile) ; 
		Document doc = new SAXBuilder().build(in);
		return doc ;
	}
	private final File theTemplateFile ;
	private final EntityHelper theEntityHelper = EntityHelper.getInstance() ; 
}
