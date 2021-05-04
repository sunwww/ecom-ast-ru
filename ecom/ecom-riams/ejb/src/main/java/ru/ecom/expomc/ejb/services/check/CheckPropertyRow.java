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
    public Long getId() { return id ; }
    public void setId(Long aId) { id = aId ; }


    /** Проверка */
    public long getCheck() { return check ; }
    public void setCheck(long aCheck) { check = aCheck ; }

    /** Свойство */
    public String getProperty() { return property ; }
    public void setProperty(String aProperty) { property = aProperty ; }

    /** Комментарий */
    public String getComment() { return comment ; }
    public void setComment(String aComment) { comment = aComment ; }

    /** Значение */
    public String getValue() { return value ; }
    public void setValue(String aValue) { value = aValue ; }

    public String getCheckAndProperty() {
        return check + "," + property;
    }
    /** Значение */
    private String value ;
    /** Комментарий */
    private String comment ;
    /** Свойство */
    private String property ;
    /** Проверка */
    private long check ;
    /** Идентификатор */
    private Long id ;

}
