package ru.ecom.ejb.xml;

import org.jdom.Document;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Element;
import ru.ecom.report.util.XmlDocument;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

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
		tr.setOutputProperty(OutputKeys.INDENT,"yes");
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

	/** Создаем файл из документа */
	public static String createXmlFile(org.jdom.Element aElement, String aFileName) {
		if (aElement == null) {
			//log.error("no data for create file " + aFileName);
			return null;
		}
		try {
			XMLOutputter outputter = new XMLOutputter();
			OutputStreamWriter fwrt = new OutputStreamWriter(new FileOutputStream(aFileName), Charset.forName("windows-1251"));
			Document pat = new Document(aElement);
			outputter.setFormat(org.jdom.output.Format.getPrettyFormat().setEncoding("windows-1251"));
			outputter.output(pat, fwrt);
			fwrt.close();
			return aFileName;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println("Someshing happened strange!!!");
			return null;
		}
	}

}
