package ru.ecom.mis.ejb.domain.address;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;

/**
 *
 */
@Entity
@Table(schema="SQLUser")
public class VocPlaceAddressType extends BaseEntity {
 
    /** Название */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Название */
    private String theName ;
 

}
