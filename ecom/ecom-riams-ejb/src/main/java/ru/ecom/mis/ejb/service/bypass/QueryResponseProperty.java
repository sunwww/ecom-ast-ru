package ru.ecom.mis.ejb.service.bypass;

/**
 * Свойство для вывода на экран
 * 
 */
public class QueryResponseProperty {
    public QueryResponseProperty(String aProperty, String aTitle) {
        theProperty = aProperty ;
        theTitle = aTitle ;
    }

    /** Свойство */
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Название */
    public String getTitle() { return theTitle ; }
    public void setTitle(String aTitle) { theTitle = aTitle ; }

    /** Название */
    private String theTitle ;
    /** Свойство */
    private String theProperty ;
}
