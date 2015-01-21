package ru.ecom.ejb.services.query;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ru.nuzmsh.util.PropertyUtil;

@Stateless
@Remote(IWebQueryService.class)
public class WebQueryServiceBean implements IWebQueryService {
	
	public int executeUpdateNativeSql(String aQuery) {
		return theManager.createNativeQuery(aQuery).executeUpdate() ;
	}
	public Collection<WebQueryResult> executeNativeSql(String aQuery,Integer aMaxResult) {
		return executeQuery(theManager.createNativeQuery(aQuery.replace("&#xA;", " ").replace("&#x9;", " ")),aMaxResult) ;
	}
	public Collection<WebQueryResult> executeNativeSql(String aQuery) {
		return executeNativeSql(aQuery,null) ;
	}
	
	public Collection<WebQueryResult> executeQuery(Query aQuery) {
		return executeQuery(aQuery,null) ;
	}
	@SuppressWarnings("unchecked")
	public Collection<WebQueryResult> executeQuery(Query aQuery,Integer aMaxResult) {
	List<Object> list ;
	if (aMaxResult!=null) {
		list= aQuery.setMaxResults(aMaxResult).getResultList() ;
	} else {
		list= aQuery.getResultList() ;
	}
	LinkedList<WebQueryResult> ret = new LinkedList<WebQueryResult>() ;
	long i = 0 ;
	Class<WebQueryResult> clazz = WebQueryResult.class ;
	Class<Object> obj_clazz =Object.class ;
	for (Object rowL : list) {
		
		WebQueryResult result = new WebQueryResult() ;
		if (rowL instanceof Object[]) {
			
			Object[] row = (Object[])rowL ;
			for (int ii =0 ;ii<row.length&&ii<27;ii++) {
				try {
					Method ejbSetterMethod = clazz.getMethod("set"+(ii+1), obj_clazz);
					ejbSetterMethod.invoke(result, row[ii]) ;
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}/*
			if(row.length>0) result.set1(row[0]) ;
			if(row.length>1) result.set2(row[1]) ;
			if(row.length>2) {result.set3(row[2]) ;
			if(row.length>3) {result.set4(row[3]) ;
			if(row.length>4) {result.set5(row[4]) ;
			if(row.length>5) {result.set6(row[5]) ;
			if(row.length>6) {result.set7(row[6]) ;
			if(row.length>7) {result.set8(row[7]) ;
			if(row.length>8) {result.set9(row[8]) ;
			if(row.length>9) {result.set10(row[9]) ;
			if(row.length>10) {result.set11(row[10]) ;
			if(row.length>11) {result.set12(row[11]) ;
			if(row.length>12) {result.set13(row[12]) ;
			if(row.length>13) {result.set14(row[13]) ;
			if(row.length>14) {result.set15(row[14]) ;
			if(row.length>15) {result.set16(row[15]) ;
			if(row.length>16) {result.set17(row[16]) ;
			if(row.length>17) {result.set18(row[17]) ;
			if(row.length>18) {result.set19(row[18]) ;
			if(row.length>19) {result.set20(row[19]) ;
			if(row.length>20) {result.set21(row[20]) ;
			if(row.length>21) {result.set22(row[21]) ;
			if(row.length>22) {result.set23(row[22]) ;
			if(row.length>23) {result.set24(row[23]) ;
			if(row.length>24) {result.set25(row[24]) ;
			if(row.length>25) {result.set26(row[25]) ;
			if(row.length>26) {result.set27(row[26]) ;
			if(row.length>27) {result.set28(row[27]) ;
			}}}}}}}}}}}}}}}}}}}}}}}}}}*/
		} else {
			result.set1(rowL) ;
		}
		
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
		return executeHql(aQuery,
				null);
	}
	
	public Collection<WebQueryResult> executeHql(String aQuery,
			Integer aMaxResult) {
		return executeQuery(theManager.createQuery(
				aQuery.replace("&#xA;", " ").replace("&#x9;", " ")
				),aMaxResult) ;
	}
	
    private @PersistenceContext EntityManager theManager;

	public List<Object[]> executeNativeSqlGetObj(String aQuery,
			Integer aMaxResult) {
		Query query = theManager.createQuery(aQuery.replace("&#xA;", " ").replace("&#x9;", " "));
		List<Object[]> list ;
		if (aMaxResult!=null) {
			list= query.setMaxResults(aMaxResult).getResultList() ;
		} else {
			list= query.getResultList() ;
		}
		return list ;
	}
	public List<Object[]> executeNativeSqlGetObj(String aQuery) {
		return executeNativeSqlGetObj(aQuery,null) ;
	}

	
}
