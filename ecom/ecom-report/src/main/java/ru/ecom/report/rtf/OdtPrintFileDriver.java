package ru.ecom.report.rtf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ru.ecom.report.excel.PoiCellUtil;
import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.ReplaceHelper;
import ru.ecom.report.replace.SetValueException;
import ru.nuzmsh.util.zip.JarUtil;

public class OdtPrintFileDriver implements IPrintFileDriver {
	
	public OdtPrintFileDriver(String aExtension) {
		theExtension = aExtension ;
	}
	public IPrintFileDriver newInstance(String id, File templateDir, String key, String theWorkDir) {
		OdtPrintFileDriver d = new OdtPrintFileDriver(theExtension) ;
		d.theId = id ;
		d.theTemplateDir = templateDir ;
		d.theKey = key ;
		d.theWorkDir = new File(theWorkDir) ;
		//d.theExtension = ".odt" ;
		return d;
	}
	
	public void buildFile() {
		File dir = new File(theWorkDir,getTempFileName().append("-out").toString()) ;
		JarUtil.makeArchive(new File(theWorkDir,getTempFileName().append(theExtension).toString()), dir) ;
	}
	public void buildFile(boolean aRemovedTempFile) {
		buildFile() ;
		if (aRemovedTempFile) {
			deleteDir(new File(theWorkDir,getTempFileName().toString())) ;
			deleteDir(new File(theWorkDir,getTempFileName().append("-out").toString())) ;
			
		}
		//new File(theWorkDir,theKey+"-"+theId).deleteOnExit() ;
	}
	
	private void deleteDir(File aFile) {
	   if(!aFile.exists()) return;
	    if(aFile.isDirectory()) {
	      for(File childFile : aFile.listFiles()) deleteDir(childFile);
	      aFile.delete();
	    } else {
	    	aFile.delete();
	    }		
	}

	public String getEncoding() {
		return "utf-8" ;
	}

	public File getInputFile() {
		File dir = new File(theWorkDir,getTempFileName().toString()) ;
		return new File(dir, "content.xml") ;
	}
	
	public File getOutputFile() {
		File dir = new File(theWorkDir,getTempFileName().append("-out").toString()) ;
		return new File(dir, "content.xml") ;
	}
	public File getStyleInputFile() {
		File dir = new File(theWorkDir,getTempFileName().toString()) ;
		return new File(dir, "styles.xml") ;
	}

	public File getStyleOutputFile() {
		File dir = new File(theWorkDir,getTempFileName().append("-out").toString()) ;
		return new File(dir, "styles.xml") ;
	}

	public String getResultFilename() {
		return new File(getTempFileName().append(theExtension).toString()).getName() ;
	}
	private StringBuilder getTempFileName() {
		return new StringBuilder().append(theKey)
				.append("-").append(theLogin).append("-")
				.append(theId) ;
	}

	public boolean isAccept(File aDir, String aKeyName) {
		return new File(aDir, aKeyName+theExtension).exists() ;
	}

	public boolean isRtfReplaceMode() {
		return false;
	}

	
	public void prepare() {
		prepare("") ;
		prepare("-out") ;
	}

	private void prepare(String aDirSuffix) {
		File archiveFile = new File(theTemplateDir, theKey+theExtension) ;
		File dir = new File(theWorkDir,getTempFileName().append(aDirSuffix).toString()) ;
		//log("dir=" +dir.getAbsolutePath()) ;
		//log("arch="+archiveFile.getAbsolutePath()) ;
		dir.mkdirs() ;
		try {
			log(archiveFile.getPath()) ;
			log(dir.getPath()) ;
			JarUtil.extract(archiveFile,dir) ;
		} catch (IOException e) {
			throw new RuntimeException(e) ;
		}
	}
	
	public void print(ReplaceHelper aReplaceHelper, IValueGetter aValueGetter) throws RtfPrintException {
		//InputStream os = new FileInputStream(getOutputFile()) ;
		try {
			InputStream is = new FileInputStream(getInputFile()) ;
			Document oldXmlDoc = new SAXBuilder().build(is) ;
			Document newXmlDoc = new Document() ;
			Element oldroot = oldXmlDoc.getRootElement();
			
			
			Element newroot = viewStrucXml(oldroot,aValueGetter, aReplaceHelper,false) ;
			newXmlDoc.setRootElement(newroot);	        
			
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			OutputStream outStr = new FileOutputStream(getOutputFile()) ; 
			OutputStreamWriter out = new OutputStreamWriter(outStr, getEncoding()) ;
			xmlOut.output(newXmlDoc, out) ;
		} catch(Exception e) {
			
		} finally {
			
		}
		try {
	        InputStream is = new FileInputStream(getStyleInputFile()) ;
	        Document oldXmlDoc = new SAXBuilder().build(is) ;
	        Document newXmlDoc = new Document() ;
	        Element oldroot = oldXmlDoc.getRootElement();
	
	        
	        Element newroot = viewStrucXml(oldroot,aValueGetter, aReplaceHelper,false) ;
	        newXmlDoc.setRootElement(newroot);	        
	        
	        XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
	        OutputStream outStr = new FileOutputStream(getStyleOutputFile()) ; 
			OutputStreamWriter out = new OutputStreamWriter(outStr, getEncoding()) ;
			xmlOut.output(newXmlDoc, out) ;
		} catch(Exception e) {
			
		}
	}
	
	
	
	
	private static void setTextByTag(Element aEl, String aText) {
		String[] sp = aText.split("<text:line-break/>") ;
		for (int i=0;i<sp.length;i++) {
			aEl.addContent(sp[i]) ;
			//aEl.addContent(new Text("                                  ")) ;
			if (i<(sp.length-1)) aEl.addContent(new Element("line-break","text","urn:oasis:names:tc:opendocument:xmlns:text:1.0")) ;
		}
	}
	private static void log(Object aObj) {
		//System.out.println(aObj) ;
	}
	public static Element viewStrucXml(Element aElement, IValueGetter aValueGetter,ReplaceHelper aReplaceHelper,boolean aNotEditText) {
    	//String value = aElement.getValue().trim() ; 
		//boolean isChildren = isChildrenElement(aElement) ;
		//boolean isAttribute = isAttributes(aElement) ;
		//boolean isReplaceValue = isReplaceValue(value) ;
		
    	Element convElem = new Element(aElement.getName(),aElement.getNamespacePrefix(),aElement.getNamespaceURI()) ;
    	//log(aElement.getName()+"--"+aElement.getNamespacePrefix()+"--"+aElement.getNamespaceURI());
    	copyAttribute(aElement, convElem);
    	String text = aNotEditText?aElement.getText():convertText(aElement.getText(),aValueGetter, aReplaceHelper) ;
    	//Text text = new Text() ;
    	//convElem.addContent() ;
    	setTextByTag(convElem,text) ;
    	
        //String dop1 = aDop + "-" ;
		//System.out.print(aDop+aElement.getQualifiedName());
		//log(new StringBuilder() .append("  --> isChildren=")
		//		.append(isChildren).append(" isAttributes=")
		//		.append(isAttribute)
		//		.append(" isReplace:")
		//		.append(isReplaceValue));
		int forCount = 0;
		Element forE = new Element("forE") ;
		String forText = null ;
		for (Object o : aElement.getChildren()) {
        	Element temp = (Element) o ;
        	String tempValue = temp.getValue() ;
    		boolean isBeginFor = isBeginFor(tempValue) ;
    		boolean isEndFor = isEndFor(tempValue) ;
    		
        	if (forCount==0 && (!isBeginFor||aNotEditText) ) {
        		log("Нет цикла.  ---"+aNotEditText) ;
            	Element newEl = viewStrucXml(temp, aValueGetter, aReplaceHelper,aNotEditText)  ;
        		if(newEl!=null) convElem.addContent(newEl);   
        	} else {
        		if (isBeginFor) {
        			log("Начало цикла>>>>>>>>>>>>>>") ;
        			log("forText="+tempValue) ;
        			forCount = forCount+1 ;
        			if (forCount>1) {
        				forE.addContent(viewStrucXml(temp, aValueGetter, aReplaceHelper,true)) ;
        			} else {
        				forText = tempValue ;
        				forE = new Element("forE") ;
        			}
        		} else if (isEndFor) {
        			
        			
        			forCount = forCount-1 ;
        			if (forCount==0) {
        				log("<<<<<<<<<<<<Окончание цикла") ;
        				log(">>>>>>>>>>>Начало обратотки цикла") ;
        				try {
        					listParser(forE,convElem, aValueGetter,forText, aReplaceHelper) ;
        					forText = null ;
        					forE = new Element("forE") ;
        				} catch (Exception e) {
        					convElem.setText("ОШИБКА!!!!!!!!!!!") ;
        				}
        				log("<<<<<<<<<<Окончание обратотки цикла") ;
        			} else {
        				forE.addContent(viewStrucXml(temp, aValueGetter, aReplaceHelper,true)) ;
        				log("Один из циклов закончился цикла") ;
        			}
        		} else {
        			log("Продолжение цикла") ;
        			forE.addContent(viewStrucXml(temp, aValueGetter, aReplaceHelper,true)) ;
        		}
        	}
        }
		return convElem ;
    }
    private static void listParser(Element aForE,Element toCopyElement, IValueGetter aValueGetter, String aStr,ReplaceHelper aReplaceHelper) throws SetValueException {
    	//Element el = new Element("result") ;
    	log("ЦИКЛ========") ;
    	log("forE="+aForE.toString()) ;
    	log("param="+aStr);
    	StringTokenizer st = new StringTokenizer(aStr, " \t:");
        st.nextToken() ;
        String name = st.nextToken() ;
        String expression = st.nextToken() ;
        List list = (List) aValueGetter.getValue(expression) ;
        //aValueGetter.set(name, list) ;
        
    	for (Object val:list) {
    		//log(name) ;
    		//log(val) ;
    		aValueGetter.set(name, val);
    		int forCount = 0 ;
    		String forText = null ;
    		Element forE = new Element("forE") ;
	    	for (Object o : aForE.getChildren()) {
	        	Element temp = (Element) o ;
	        	String tempValue = temp.getValue() ;
	    		boolean isBeginFor = isBeginFor(tempValue) ;
	    		boolean isEndFor = isEndFor(tempValue) ;
	        	if (forCount==0 && !isBeginFor) {
	        		Element elem = (Element) o ;
		    		Element convElem = viewStrucXml( elem,  aValueGetter, aReplaceHelper, false) ;
		    		toCopyElement.addContent(convElem) ;   
	        	} else {
	        		if (isBeginFor) {
	        			log("Начало цикла") ;
	        			forCount = forCount+1 ;
	        			
	        			if (forCount>1) {
	        				forE.addContent(viewStrucXml(temp, aValueGetter, aReplaceHelper,true)) ;
	        			} else {
	        				forText = tempValue ;
	        				forE = new Element("forE") ;
	        			}
	        		} else if (isEndFor) {
	        			
	        			
	        			forCount = forCount-1 ;
	        			if (forCount==0) {
	        				log("Окончание цикла") ;
	        				try {
	        					listParser(forE,toCopyElement, aValueGetter,forText, aReplaceHelper) ;
	        					forText = null ;
	        					forE = new Element("forE") ;
	        				} catch (Exception e) {
	        					//convElem.setText("ОШИБКА!!!!!!!!!!!") ;
	        				}
	        			} else {
	        				forE.addContent(viewStrucXml(temp, aValueGetter, aReplaceHelper,true)) ;
	        				log("Один из циклов закончился цикла") ;
	        			}
	        		} else {
	        			log("Продолжение цикла") ;
	        			forE.addContent(viewStrucXml(temp, aValueGetter, aReplaceHelper,true)) ;
	        		}
	        	}
	    		
	    		
	    		
	    	}
	    	
    	}
    }
    private static void copyAttribute(Element aStarting, Element aCopying) {
    	if (aStarting!=null && aCopying!=null) {
    		for (Object o: aStarting.getAttributes()) {
    			Attribute atr = (Attribute) o ;
    			Attribute atrNew = new Attribute(atr.getName(),atr.getValue(),3,atr.getNamespace());
    			aCopying.setAttribute(atrNew) ;
    		}
    	}
    }
    private static String convertText(String aText, IValueGetter aValueGetter,ReplaceHelper aReplaceHelper)  {
    	try {
    		//log("TEXT-->"+aText) ;
    		return aReplaceHelper.replaceWithValues(aText, aValueGetter).toString() ;
    	} catch (Exception e) {
			return new StringBuilder().append("ОШИБКА !!!: ").append(aText).append(" ->").append(e).toString() ;
		}    	
    }
    public static boolean isBeginFor(String aValue) {
    	if (aValue!=null && aValue.startsWith("$$FOR") && aValue.endsWith("$$")) {
    		return true ;
    	}    	
    	return false;
    }
    public static boolean isEndFor(String aValue) {
    	if (aValue!=null && aValue.equals("$$FOREND")) {
    		return true ;
    	}
    	return false;
    }

	
	
	/** Логин */
	public String getLogin() {return theLogin;}
	public void setLogin(String aLogin) {theLogin = aLogin;}

	/** Логин */
	private String theLogin;
    private String theExtension ;
	private String theId ;
	private File theTemplateDir ;
	private String theKey ;
	private File theWorkDir ;

}
