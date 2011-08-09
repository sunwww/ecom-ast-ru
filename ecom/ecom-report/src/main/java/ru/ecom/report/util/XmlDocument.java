package ru.ecom.report.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XmlDocument {
	public XmlDocument() throws ParserConfigurationException {

    	theDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
    	
    	theDocumentBuilderFactory.setValidating(false);
    	theDocumentBuilderFactory.setNamespaceAware(false);
    	
    	theDocumentBuilder = theDocumentBuilderFactory.newDocumentBuilder();
    	theDocument = theDocumentBuilder.newDocument();
	}
	
	/** DocumentBuilderFactory */
	public DocumentBuilderFactory getDocumentBuilderFactory() {return theDocumentBuilderFactory;}
	public void setDocumentBuilderFactory(DocumentBuilderFactory aDocumentBuilderFactory) {theDocumentBuilderFactory = aDocumentBuilderFactory;}

	/** DocumentBuilder */
	public DocumentBuilder getDocumentBuilder() {return theDocumentBuilder;}
	public void setDocumentBuilder(DocumentBuilder aDocumentBuilder) {theDocumentBuilder = aDocumentBuilder;}

	/** Document */
	public Document getDocument() {return theDocument;}
	public void setDocument(Document aDocument) {theDocument = aDocument;}

	/** Document */
	private Document theDocument;
	/** DocumentBuilder */
	private DocumentBuilder theDocumentBuilder;
	/** DocumentBuilderFactory */
	private DocumentBuilderFactory theDocumentBuilderFactory;
	
    public Element newElement(Node parent, String name, String value) {
        Element newElement = theDocument.createElement(name);
        if (parent != null) {
            parent.appendChild(newElement);
        }
        if (value != null) {
            newElement.appendChild(
                theDocument.createTextNode(value)
            );
        }
        return newElement;
    }

    public Attr newAttribute(Node parent, String name, String value) {
        Attr newAttribute = theDocument.createAttribute(name);
        if (parent != null) {
            parent.getAttributes().setNamedItem(newAttribute);
        }
        if (value != null) {
            newAttribute.setValue(value);
        }
        return newAttribute;
    }	
	
    /**
     * Сохранение XML-документа в файл. 
     */
    public void saveDocument(File file) throws TransformerException {
    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
    	Transformer transformer = transformerFactory.newTransformer();
    	
    	transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
    	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    	
    	transformer.transform(
    			new DOMSource(theDocument), new StreamResult(file)
    	);
    }
    public void openDocument(File file) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        transformer.transform(
            new DOMSource(theDocument), new StreamResult(file)
        );
    }

}
