package ru.ecom.diary.ejb.domain.protocol.template;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 15.12.2006
 * Time: 14:46:07
 * To change this template use File | Settings | File Templates.
 */
public class PropertyProt implements Serializable {
    /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Наименование свойства */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Место расположения свойства */
    public String getPiece() { return thePiece ; }
    public void setPiece(String aPiece) { thePiece = aPiece ; }

    /** Место расположения свойства */
    private String thePiece ;

    /** Наименование свойства */
    private String theName ;

    /** Идентификатор */
    private long theId ;

}
