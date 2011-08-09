package ru.ecom.ejb.services.query;

import java.util.Collection;

public interface IWebQueryService {

	Collection<WebQueryResult> executeHql(String aQuery) ;
	Collection<WebQueryResult> executeNativeSql(String aQuery) ;

}
