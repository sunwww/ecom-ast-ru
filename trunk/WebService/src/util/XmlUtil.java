package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;


public class XmlUtil {
	public static void main(String args[]) throws Exception{
		String localName;
		int eventType;
		String value;
		String fileName = SystemUtil.getRiamsDsConf();
		XMLStreamReader xsr = XmlUtil.getXMLReader(fileName);
		while(xsr.hasNext()) {
		    xsr.next();
		    eventType = xsr.getEventType();
		    System.out.println(XmlUtil.getEventTypeString(eventType));
		    if(xsr.isStartElement()){
			    localName = xsr.getLocalName();
			    System.out.println(localName);		    	
		    }
		    if(xsr.isCharacters()){
		    	value = xsr.getText();
		    	System.out.println(value);
		    }

		}
		
	}
	public static void writeElementSchema(XMLStreamWriter aXsw, String aSchema){
		try {
			aXsw.writeAttribute("xmlns:xs", "http://www.w3.org/2000/10/XMLSchema");
			aXsw.writeAttribute("xmlns:xsi","urn:rows");	//FIXME!!!
			//aXsw.writeAttribute("xmlns:xsi", "http://www.w3.org/2000/10/XMLSchema-instance");
			//aXsw.writeAttribute("xsi:noNamespaceSchemaLocation", aSchema);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void writeStartSchema(XMLStreamWriter aXsw, String aSchemaNamespace, String aSchemaId){
		try {
			aXsw.writeStartElement("xs","schema",aSchemaNamespace);
			aXsw.writeAttribute("id", aSchemaId);
			
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void writeStartSchemaElement(XMLStreamWriter aXsw, String aSchemaNamespace
			, String ElementName, String ElementType){
	    try {
			aXsw.writeStartElement("xs", "element",aSchemaNamespace);
	    	if(StringUtil.isNotEmpty(ElementName)) aXsw.writeAttribute("name", ElementName);
	    	if(StringUtil.isNotEmpty(ElementType)) aXsw.writeAttribute("type", "xs:"+ElementType);
	    } catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void writeSchemaElement(XMLStreamWriter aXsw
			, String aSchemaNamespace
			, String ElementName, String ElementType){
	    try {
	    	writeStartSchemaElement(aXsw, aSchemaNamespace, ElementName, ElementType);
	    	aXsw.writeEndElement();
	    } catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void writeSchemaStringRestriction(XMLStreamWriter aXsw
			, int aMinLength, int aMaxLength){
		try {
			aXsw.writeStartElement("xs:simpleType");
				aXsw.writeStartElement("xs:restriction");
					aXsw.writeAttribute("base","xs:string");
						if(StringUtil.isNotEmpty(aMinLength)) {
							aXsw.writeStartElement("xs:minLength",""+aMinLength);
							aXsw.writeEndElement();
						}
						if(StringUtil.isNotEmpty(aMaxLength)) {
							aXsw.writeStartElement("xs:maxLength",""+aMaxLength);
							aXsw.writeEndElement();
						}						
						aXsw.writeEndElement();
				aXsw.writeEndElement();
			aXsw.writeEndElement();
		  } catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static XMLStreamReader getXMLReader(String aFileName) throws XMLStreamException{
		XMLStreamReader xsr = null;
		if(aFileName!=null) {
			XMLInputFactory f = XMLInputFactory.newInstance();
			FileReader fr;
			try {
				fr = new FileReader(aFileName);
				BufferedReader br = new BufferedReader(fr);
				xsr = f.createXMLStreamReader(br);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				
			}
		}
		return xsr;
	}

	public static XMLStreamWriter getXMLWriter(String aFileName, String aCodePage){
		XMLStreamWriter xsw = null;
		if(StringUtil.isNotEmpty(aFileName)){
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			BufferedWriter bw = FileUtil.getBufferedWriter(aFileName, aCodePage);
			try {
				xsw = factory.createXMLStreamWriter(bw);
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return xsw;
	}

	public final static String getEventTypeString(int eventType) {
	    switch (eventType) {
	        case XMLEvent.START_ELEMENT:
	            return "START_ELEMENT";
	        case XMLEvent.END_ELEMENT:
	            return "END_ELEMENT";
	        case XMLEvent.PROCESSING_INSTRUCTION:
	            return "PROCESSING_INSTRUCTION";
	        case XMLEvent.CHARACTERS:
	            return "CHARACTERS";
	        case XMLEvent.COMMENT:
	            return "COMMENT";
	        case XMLEvent.START_DOCUMENT:
	            return "START_DOCUMENT";
	        case XMLEvent.END_DOCUMENT:
	            return "END_DOCUMENT";
	        case XMLEvent.ENTITY_REFERENCE:
	            return "ENTITY_REFERENCE";
	        case XMLEvent.ATTRIBUTE:
	            return "ATTRIBUTE";
	        case XMLEvent.DTD:
	            return "DTD";
	        case XMLEvent.CDATA:
	            return "CDATA";
	        case XMLEvent.SPACE:
	            return "SPACE";
	    }
	    return "UNKNOWN_EVENT_TYPE " + "," + eventType;
	}
	public static String sqlToXsdType(String aSqlType){
		String ret="";
		if(aSqlType.equalsIgnoreCase("BigInt")) ret="long";
		else if(aSqlType.equalsIgnoreCase("Binary")) ret="base64Binary";
		else if(aSqlType.equalsIgnoreCase("Bit")) ret="boolean";
		else if(aSqlType.equalsIgnoreCase("Blob")) ret="base64Binary";
		else if(aSqlType.equalsIgnoreCase("Bool")) ret="boolean";
		else if(aSqlType.equalsIgnoreCase("Char")) ret="string";
		else if(aSqlType.equalsIgnoreCase("Clob")) ret="string";
		else if(aSqlType.equalsIgnoreCase("Date")) ret="date";
		else if(aSqlType.equalsIgnoreCase("DateTime")) ret="dateTime";
		else if(aSqlType.equalsIgnoreCase("Decimal")) ret="decimal";
		else if(aSqlType.equalsIgnoreCase("Float")) ret="float";
		else if(aSqlType.equalsIgnoreCase("Int")) ret="int";
		else if(aSqlType.equalsIgnoreCase("Integer")) ret="int";
		else if(aSqlType.equalsIgnoreCase("Int4")) ret="short";
		else if(aSqlType.equalsIgnoreCase("Int8")) ret="int";
		else if(aSqlType.equalsIgnoreCase("Money")) ret="decimal";
		else if(aSqlType.equalsIgnoreCase("Numeric")) ret="decimal";
		else if(aSqlType.equalsIgnoreCase("RowId")) ret="base64Binary";
		else if(aSqlType.equalsIgnoreCase("SmallInt")) ret="short";
		else if(aSqlType.equalsIgnoreCase("Text")) ret="string";
		else if(aSqlType.equalsIgnoreCase("Time")) ret="time";
		else if(aSqlType.equalsIgnoreCase("TimeStamp")) ret="base64Binary";
		else if(aSqlType.equalsIgnoreCase("VarBinary")) ret="base64Binary";
		else if(aSqlType.equalsIgnoreCase("VarChar")) ret="string";
		else if(aSqlType.equalsIgnoreCase("Unknown")) ret="string";
		else ret="any";
		return ret;
		
	}

}
