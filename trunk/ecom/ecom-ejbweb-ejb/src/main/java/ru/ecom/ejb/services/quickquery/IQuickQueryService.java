package ru.ecom.ejb.services.quickquery;

import java.util.Map;

import ru.ecom.ejb.services.quickquery.response.QuickQueryResponse;

public interface IQuickQueryService {
	
	QuickQueryResponse executeQuery(String aKey) ;
	Map<String,String> listQuickQueries() ;


}
