package ru.ecom.expomc.ejb.services.exportformat.driver;

import org.apache.log4j.Logger;
import ru.ecom.expomc.ejb.services.exportformat.AbstractExportFormatDriver;
import ru.ecom.expomc.ejb.services.exportformat.SaveXmlException;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author ikouzmin 13.03.2007 16:52:47
 */

public class DefaultExportDriver extends AbstractExportFormatDriver {
    private static final Logger LOG = Logger.getLogger(DefaultExportDriver.class) ;

    protected boolean theNative;
    protected Query theQuery;
    private List resultList;
    private long rows;
    private Class clazz;
    private List<Method> methods;
    private int cols;

    HashMap<String,String> theDriverConfig;

    DefaultExportDriver(EntityManager theManager, boolean aNative, String sql,String driverConfig) {
        theNative = aNative;
        if (theNative) {
            theQuery = theManager.createNativeQuery(sql);
        } else {
            String qry = sql.toLowerCase();
            if (!qry.startsWith("select") && !qry.startsWith("from")) {
                sql = "from "+sql;
            }
            theQuery = theManager.createQuery(sql);
        }

        theDriverConfig = new HashMap<>();
        for (String s : driverConfig.split(";")) {
            String[] keys = (s+"==~").split("=");
            String keyName = keys[0];
            String keyValue = keys.length>0 ? keys[1] : "";
            theDriverConfig.put(keyName,keyValue);
        }
    }

    public void execute(int maxRecords) {
        if (maxRecords > 0) {
            resultList = theQuery.setMaxResults(maxRecords).getResultList();
        } else {
            resultList = theQuery.getResultList();
        }
        rows = resultList.size();
    }

    public void saveXml(StringBuilder s) throws SaveXmlException {
        long i = 0;
        try {
            if (rows == 0) {
                saveEmpty(s);
            } else {
                saveProlog(s);
                for (Object o : resultList) {
                    saveRow(o, s);
                    i++;
                    if (i % 10000 == 0) LOG.info("row:"+i);
                }
                saveEpilog(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SaveXmlException(e.getMessage(), e);
        }
    }

    public void saveXml(Writer writer) throws SaveXmlException {
        long i = 0;
        StringBuilder s = new StringBuilder();
        try {
            if (rows == 0) {
                saveEmpty(s);
                writer.write(s.toString());
            } else {
                saveProlog(s);
                writer.write(s.toString());
                for (Object o : resultList) {
                    s.setLength(0);
                    saveRow(o, s);
                    writer.write(s.toString());
                    i++;
                    if (i % 10000 == 0) LOG.info("row:"+i);
                }
                s.setLength(0);
                saveEpilog(s);
                writer.write(s.toString());
            }
        } catch (Exception e) {
            throw new SaveXmlException(e.getMessage(), e);
        }
    }

    private void saveProlog(StringBuilder s) {
        clazz = null;
        methods = null;
        cols = 0;
        if (rows > 0) clazz = resultList.get(0).getClass();
        if (clazz!=null) {
            LOG.info("ROW CLASS:" + clazz.getCanonicalName());
        }
        if (clazz.equals(Object[].class)) {
            cols = ((Object []) resultList.get(0)).length;
            s.append("<result type='sql' entityClass='" + clazz.getCanonicalName()+
                    "' rows='" + rows + "' cols='" + cols + "' >\n");
        } else {
            methods = BeanPropertyUtil.getBeanPropertyGetMethods(clazz);
            cols = methods.size();
            String className = clazz.getCanonicalName();
            String classTag = clazz.getSimpleName().toLowerCase();

            s.append("<result type='entity' entityClass='" + className +
                    "' rows='" + rows + "' fields='" + cols + "' entityClassTag='" + classTag +
                    "' tag='" + classTag + "'>\n");
        }
    }

    private void saveRow(Object o, StringBuilder s)  {
     //   Object[] nullArgs = {};
        s.append("\t<row>\n");
        saveObject(o,s,0,true);
        s.append("\t</row>\n");

//        if (methods != null) {
//            for (Method method : methods) {
//                if(method.isAnnotationPresent(Transient.class)) continue;
//                Object value = method.invoke(o, nullArgs);
//                if (value instanceof Collection) continue;
//                String name = BeanPropertyUtil.getBeanPropertyByGetMethod(method.getName());
//                s.append("\t\t<" + name + ">" + exportEntity(value) + "</" + name + ">\n");
//            }
//        } else {
//            Object [] row = (Object[]) o;
//            for (int i = 0; i < row.length; i++) {
//                s.append("\t\t<col c='" + (i+1) + "'>" + exportEntity(row[i]) + "</col>\n");
//            }
//        }
//        LOG.info("row:'" + s.toString()+"'");
    }

    private boolean checkExpand(int level,String name) {
        if (level==0) return true;
        String expand = ","+theDriverConfig.get("expand")+",";
        String levelString = ""+level;
//        LOG.info("CheckExpand:"+levelString+"/"+name+" From:"+expand);
        return expand.indexOf(","+levelString+",")>=0 || expand.indexOf(","+name+",")>=0 ||
                expand.indexOf(","+levelString+"/"+name+",")>=0;
    }

    private void saveObject(Object o,StringBuilder s,int level,boolean isExpanded)  {
        if (o == null) return;
        Class clazz = o.getClass();
        List<Method> methods ;
//        boolean isExpanded = false;
        boolean isEntity = clazz.isAnnotationPresent(Entity.class);

        if (isEntity) {
            if (isExpanded) {
                if (this.methods != null && o.getClass().equals(this.clazz)) {
                    methods = this.methods;
                } else {
                    methods = BeanPropertyUtil.getBeanPropertyGetMethods(clazz);
                }
                Object[] noargs = {};
                for (Method method : methods) {
                    String name = BeanPropertyUtil.getBeanPropertyByGetMethod(method.getName());
                    try {
                        Object value = method.invoke(o, noargs);
                        if(method.isAnnotationPresent(Transient.class)) continue;
                        //if (value instanceof Collection) continue;
                        boolean isExpandChild =  checkExpand(level+1,name);
                        if (value instanceof Collection && !isExpandChild) continue;

                        s.append("\t\t<" + name + ">");
                        saveObject(value,s,level+1,isExpandChild);
                        s.append("</" + name + ">\n");
                    } catch (IllegalAccessException | InvocationTargetException e) {
                    }
                }
            } else {
                Class[] noclass = {};
                Object[] noargs = {};
                try {
                    Method meth = clazz.getMethod("getId", noclass);
                    Object val = meth.invoke(o,noargs);
                    s.append("<id class='" + clazz.getCanonicalName()+"'>"+val.toString()+"</id>");
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                }
            }
        } else if (o instanceof Collection) {
            s.append("<list>");
            Collection collection = (Collection) o;
            for (Object value : collection) {
                s.append("<item>");
                saveObject(value,s,level+1,checkExpand(level+1,"~"));
                s.append("</item>");
            }
            s.append("</list>");
        } else if (clazz.equals(Object[].class)) {
            Object [] row = (Object[]) o;
            for (int i = 0; i < row.length; i++) {
                s.append("\t\t<col c='" + (i+1) + "'>");
                saveObject(row[i],s,level+1,true);
                s.append("</col>\n");
            }
        } else {
            s.append(convertToXmlString(o));
        }
    }

    private static String convertToXmlString(Object o) {
        String ret;
        if (o == null) ret = "";
        else {
            ret = o.toString();
            ret = ret.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
        }
        return ret;
    }

    private static String exportEntity(Object o) {
        String ret;
        if (o == null) ret = "";
        else {
            Class clazz = o.getClass();
            if (clazz.isAnnotationPresent(Entity.class)) {
                Class[] noclass = {};
                Object[] noargs = {};
                try {
                    Method meth = clazz.getMethod("getId",noclass);
                    Object val = meth.invoke(o,noargs);
                    ret = "<id class='" + clazz.getCanonicalName()+"'>"+val.toString()+"</id>";
//                    ret = clazz.getCanonicalName()+":"+val.toString();
//                    ret = ret.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
                    ret = "***";
                }
            } else {
                ret = o.toString();
                ret = ret.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
            }

        }
//        LOG.info("export-entity:'" + ret + "'");
        return ret;
    }

    private void saveEpilog(StringBuilder s) {
        s.append("</result>\n");
    }

    private void saveEmpty(StringBuilder s) {
        s.append("<result rows='0'><nodata/></result>");
    }

    private void saveError(StringBuilder s) {

    }


}


class BeanPropertyUtil {
    static List<Method> getBeanPropertyGetMethods(Class aClass) {
        List<Method> methodList = new ArrayList<>();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();

            if (method.getParameterTypes().length > 0) continue;
            if (methodName.equals("getClass")) continue;

            if (methodName.startsWith("get") ||
                    methodName.startsWith("is")) {

                methodList.add(method);
            }
        }
        return methodList;
    }

    static String getBeanPropertyByGetMethod(String aMethodName) {
        String fieldName = aMethodName.replaceFirst("^get", "").replaceFirst("^is", "");
        fieldName = fieldName.substring(0, 1).toLowerCase() +
                fieldName.substring(1);
        return fieldName;

    }
}