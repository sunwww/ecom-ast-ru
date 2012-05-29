package test.odt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;


import ru.ecom.report.rtf.IPrintFileDriver;
import ru.ecom.report.rtf.OdtPrintFileDriver;
import ru.ecom.report.rtf.RtfPrintException;
import ru.ecom.report.rtf.RtfPrintServiceHelper;
import ru.ecom.report.util.XmlDocument;

import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class TestOdtPrintService {
	
    public static void main(String[] args) throws RtfPrintException {
    	
        RtfPrintServiceHelper service = new RtfPrintServiceHelper();
        File odtFile = new File("/home/stkacheva/test") ;
        System.out.println(odtFile.getAbsolutePath());
        String templateDir = "/home/stkacheva/test" ;
        String workDir ="/home/stkacheva/test" ;
        String id = "medServicies" ;
        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime()) ;
    	SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy") ;
    	System.out.println(format.format(date)) ;
        try {
        	//IPrintFileDriver driver = new OdtPrintFileDriver().newInstance(id, odtFile, "statcard", workDir) ;
            IPrintFileDriver driver = new OdtPrintFileDriver(".odt").newInstance(id, odtFile, "medServicies", workDir) ;
            driver.prepare() ;
            
            InputStream os = new FileInputStream(driver.getOutputFile()) ;
            InputStream is = new FileInputStream(driver.getInputFile()) ;
            Document oldXmlDoc = new SAXBuilder().build(is) ;
	        Document newXmlDoc = new SAXBuilder().build(os) ;
	        Element oldroot = oldXmlDoc.getRootElement();

	        
	        Element newroot = viewStrucXml(oldroot,"") ;
	        newXmlDoc.setRootElement(newroot);	        
	        
	        XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
	        OutputStream outStr = new FileOutputStream(driver.getOutputFile()) ; 
			OutputStreamWriter out = new OutputStreamWriter(outStr, "utf-8") ;
			xmlOut.output(newXmlDoc, out) ;
	        driver.buildFile(false) ;
        } catch (Exception e) {
        	System.out.println(e.getMessage()) ;
        	System.out.println(e) ;
        }
        
    }
    public static void forXml(Element aParent, String aDop) {
    	
    }
    
    public static Element viewStrucXml(Element aElement,String aDop) {
    	String value = aElement.getValue().trim() ; 
		boolean isChildren = isChildrenElement(aElement) ;
		boolean isAttribute = isAttributes(aElement) ;
		boolean isReplaceValue = isReplaceValue(value) ;
		
    	Element convElem = new Element(aElement.getName(),aElement.getNamespacePrefix(),aElement.getNamespaceURI()) ;
    	copyAttribute(aElement, convElem);
    	convElem.setText(aElement.getText()) ;
        String dop1 = aDop + "-" ;
		System.out.print(aDop+aElement.getQualifiedName());
		System.out.println(new StringBuilder() .append("  --> isChildren=")
				.append(isChildren).append(" isAttributes=")
				.append(isAttribute)
//				.append(" isBeginFor:")
//				.append(isBeginFor)
//				.append(" isEndFor:")
//				.append(isEndFor)
				.append(" isReplace:")
				.append(isReplaceValue));
		int forCount = 0;
		Element forE = new Element("forE") ;
		for (Object o : aElement.getChildren()) {
        	Element temp = (Element) o ;
        	String tempValue = temp.getValue() ;
    		boolean isBeginFor = isBeginFor(tempValue) ;
    		boolean isEndFor = isEndFor(tempValue) ;
        	if (forCount==0 && !isBeginFor) {
        		System.out.println("Нет цикла") ;
            	Element newEl = viewStrucXml(temp,dop1)  ;
        		if(newEl!=null) convElem.addContent(newEl);   
        	} else {
        		if (isBeginFor) {
        			System.out.println("Начало цикла") ;
        			forCount = forCount+1 ;
        			
        		} else if (isEndFor) {
        			
        			
        			forCount = forCount-1 ;
        			if (forCount==0) {
        				System.out.println("Окончание цикла") ;
        				listParser(forE,convElem) ;
        			} else {
        				System.out.println("Один из циклов закончился цикла") ;
        			}
        		} else {
        			System.out.println("Продолжение цикла") ;
        			forE.addContent(viewStrucXml(temp,dop1)) ;
        		}
        	}
        }
		return convElem ;
    }
    private static void listParser(Element forE,Element toCopyElement) {
    	//Element el = new Element("result") ;
    	for (int i=1; i<4; i++) {
    		
	    	for (Object o : forE.getChildren()) {
	    		Element elem = (Element) o ;
	    		Element convElem = new Element(elem.getName(),elem.getNamespacePrefix(),elem.getNamespaceURI()) ;
	        	copyAttribute(elem, convElem);
	        	convElem.setText(elem.getText()) ;
	    		toCopyElement.addContent(convElem) ;
	    	}
    	}
    	//return new Element("kk") ;
    }
    private static void copyAttribute(Element aStarting, Element aCopying) {
    	if (aStarting!=null && aCopying!=null) {
    		for (Object o: aStarting.getAttributes()) {
    			//Attribute atr = (Attribute) o ;
    			//Attribute atrNew = new Attribute(atr.getName(),atr.getValue(),3,atr.getNamespace());
    			//aCopying.setAttribute(atrNew) ;
    		}
    	}
    }
    private static String convertText(String aText) {
    	return aText.replace("${", "!!!") ;
    }
    private static boolean isBeginFor(String aValue) {
    	if (aValue!=null && aValue.startsWith("$$FOR{") && aValue.endsWith("}$$")) {
    		return true ;
    	}    	
    	return false;
    }
    private static boolean isEndFor(String aValue) {
    	if (aValue!=null && aValue.equals("$$FOREND")) {
    		return true ;
    	}
    	return false;
    }
    private static boolean isReplaceValue(String aValue) {
    	if (aValue!=null && aValue.indexOf("${")!=-1) {
    		return true ;
    	} 
    	return false;
    }
    private static boolean isChildrenElement(Element aParent) {
    	if (aParent!=null && aParent.getChildren().size()>0) {
    		return true ;
    	} 
    	return false;
    }
    private static boolean isAttributes(Element aParent) {
    	if (aParent!=null && aParent.getAttributes().size()>0) {
    		return true ;
    	} 
    	return false;
    }
    
}
