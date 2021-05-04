package ru.ecom.miniejb.impl;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 07.02.2007
 * Time: 2:22:17
 * To change this template use File | Settings | File Templates.
 */
public class EjbHash {


    public EjbHash() {

    }

    public void put(Class aClass, String aJndi) {
        theHashMap.put(aClass.getName(), aJndi);
    }

    public String get(Class aClass) {
        return theHashMap.get(aClass.getName());
    }

    private final HashMap<String, String> theHashMap = new HashMap<>();
}
