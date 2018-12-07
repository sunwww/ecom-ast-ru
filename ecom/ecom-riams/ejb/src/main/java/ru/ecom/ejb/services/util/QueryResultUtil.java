package ru.ecom.ejb.services.util;

import javax.persistence.Query;
import java.util.List;

public class QueryResultUtil {

	@SuppressWarnings("unchecked")
	public static <T> T getSingleResult(Class<T> aClass, Query aQuery) {
		List<T> list = aQuery.setMaxResults(2).getResultList() ;
		return list.size()==1 ? list.iterator().next() : null ; 
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getFirst(Class<T> aClass, Query aQuery) {
		List<T> list = aQuery.setMaxResults(2).getResultList() ;
		return !list.isEmpty() ? list.iterator().next() : null ; 
	}
}
