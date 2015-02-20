package ru.ecom.ejb.xml;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

import ru.ecom.report.util.XmlDocument;

public class XmlUtil {

	public static String namePackage(String aPackage) {
		if (aPackage==null||aPackage.equals("")) return "01" ;
		if (aPackage.length()==1) return "0"+aPackage ;
		return aPackage.substring(aPackage.length()-2,aPackage.length());
	}

	public static void recordElementInDocumentXml(XmlDocument aXmlDocument, Element aMainNode, String aNameElement,Object aValue, boolean aIsRequired,String aValueDefault){
		String value = XmlUtil.getStringValue(aValue) ;
		if (!aIsRequired && (aValue==null || value.equals(""))) {
			
		} else {
			if (aIsRequired && (aValue==null || value.equals(""))) value=aValueDefault;
			aXmlDocument.newElement(aMainNode,aNameElement,value) ;
		}
	}

	public static void saveXmlDocument(XmlDocument aXmlDocument,File aOutFile) throws TransformerFactoryConfigurationError, TransformerException {
		Transformer tr = TransformerFactory.newInstance().newTransformer() ;
		tr.setOutputProperty(OutputKeys.ENCODING, "cp1251") ;
		StreamResult sr = new StreamResult(aOutFile) ;
		tr.transform(new DOMSource(aXmlDocument.getDocument()), sr) ;
	}

	public static String formatTime(Object aTime) {
		//return (""+aTime).replace(":", "-") ;
		return (""+aTime) ;
	}

	public static String getStringValue(Object aValue) {
		return aValue!=null?""+aValue:"" ;
	}

}
