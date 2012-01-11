package ru.ecom.ejb.services.query;

import java.util.Collection;

public interface IWebQueryService {

	Collection<WebQueryResult> executeHql(String aQuery) ;
	Collection<WebQueryResult> executeNativeSql(String aQuery) ;
	Collection<WebQueryResult> executeHql(String aQuery,Integer aMaxResult) ;
	Collection<WebQueryResult> executeNativeSql(String aQuery,Integer aMaxResult) ;

}
