package ru.ecom.expomc.ejb.services.form.check;

import java.io.Serializable;

/**
 */
public class ChecksTableFormRowOn implements Serializable {

    /** Ид. check */
    public long getCheckId() { return theCheckId ; }
    public void setCheckId(long aCheckId) { theCheckId = aCheckId ; }

    /** Ид. check */
    private long theCheckId ;
    /** название */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** название */
    private String theName ;
    /** Ключ  */
    public String getKey() { return theKey ; }
    public void setKey(String aKey) { theKey = aKey ; }

    /** Значение */
    public boolean getValue() { return theValue ; }
    public void setValue(boolean aValue) { theValue = aValue ; }

    /** Значение */
    private boolean theValue ;
    /** Ключ  */
    private String theKey ;
}
