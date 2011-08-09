package ru.ecom.expomc.ejb.services.exportformat.util;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

/**
 * User: ikouzmin
 * Date: 08.03.2007
 * Time: 15:03:24
 */
public class Xslt {

    public static String parseXsl(String xsltString) {
        if (!xsltString.startsWith("<xsl:stylesheet")) {
            xsltString = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                    xsltString +
                    "</xsl:stylesheet>\n";
        }
        if (!xsltString.startsWith("<?xml")) {
            xsltString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xsltString;
        }
        return xsltString;
    }

    public static void transform(StreamSource xml, StreamSource xsl, Result xmlOut) throws TransformerException {
        // Создание преобразования
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(xsl);
        transformer.transform(xml, xmlOut);
    }
}
