package ru.ecom.ejb.services.util;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 26.08.2006
 * Time: 12:06:42
 * To change this template use File | Settings | File Templates.
 */
public class ClassLoaderHelper {

    private ClassLoaderHelper() {

    }

    public static ClassLoaderHelper getInstance() {
        return INSTANCE;
    }

    public Class loadClass(String aFullClassName) throws ClassNotFoundException {
        return Thread.currentThread().getContextClassLoader().loadClass(aFullClassName) ;
    }
    
    private static final ClassLoaderHelper INSTANCE = new ClassLoaderHelper();
}
