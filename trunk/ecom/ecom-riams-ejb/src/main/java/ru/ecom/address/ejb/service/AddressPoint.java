package ru.ecom.address.ejb.service;

import java.io.Serializable;

/**
 * Кажется не используется
 */
public class AddressPoint implements Serializable {

    /** Номер доме */
    public String getHouseNumber() { return theHouseNumber ; }
    public void setHouseNumber(String aHouseNumber) { theHouseNumber = aHouseNumber ; }

    /** Номер корпуса */
    public String getHouseBuilding() { return theHouseBuilding ; }
    public void setHouseBuilding(String aHouseBuilding) { theHouseBuilding = aHouseBuilding ; }

    
    /** Номер корпуса */
    private String theHouseBuilding ;
    /** Номер доме */
    private String theHouseNumber ;
}
