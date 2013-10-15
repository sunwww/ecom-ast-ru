package util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import exp.ExpIterator;

public class SqlUtil {
	public static void main(String args[]){
		try {
			writeTestSchema("/tmp/riams/testSchema.xml");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void writeTestSchema(String aFileName) throws XMLStreamException{
		
		FileUtil.deleteFile(aFileName);
		XMLStreamWriter xsw = XmlUtil.getXMLWriter(aFileName,"");
	    String schemaNS = "http://www.w3.org/2001/XMLSchema"; 
	    
		xsw.writeStartDocument();
		xsw.writeStartElement("root");
		XmlUtil.writeElementSchema(xsw, "#mySchema");
			XmlUtil.writeStartSchema(xsw, schemaNS, "mySchema");
				XmlUtil.writeSchemaElement(xsw, schemaNS, "root", "any");
				XmlUtil.writeSchemaElement(xsw, schemaNS, "lastname", "string");
			xsw.writeEndElement();
	    xsw.writeEndElement();
		xsw.writeEndDocument();
		xsw.flush();
		xsw.close();
	}
	public static void writeResultSetSchema(ResultSet aRs
			, XMLStreamWriter aXsw, String aSchemaNs, String aRootName
			, String aRowName){
		XmlUtil.writeStartSchema(aXsw, aSchemaNs, "mySchema");
		XmlUtil.writeStartSchemaElement(aXsw, aSchemaNs, aRootName, "");
		try {
			aXsw.writeStartElement("xs:complexType");
			aXsw.writeStartElement("xs:sequence");
			XmlUtil.writeStartSchemaElement(aXsw, aSchemaNs, aRowName, "");
			aXsw.writeStartElement("xs:complexType");
			aXsw.writeStartElement("xs:sequence");
		} catch (XMLStreamException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ResultSetMetaData rsmd;
		String columnName;
		String columnSqlType;
		String columnXsdType;
		try {
			rsmd = aRs.getMetaData();
		    int numberOfColumns = rsmd.getColumnCount();
		    
			for(int ci=1; ci<=numberOfColumns;ci++){
				columnName = rsmd.getColumnName(ci);
				columnSqlType = rsmd.getColumnTypeName(ci);
				columnXsdType = XmlUtil.sqlToXsdType(columnSqlType);
				XmlUtil.writeSchemaElement(aXsw, aSchemaNs, columnName, columnXsdType);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			aXsw.writeEndElement();
			aXsw.writeEndElement();
			aXsw.writeEndElement();
			aXsw.writeEndElement();
			aXsw.writeEndElement();
			aXsw.writeEndElement();
			aXsw.writeEndElement();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//aScemeName=""|"internal"|url
	public static void ResultSetToXml(ResultSet aRs, String aFileName
			, String aRowsName, String aRowName) throws SQLException, XMLStreamException{
		if(!StringUtil.isNotEmpty(aRowsName)) aRowsName = "rows";
		if(!StringUtil.isNotEmpty(aRowName)) aRowName = "row";
		FileUtil.deleteFile(aFileName);
		String tempFileName = aFileName + ".tmp";
	    String schemaNs = "http://www.w3.org/2001/XMLSchema"; 
		XMLStreamWriter xsw = XmlUtil.getXMLWriter(tempFileName,"");
		try {
			xsw.writeStartDocument();
			xsw.writeStartElement("root");
			
				XmlUtil.writeElementSchema(xsw, "#mySchema");
			    writeResultSetSchema(aRs, xsw, schemaNs, aRowsName, aRowName);
		    
			ResultSetMetaData rsmd = aRs.getMetaData();
		    int numberOfColumns = rsmd.getColumnCount();

			xsw.writeStartElement("xsi:"+aRowsName);
		    
		    String columnName;
		    String columnSqlType;
		    String columnXsdType;
		    String value;
		    int ri =0;
		    for ( ri=1; aRs.next(); ri++) {
				xsw.writeStartElement("row");
		        xsw.writeAttribute("id", ""+ri);
				for(int ci=1; ci<=numberOfColumns;ci++){
					//System.out.println(ci);
					columnName = rsmd.getColumnName(ci);
					columnSqlType = rsmd.getColumnTypeName(ci);
					columnXsdType = XmlUtil.sqlToXsdType(columnSqlType);
					value = aRs.getString(ci);
					if(value==null) value="";
					
					if(columnXsdType.equalsIgnoreCase("boolean")) value=value.equalsIgnoreCase("t") ? "1" : "0";
			        
					xsw.writeStartElement(columnName);
					try {
			        xsw.writeCharacters(value==null? "" : value);
					}
					catch (Exception e) {
						xsw.writeCharacters("");
						FileUtil.writeErrorFile(e,getErrorFileName());
					}
			        xsw.writeEndElement();
			      }
				xsw.writeEndElement();
				
			}
		    xsw.writeEndDocument();
			xsw.writeEndDocument();
			System.out.println("LENGHT RS SQL="+ri);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			
			FileUtil.writeErrorFile(e,getErrorFileName());
		}
		finally {
			xsw.flush();
			xsw.close();
			FileUtil.rename(tempFileName, aFileName);
		}
	}
	public static String getLIST(String aColumns, String aAlias){
		StringBuilder res = new StringBuilder() ;
		res.append("ARRAY_TO_STRING(ARRAY_AGG("+aColumns+"),',')");
		if(StringUtil.isNotEmpty(aAlias)) res.append(" AS "+aAlias);
		return res.toString();
	}
	public static String getDISTINCT(String aClause){
		String res = "" ;
		if(StringUtil.isNotEmpty(aClause)) res=" DISTINCT ON ("+aClause+")";
		return res ;
	}
	public static String getISNULL(String aArg1){
		return getISNULL(aArg1, "", false, "");
	}
	public static String getISNULL(String aArg1, String aArg2){
		return getISNULL(aArg1, aArg2, false, "");
	}
	public static String getISNULL(String aArg1, String aArg2
			, Boolean aIsString){
		return getISNULL(aArg1, aArg2, aIsString, "");
	}
	public static String getISNULL(String aArg1, String aArg2
			, Boolean aIsString, String aAlias){
		StringBuilder res = new StringBuilder() ;
		aIsString = aIsString==null ? false : aIsString ;
		if(StringUtil.isNotEmpty(aArg1)){
			res.append("CASE WHEN ");
			
			if(isTrue(aIsString)) res.append("("+aArg1+" IS NULL OR ''||"+aArg1+"='')");
			else res.append(aArg1+" IS NULL");

			aArg2=prepareSetValue(aArg2, aIsString);
			
			res.append(" THEN ");
			res.append(aArg2);
			res.append(" ELSE ");
			res.append(isTrue(aIsString)? "''||" : "");
			res.append(aArg1+" END");
			if(StringUtil.isNotEmpty(aAlias)) res.append(" AS "+aAlias);
		}
		return res.toString();
		
	}
	public static String getIFNULL(String aArg1, String aArg2, String aArg3){
		return getIFNULL(aArg1, aArg2, aArg3, false, "");
	}
	public static String getIFNULL(String aArg1, String aArg2, String aArg3
			, Boolean aIsString, String aAlias){
		StringBuilder res = new StringBuilder() ;
		aIsString = aIsString==null ? false : aIsString ;
		if(StringUtil.isNotEmpty(aArg1)){
			res.append("CASE WHEN ");
			
			if(isTrue(aIsString)) res.append("("+aArg1+" IS NULL OR ''||"+aArg1+"='')");
			else res.append(aArg1+" IS NULL");

			aArg2=prepareSetValue(aArg2, aIsString);
			aArg3=prepareSetValue(aArg3, aIsString);
			
			res.append(" THEN ");
			res.append(aArg3);
			res.append(" ELSE "+aArg2+" END");
			if(StringUtil.isNotEmpty(aAlias)) res.append(" AS "+aAlias);
		}
		return res.toString();
		
	}
	public static String prepareSetValue(String aValue, boolean aIsString){
		if(!StringUtil.isNotEmpty(aValue)) aValue=isTrue(aIsString)?"''":null; 
		else if(StringUtil.isNumber(aValue)) aValue=isTrue(aIsString)?"'"+aValue+"'":aValue;
		return aValue;
	}
	public static boolean isTrue(Boolean aBoolean){ 
			Boolean res=aBoolean;
			if(aBoolean==null) res=false;
			return res;  
		}  	
	public static String getErrorFileName(){
		return "SqlUtilError.txt";
	}	

}
