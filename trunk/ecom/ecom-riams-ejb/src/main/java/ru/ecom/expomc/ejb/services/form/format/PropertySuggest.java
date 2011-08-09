package ru.ecom.expomc.ejb.services.form.format;

import java.io.Serializable;

/**
 * @author esinev
 * Date: 17.11.2006
 * Time: 11:18:33
 */
public class PropertySuggest implements Serializable {
    public PropertySuggest(String aProperty, String aComment) {
        theProperty = aProperty;
        theComment = aComment ;
    }

    /** Комментарий */
    public String getComment() { return theComment ; }

    /** Комментарий */
    private final String theComment ;
    /** Свойство */
    public String getProperty() { return theProperty ; }

    /** Свойство */
    private final String theProperty ;
}
