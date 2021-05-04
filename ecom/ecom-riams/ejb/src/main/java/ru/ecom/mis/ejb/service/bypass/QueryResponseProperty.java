package ru.ecom.mis.ejb.service.bypass;

/**
 * Свойство для вывода на экран
 * 
 */
public class QueryResponseProperty {
    public QueryResponseProperty(String aProperty, String aTitle) {
        property = aProperty ;
        title = aTitle ;
    }

    /** Свойство */
    public String getProperty() { return property ; }
    public void setProperty(String aProperty) { property = aProperty ; }

    /** Название */
    public String getTitle() { return title ; }
    public void setTitle(String aTitle) { title = aTitle ; }

    /** Название */
    private String title ;
    /** Свойство */
    private String property ;
}
