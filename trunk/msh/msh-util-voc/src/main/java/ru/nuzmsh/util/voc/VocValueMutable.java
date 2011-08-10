package ru.nuzmsh.util.voc;

import java.io.Serializable;

/**
 * Значение справочника
 */
@SuppressWarnings("serial")
public class VocValueMutable implements Serializable, Comparable {

    public VocValueMutable(String aId, String aValue) {
        theId = aId ;
        theName = aValue ;
    }

    /** Идентификатор */
    public String getId() { return theId ; }

    /** Значение */
    public String getName() { return theName ; }

    public String toString() {
        return theName +" " +theId ;
    }

    public void setName(String aName) {
        theName = aName ;
    }

    public int compareTo(Object aObject) {
        if(aObject!=null && aObject instanceof VocValueMutable && theName!=null) {
            VocValueMutable v = (VocValueMutable) aObject ;
            return theName.compareToIgnoreCase(v.theName) ;
        }
        return 0;
    }

    /** Значение */
    private String theName ;
    /** Идентификатор */
    private final String theId ;
}
