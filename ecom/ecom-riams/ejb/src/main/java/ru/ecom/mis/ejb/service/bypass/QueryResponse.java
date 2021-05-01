package ru.ecom.mis.ejb.service.bypass;

import java.util.List;

/**
 *
 */
public class QueryResponse {

    

    /** Список свойст */
    public List<QueryResponseProperty> getProperties() { return properties ; }
    public void setProperties(List<QueryResponseProperty> aProperties) { properties = aProperties ; }

    /** Список свойст */
    private List<QueryResponseProperty> properties ;
}
