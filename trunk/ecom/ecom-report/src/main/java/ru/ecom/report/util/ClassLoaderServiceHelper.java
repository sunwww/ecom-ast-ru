package ru.ecom.report.util;

/**
 * @author esinev
 * Date: 10.10.2006
 * Time: 16:32:37
 */
public class ClassLoaderServiceHelper {


     public Object load(String aClassName) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
         return Class.forName(aClassName).newInstance() ;
     }
}
