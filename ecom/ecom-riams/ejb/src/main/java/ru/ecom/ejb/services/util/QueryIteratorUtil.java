package ru.ecom.ejb.services.util;

import javax.persistence.Query;
import java.util.Iterator;

/**
 *
 */
public class QueryIteratorUtil {

	@SuppressWarnings("unchecked")
	public static <T> Iterator<T> iterate(Class<T> aClass, Query aQuery) {
		return (Iterator<T>)iterate(aQuery) ;
	}
	
    public static Iterator iterate(Query aQuery) {
        org.hibernate.ejb.QueryImpl query = (org.hibernate.ejb.QueryImpl)
                aQuery ;
        return query.getHibernateQuery().iterate();
    }
}
