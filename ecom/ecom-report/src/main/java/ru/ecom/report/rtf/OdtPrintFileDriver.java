package ru.ecom.report.rtf;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.ReplaceHelper;
import ru.ecom.report.replace.SetValueException;
import ru.nuzmsh.util.zip.JarUtil;

import java.io.*;
import java.util.List;
import java.util.StringTokenizer;

public class OdtPrintFileDriver implements IPrintFileDriver,IQRPrinter {
	private static final Logger LOG = Logger.getLogger(OdtPrintFileDriver.class);
	
	public OdtPrintFileDriver(String aExtension) {
		theExtension = aExtension ;
	}
	public IPrintFileDriver newInstance(String id, File templateDir, String key, String theWorkDir) {
		OdtPrintFileDriver d = new OdtPrintFileDriver(theExtension) ;
		d.theId = id ;
		d.theTemplateDir = templateDir ;
		d.theKey = key ;
		d.theWorkDir = new File(theWorkDir) ;
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
	private File getStyleInputFile() {
		File dir = new File(theWorkDir,getTempFileName().toString()) ;
		return new File(dir, "styles.xml") ;
	}

	private File getStyleOutputFile() {
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
		dir.mkdirs() ;
		try {
			JarUtil.extract(archiveFile,dir) ;
		} catch (IOException e) {
			throw new RuntimeException(e) ;
		}
	}
	
	public void print(ReplaceHelper aReplaceHelper, IValueGetter aValueGetter) throws RtfPrintException {
		//InputStream os = new FileInputStream(getOutputFile()) ;
		try (InputStream is = new FileInputStream(getInputFile());
			 OutputStream outStr = new FileOutputStream(getOutputFile()) ){
			
			Document oldXmlDoc = new SAXBuilder().build(is) ;
			Document newXmlDoc = new Document() ;
			Element oldroot = oldXmlDoc.getRootElement();
			Element newroot = viewStrucXml(oldroot,aValueGetter, aReplaceHelper,false) ;
			newXmlDoc.setRootElement(newroot);	        
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			OutputStreamWriter out = new OutputStreamWriter(outStr, getEncoding()) ;
			xmlOut.output(newXmlDoc, out) ;
		} catch(Exception e) {
			LOG.error(e.getLocalizedMessage(),e);
		} 
		try(InputStream is = new FileInputStream(getStyleInputFile()) ;
			OutputStream outStr = new FileOutputStream(getStyleOutputFile()) ){
	        Document oldXmlDoc = new SAXBuilder().build(is) ;
	        Document newXmlDoc = new Document() ;
	        Element oldroot = oldXmlDoc.getRootElement();
	        Element newroot = viewStrucXml(oldroot,aValueGetter, aReplaceHelper,false) ;
	        newXmlDoc.setRootElement(newroot);	        
	        XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			OutputStreamWriter out = new OutputStreamWriter(outStr, getEncoding()) ;
			xmlOut.output(newXmlDoc, out) ;
		} catch(Exception e) {
			LOG.error(e.getLocalizedMessage(),e);
		}
	}

	private static void setTextByTag(Element aEl, String aText) {
		String[] sp = aText.split("<text:line-break/>") ;
		for (int i=0;i<sp.length;i++) {
			aEl.addContent(sp[i]) ;
			if (i<(sp.length-1)) aEl.addContent(new Element("line-break","text","urn:oasis:names:tc:opendocument:xmlns:text:1.0")) ;
		}
	}
	
	public static Element viewStrucXml(Element aElement, IValueGetter aValueGetter,ReplaceHelper aReplaceHelper,boolean aNotEditText) {

    	Element convElem = new Element(aElement.getName(),aElement.getNamespacePrefix(),aElement.getNamespaceURI()) ;
    	copyAttribute(aElement, convElem);
		//Milamesher #120 24092018 вставка qr-кода
		String text = "";  //по умолчанию - если не получится создать qr-код, просто будет пустая строка вместо него
		if (aElement.getText().contains(theReplaceString)) {
			String aLine = aElement.getText();
			int index1=aLine.indexOf("("), index2=aLine.indexOf(")");
			if (index1!=-1 && index2!=-1) {
				String params=aLine.substring(index1+1,index2);
				String[] pars = params.split(",");
				if (pars.length==3) {
					String qr_text = convertText(pars[0],aValueGetter, aReplaceHelper);
					try {
						int qr_w = Integer.valueOf(pars[1]);
						int qr_h = Integer.parseInt(pars[2]);
						if (qr_w != 0 && qr_h != 0 && !qr_text.equals("")) {
							theQR_text=qr_text;
							theQR_w=qr_w;
							theQR_h=qr_h;
							text=theReplaceString;
						}
					}
					catch (Exception e) {}
				}
			}
		}
		else text = aNotEditText?aElement.getText():convertText(aElement.getText(),aValueGetter, aReplaceHelper) ;
    	setTextByTag(convElem,text) ;
		int forCount = 0;
		Element forE = new Element("forE") ;
		String forText = null ;
		for (Object o : aElement.getChildren()) {
        	Element temp = (Element) o ;
        	String tempValue = temp.getValue() ;
    		boolean isBeginFor = isBeginFor(tempValue) ;
    		boolean isEndFor = isEndFor(tempValue) ;
    		
        	if (forCount==0 && (!isBeginFor||aNotEditText) ) {
            	Element newEl = viewStrucXml(temp, aValueGetter, aReplaceHelper,aNotEditText)  ;
        		//if(newEl!=null) 
        			convElem.addContent(newEl);   
        	} else {
        		if (isBeginFor) {
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
        				try {
        					listParser(forE,convElem, aValueGetter,forText, aReplaceHelper) ;
        					forText = null ;
        					forE = new Element("forE") ;
        				} catch (Exception e) {
        					convElem.setText("ОШИБКА!!!!!!!!!!!") ;
        				}
        			} else {
        				forE.addContent(viewStrucXml(temp, aValueGetter, aReplaceHelper,true)) ;
        			}
        		} else {
        			forE.addContent(viewStrucXml(temp, aValueGetter, aReplaceHelper,true)) ;
        		}
        	}
        }
		return convElem ;
    }
    private static void listParser(Element aForE,Element toCopyElement, IValueGetter aValueGetter, String aStr,ReplaceHelper aReplaceHelper) throws SetValueException {
    	StringTokenizer st = new StringTokenizer(aStr, " \t:");
        st.nextToken() ;
        String name = st.nextToken() ;
        String expression = st.nextToken() ;
        List list = (List) aValueGetter.getValue(expression) ;

    	for (Object val:list) {
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
	        			LOG.info("Начало цикла") ;
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
	        				LOG.info("Окончание цикла") ;
	        				try {
	        					listParser(forE,toCopyElement, aValueGetter,forText, aReplaceHelper) ;
	        					forText = null ;
	        					forE = new Element("forE") ;
	        				} catch (Exception e) {
	        					//convElem.setText("ОШИБКА!!!!!!!!!!!") ;
	        				}
	        			} else {
	        				forE.addContent(viewStrucXml(temp, aValueGetter, aReplaceHelper,true)) ;
	        				LOG.info("Один из циклов закончился цикла") ;
	        			}
	        		} else {
	        			LOG.info("Продолжение цикла") ;
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
    		return aReplaceHelper.replaceWithValues(aText, aValueGetter).toString() ;
    	} catch (Exception e) {
			return "ОШИБКА !!!: "+aText+" ->"+e;
		}    	
    }
    static boolean isBeginFor(String aValue) {
		return aValue != null && aValue.startsWith("$$FOR") && aValue.endsWith("$$");
	}
	static boolean isEndFor(String aValue) { return "$$FOREND".equals(aValue);}

	/** Логин */
	public String getLogin() {return theLogin;}
	public void setLogin(String aLogin) {theLogin = aLogin;}

    public String getQR_text() { return theQR_text; }

	public int getQR_w() { return theQR_w; }

    public int getQR_h() { return theQR_h; }

	public String getReplaceString() { return theReplaceString; }

	public String getExtension() { return theExtension; }
	/** Логин */
	private String theLogin;
    private String theExtension ;
	private String theId ;
	private File theTemplateDir ;
	private String theKey ;
	private File theWorkDir ;
	/** Текст в qr-коде */
	private static String theQR_text;
	/** Ширина qr-кода */
	private static int theQR_w;
	/** Высота qr-кода */
	private static int theQR_h;
	/** Строка для замены */
	private static String theReplaceString="printQRCode";
}