package ru.ecom.address.ejb.domain.address;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
 * Тип адреса: улица, город, область, переулок и т.д.
 */
@Entity
@Comment("Тип адреса")
@AIndexes(@AIndex(unique = true, properties = "shortName"))
@Table(schema="SQLUser")
public class AddressType extends BaseEntity {
    /** Наименование */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Сокращенное наименование */
    public String getShortName() { return theShortName ; }
    public void setShortName(String aShortName) { theShortName = aShortName ; }

    /** Код в ОМС */
    public String getOmcCode() { return theOmcCode ; }
    public void setOmcCode(String aOmcCode) { theOmcCode = aOmcCode ; }

    /** Код в ОМС */
    private String theOmcCode ;
    /** Сокращенное наименование */
    private String theShortName ;
    /** Наименование */
    private String theName ;
}
