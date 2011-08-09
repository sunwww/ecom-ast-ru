package ru.ecom.expomc.ejb.domain.address;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;

/**
 * Домен адреса
 * НАпример: страна, регион, город и т.д.
 */
@SuppressWarnings("serial")
@Entity
@Table(schema="SQLUser")
public class AddressDomain extends BaseEntity {

    /** Название домена */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Название домена */
    private String theName ;
}
