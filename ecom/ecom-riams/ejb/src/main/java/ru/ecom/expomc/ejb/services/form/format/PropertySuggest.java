package ru.ecom.expomc.ejb.services.form.format;

import java.io.Serializable;

/**
 * @author esinev
 * Date: 17.11.2006
 * Time: 11:18:33
 */
public class PropertySuggest implements Serializable {
    public PropertySuggest(String aProperty, String aComment) {
        property = aProperty;
        comment = aComment ;
    }

    /** Комментарий */
    public String getComment() { return comment ; }

    /** Комментарий */
    private final String comment ;
    /** Свойство */
    public String getProperty() { return property ; }

    /** Свойство */
    private final String property ;
}
