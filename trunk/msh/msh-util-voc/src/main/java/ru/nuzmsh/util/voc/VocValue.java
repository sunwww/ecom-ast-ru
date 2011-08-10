package ru.nuzmsh.util.voc;

import java.io.Serializable;

/**
 * @author ESinev
 *         Date: 22.11.2005
 *         Time: 11:26:29
 */
@SuppressWarnings("serial")
public class VocValue implements Serializable, Comparable {

    public VocValue(String aId, String aName) {
        theId = aId ;
        theName = aName ;
    }

    public int compareTo(Object aObject) {
        if(aObject!=null && aObject instanceof VocValue && theName!=null) {
            VocValue v = (VocValue) aObject ;
            return theName.compareToIgnoreCase(v.theName) ;
        }
        return 0;
    }

    public String toString() {
        return theId +" : " +theName ;
    }

    /** Идентификатор */
    public String getId() { return theId ; }

    /** Название */
    public String getName() { return theName ; }

    /** Название */
    private final String theName ;
    /** Идентификатор */
    private final String theId ;
}
