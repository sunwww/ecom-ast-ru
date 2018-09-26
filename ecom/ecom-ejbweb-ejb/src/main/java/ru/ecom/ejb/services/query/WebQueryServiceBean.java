package ru.ecom.ejb.services.query;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.services.util.ApplicationDataSourceHelper;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Stateless
@Remote(IWebQueryService.class)
public class WebQueryServiceBean implements IWebQueryService {

	public String executeSqlGetJson(String aQuery,Integer limit,String nameArray) throws NamingException, SQLException {

		if(limit==null || limit==0 || limit>100 ) limit=100;

		DataSource ds =findDataSource();
		Connection connection = null;
		Statement statement = null;
		try {
			connection = ds.getConnection();
			statement = connection.createStatement();
			statement.setMaxRows(limit);
			ResultSet resultSet = statement.executeQuery(aQuery);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			JSONArray array = new JSONArray();
			while (resultSet.next()) {
				JSONObject temp = new JSONObject();
				for (int j = 1; j <= resultSet.getMetaData().getColumnCount(); j++) {
					temp.put(rsmd.getColumnName(j),resultSet.getString(j));
				}
				array.put(temp);
			}

			if(nameArray.equals("")){
				return array.toString();
			}else {
				return new JSONObject().put(nameArray, array).toString();
			}

		}catch (JSONException | SQLException e){
			e.printStackTrace();
		} finally {
			if(connection!=null) connection.close();
			if(statement!=null) statement.close();
		}
		return null;
	}
	public String executeSqlGetJson(String aQuery,Integer limit) throws NamingException, SQLException {
		return executeSqlGetJson(aQuery,limit,"");
	}

	public String executeSqlGetJsonObject(String aQuery) throws NamingException, SQLException {

		DataSource ds =findDataSource();
		Connection connection = ds.getConnection();
		Statement statement = connection.createStatement();
		try {
			ResultSet resultSet = statement.executeQuery(aQuery);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			JSONObject jsonObject = new JSONObject();
			while (resultSet.next()) {
				for (int j = 1; j <= resultSet.getMetaData().getColumnCount(); j++) {
					jsonObject.put(rsmd.getColumnName(j),resultSet.getString(j));
				}
			}

			statement.close();
			connection.close();
			return jsonObject.toString();
		}catch (Exception e){e.printStackTrace();}
		return null;
	}

	private DataSource findDataSource() throws NamingException {
		return ApplicationDataSourceHelper.getInstance().findDataSource();
	}

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
	public String executeNativeSqlGetJSON(String[] aFieldNames, String aQuery, Integer aMaxResult) {
		List<Object> list ;
		Query query = theManager.createNativeQuery(aQuery.replace("&#xA;", " ").replace("&#x9;", " "));
		if (aMaxResult!=null&&aMaxResult>0) {
			list=query.setMaxResults(aMaxResult).getResultList();
		} else {
			list=query.getResultList();
		}
		try {
			JSONArray ret = new JSONArray();
			if (list.size()>0) {
				boolean first = true;
				for (Object rowO: list) {
					if (rowO instanceof Object[]){
						Object[] row = (Object[]) rowO;
						if (first && row.length<aFieldNames.length) {
							return "Количество возвращаемых столбцов меньше количества ужидаемых столбцов (возвращено "+row.length+" при ожидаемых "+aFieldNames.length+")";
						}
						first=false;
						JSONObject el = new JSONObject();
						int fldId=0;
						for (Object val:row){
							el.put(aFieldNames.length>fldId?aFieldNames[fldId]:"fldValue_"+fldId,val!=null?val.toString():null);
							fldId++;
						}
						ret.put(el);
					}
				}
			}
			return ret.toString();
		} catch (JSONException e) {
			System.out.println("Ошибка executeNativeSqlGetJSON "+e);
			e.printStackTrace();
		}
		return null;
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
					} catch (SecurityException | InvocationTargetException |
							IllegalAccessException | IllegalArgumentException | NoSuchMethodException e) {
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
		Query query = theManager.createNativeQuery(aQuery.replace("&#xA;", " ").replace("&#x9;", " "));
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
