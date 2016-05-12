package ru.ecom.mis.ejb.service.bypass;

import java.util.List;

/**
 *
 */
public class QueryResponse {

    

    /** Список свойст */
    public List<QueryResponseProperty> getProperties() { return theProperties ; }
    public void setProperties(List<QueryResponseProperty> aProperties) { theProperties = aProperties ; }

    /** Список свойст */
    private List<QueryResponseProperty> theProperties ;
}
