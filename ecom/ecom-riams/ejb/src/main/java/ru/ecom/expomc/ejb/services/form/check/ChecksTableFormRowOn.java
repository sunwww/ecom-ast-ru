package ru.ecom.expomc.ejb.services.form.check;

import java.io.Serializable;

/**
 */
public class ChecksTableFormRowOn implements Serializable {

    /** Ид. check */
    public long getCheckId() { return checkId ; }
    public void setCheckId(long aCheckId) { checkId = aCheckId ; }

    /** Ид. check */
    private long checkId ;
    /** название */
    public String getName() { return name ; }
    public void setName(String aName) { name = aName ; }

    /** название */
    private String name ;
    /** Ключ  */
    public String getKey() { return key ; }
    public void setKey(String aKey) { key = aKey ; }

    /** Значение */
    public boolean getValue() { return value ; }
    public void setValue(boolean aValue) { value = aValue ; }

    /** Значение */
    private boolean value ;
    /** Ключ  */
    private String key ;
}
