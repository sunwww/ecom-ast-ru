package ru.ecom.expomc.ejb.services.check;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 26.08.2006
 * Time: 12:58:42
 * To change this template use File | Settings | File Templates.
 */
public class CheckPropertyRow implements Serializable {

    /** Идентификатор */
    public Long getId() { return theId ; }
    public void setId(Long aId) { theId = aId ; }


    /** Проверка */
    public long getCheck() { return theCheck ; }
    public void setCheck(long aCheck) { theCheck = aCheck ; }

    /** Свойство */
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Комментарий */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Значение */
    public String getValue() { return theValue ; }
    public void setValue(String aValue) { theValue = aValue ; }

    public String getCheckAndProperty() {
        return theCheck + "," + theProperty;
    }
    /** Значение */
    private String theValue ;
    /** Комментарий */
    private String theComment ;
    /** Свойство */
    private String theProperty ;
    /** Проверка */
    private long theCheck ;
    /** Идентификатор */
    private Long theId ;

}
