package ru.ecom.ejb.services.query;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Remote(IWebQueryService.class)
public class WebQueryServiceBean implements IWebQueryService {

	public Collection<WebQueryResult> executeNativeSql(String aQuery) { 
		return executeQuery(theManager.createNativeQuery(aQuery.replace("&#xA;", " ").replace("&#x9;", " "))) ;
	}
	
	
	@SuppressWarnings("unchecked")
	public Collection<WebQueryResult> executeQuery(Query aQuery) {
		List<Object[]> list = aQuery.getResultList() ;
	LinkedList<WebQueryResult> ret = new LinkedList<WebQueryResult>() ;
	long i = 0 ;
	for (Object[] row : list) {
		WebQueryResult result = new WebQueryResult() ;
		// fixme улучшить
		if(row.length>0) result.set1(row[0]) ;
		if(row.length>1) result.set2(row[1]) ;
		if(row.length>2) result.set3(row[2]) ;
		if(row.length>3) result.set4(row[3]) ;
		if(row.length>4) result.set5(row[4]) ;
		if(row.length>5) result.set6(row[5]) ;
		if(row.length>6) result.set7(row[6]) ;
		if(row.length>7) result.set8(row[7]) ;
		if(row.length>8) result.set9(row[8]) ;
		if(row.length>9) result.set10(row[9]) ;
		if(row.length>10) result.set11(row[10]) ;
		if(row.length>11) result.set12(row[11]) ;
		if(row.length>12) result.set13(row[12]) ;
		if(row.length>13) result.set14(row[13]) ;
		if(row.length>14) result.set15(row[14]) ;
		
		result.setSn(++i) ;
		ret.add(result) ;
	}
	return ret ;
	}
	/**
	 * Выполняет запрос на HQL
	 * @param aQuery
	 * @return
	 */
	public Collection<WebQueryResult> executeHql(String aQuery) {
		return executeQuery(theManager.createQuery(
				aQuery.replace("&#xA;", " ").replace("&#x9;", " ")
				)) ;
	}
	
	
    private @PersistenceContext EntityManager theManager;
	
}
