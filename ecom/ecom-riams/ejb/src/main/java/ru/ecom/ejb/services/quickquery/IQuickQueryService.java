package ru.ecom.ejb.services.quickquery;

import ru.ecom.ejb.services.quickquery.response.QuickQueryResponse;

import java.util.Map;

public interface IQuickQueryService {
	
	QuickQueryResponse executeQuery(String aKey) ;
	Map<String,String> listQuickQueries() ;


}
