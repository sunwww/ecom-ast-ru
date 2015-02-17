package ru.ecom.ejb.services.query;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import ru.ecom.ejb.services.util.ApplicationDataSourceHelper;
import ru.nuzmsh.util.PropertyUtil;

@Stateless
@Remote(IWebQueryService.class)
public class WebQueryServiceBean implements IWebQueryService {
	
	public int executeUpdateNativeSql(String aQuery) {
		return theManager.createNativeQuery(aQuery).executeUpdate() ;
	}
	/*private String getDataSourceFromPersistenceXml() throws IOException  {
        String aResource = "/META-INF/persistence.xml";
        System.out.println("--------------get jndi from "+aResource) ;
        InputStream in = getClass().getResourceAsStream(aResource);
        try {
            Document doc = new SAXBuilder().build(in);
            Element rootElement = doc.getRootElement();
            Element unit = rootElement.getChild("persistence-unit");
            Element jtaDataSource = unit.getChild("jta-data-source");
            return jtaDataSource.getTextTrim();
        } catch(Exception e){
        	e.printStackTrace() ;
        } finally {
            in.close();
        }
        return "---------------no jndi" ;
    }*/
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
		/*try {
			System.out.println("jndi---="+getDataSourceFromPersistenceXml()) ;
		}catch(Exception e) {
			e.printStackTrace() ;
		}*/
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
			}
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
