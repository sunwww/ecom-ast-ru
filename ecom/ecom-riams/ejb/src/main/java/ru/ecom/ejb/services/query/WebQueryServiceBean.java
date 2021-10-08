package ru.ecom.ejb.services.query;


import org.apache.log4j.Logger;
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
    private static final Logger LOG = Logger.getLogger(WebQueryServiceBean.class);

    /**
     * Возвращаем json массив с результатом запроса
     * aQuery - sql запрос
     * limit - максимальное количество записей для нахождения
     * nameArray - имя объекта, куда будет помещен массив
     */
    @Override
    public String executeSqlGetJson(String aQuery, Integer limit, String nameArray) throws NamingException {

        DataSource ds = findDataSource();
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement()) {
            if (limit != null) statement.setMaxRows(limit);
            try (ResultSet resultSet = statement.executeQuery(aQuery)) {
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnCount = rsmd.getColumnCount();
                JSONArray array = new JSONArray();
                while (resultSet.next()) {
                    JSONObject temp = new JSONObject();
                    for (int j = 1; j <= columnCount; j++) {
                        temp.put(rsmd.getColumnName(j), resultSet.getObject(j));
                    }
                    array.put(temp);
                }
                return nameArray == null || nameArray.equals("") ? array.toString() : new JSONObject().put(nameArray, array).toString();
            }
        } catch (SQLException e) {
            LOG.error(e);
        }
        return null;
    }

    /**
     * Возвращаем json массив с результатом запроса
     */
    @Override
    public String executeSqlGetJson(String aQuery) throws NamingException {
        return executeSqlGetJson(aQuery, null, null);
    }

    @Override
    public String executeSqlGetJson(String aQuery, Integer limit) throws NamingException {
        return executeSqlGetJson(aQuery, limit, null);
    }

    /**
     * Возвращаем первый результат запроса в качестве json объекта
     */
    @Override
    public String executeSqlGetJsonObject(String aQuery) throws NamingException {
        JSONArray arr = new JSONArray(executeSqlGetJson(aQuery, 1, null));
        return arr.isEmpty() ? null : arr.getJSONObject(0).toString();
    }

    private DataSource findDataSource() throws NamingException {
        return ApplicationDataSourceHelper.getInstance().findDataSource();
    }

    @Override
    public int executeUpdateNativeSql(String aQuery) {
        return manager.createNativeQuery(aQuery).executeUpdate();
    }

    @Override
    public String executeNativeSqlGetJSON(String[] aFieldNames, String aQuery, Integer aMaxResult) {
        List<Object> list;
        Query query = manager.createNativeQuery(aQuery.replace("&#xA;", " ").replace("&#x9;", " "));
        if (aMaxResult != null && aMaxResult > 0) {
            list = query.setMaxResults(aMaxResult).getResultList();
        } else {
            list = query.getResultList();
        }
        try {
            JSONArray ret = new JSONArray();
            if (!list.isEmpty()) {
                boolean first = true;
                for (Object rowO : list) {
                    if (rowO instanceof Object[]) {
                        Object[] row = (Object[]) rowO;
                        if (first && row.length < aFieldNames.length) {
                            return "Количество возвращаемых столбцов меньше количества ужидаемых столбцов (возвращено " + row.length + " при ожидаемых " + aFieldNames.length + ")";
                        }
                        first = false;
                        JSONObject el = new JSONObject();
                        int fldId = 0;
                        for (Object val : row) {
                            el.put(aFieldNames.length > fldId ? aFieldNames[fldId] : "fldValue_" + fldId, val != null ? val.toString() : "");
                            fldId++;
                        }
                        ret.put(el);
                    }
                }
            }
            return ret.toString();
        } catch (JSONException e) {
            LOG.error("Ошибка executeNativeSqlGetJSON " + e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<WebQueryResult> executeNativeSql(String aQuery, Integer aMaxResult) {
        return executeNativeSql(aQuery, aMaxResult, null);
    }

    private Collection<WebQueryResult> executeNativeSql(String aQuery, Integer aMaxResult, EntityManager aManager) {
        if (aManager == null) aManager = manager;
        return executeQuery(aManager.createNativeQuery(aQuery.replace("&#xA;", " ").replace("&#x9;", " ")), aMaxResult);
    }

    @Override
    public Collection<WebQueryResult> executeNativeSql(String aQuery) {

        return executeNativeSql(aQuery, null);
    }


    @SuppressWarnings("unchecked")
    private Collection<WebQueryResult> executeQuery(Query aQuery, Integer aMaxResult) {
        List<Object> list;
        if (aMaxResult != null) {
            list = aQuery.setMaxResults(aMaxResult).getResultList();
        } else {
            list = aQuery.getResultList();
        }
        LinkedList<WebQueryResult> ret = new LinkedList<>();
        long i = 0;
        Class<WebQueryResult> clazz = WebQueryResult.class;
        Class<Object> obj_clazz = Object.class;
        for (Object rowL : list) {

            WebQueryResult result = new WebQueryResult();
            if (rowL instanceof Object[]) {

                Object[] row = (Object[]) rowL;
                for (int ii = 0; ii < row.length && ii < 27; ii++) {
                    try {
                        Method ejbSetterMethod = clazz.getMethod("set" + (ii + 1), obj_clazz);
                        ejbSetterMethod.invoke(result, row[ii]);
                    } catch (SecurityException | InvocationTargetException |
                            IllegalAccessException | IllegalArgumentException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                result.set1(rowL);
            }

            result.setSn(++i);
            ret.add(result);
        }
        return ret;
    }

    @Override
    public Collection<WebQueryResult> executeHql(String aQuery,
                                                 Integer aMaxResult) {
        return executeQuery(manager.createQuery(
                aQuery.replace("&#xA;", " ").replace("&#x9;", " ")
        ), aMaxResult);
    }

    private @PersistenceContext
    EntityManager manager;

    @Override
    public List<Object[]> executeNativeSqlGetObj(String aQuery,
                                                 Integer aMaxResult) {
        Query query = manager.createNativeQuery(aQuery.replace("&#xA;", " ").replace("&#x9;", " "));
        List<Object[]> list;
        if (aMaxResult != null) {
            list = query.setMaxResults(aMaxResult).getResultList();
        } else {
            list = query.getResultList();
        }
        return list;
    }

    @Override
    public List<Object[]> executeNativeSqlGetObj(String aQuery) {
        return executeNativeSqlGetObj(aQuery, null);
    }
}