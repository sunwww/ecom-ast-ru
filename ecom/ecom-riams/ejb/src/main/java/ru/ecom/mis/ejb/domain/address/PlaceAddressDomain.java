package ru.ecom.mis.ejb.domain.address;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;

/**
 * Домен адреса
 * НАпример: страна, регион, город и т.д. 
 */
@Entity
@Table(schema="SQLUser")
public class PlaceAddressDomain extends BaseEntity {
 
    /** Название домена */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Название домена */
    private String theName ;
 
}
