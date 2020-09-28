package ru.ecom.ejb.services.query;

import org.json.JSONException;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface IWebQueryService {

	String executeSqlGetJson(String aQuery,Integer limit,String nameArray) throws NamingException, SQLException;
	String executeSqlGetJson(String aQuery,Integer limit) throws NamingException, SQLException;
	String executeSqlGetJson(String aQuery) throws NamingException;
	String executeSqlGetJsonObject(String aQuery) throws NamingException, SQLException, JSONException;
	String executeNativeSqlGetJSON(String[] aFieldNames, String aQuery, Integer aMaxResult);
	Collection<WebQueryResult> executeHql(String aQuery) ;
	Collection<WebQueryResult> executeNativeSql(String aQuery) ;
	Collection<WebQueryResult> executeHql(String aQuery,Integer aMaxResult) ;
	Collection<WebQueryResult> executeNativeSql(String aQuery,Integer aMaxResult) ;
	
	int executeUpdateNativeSql(String aQuery) ;
	
	List<Object[]> executeNativeSqlGetObj(String aQuery,Integer aMaxResult) ;
	List<Object[]> executeNativeSqlGetObj(String aQuery) ;

}
