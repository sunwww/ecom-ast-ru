package ru.ecom.address.ejb.service;

/**
 */
public interface IAddressService {
    public String getAddressString(long aAddressPk, String aHouse, String aCorpus, String aFlat,String aZipCode) ;
    public Long getIdForLevel(long aDomain, Long aAddressId) ;
    public String getAddressNonresidentString(Long aTerritory, String aRegion
        	, Long aTypeSettlement
        	, String aSettlement
        	, Long aTypeStreet
        	, String aStreet
        	, String aHouseNumber
        	, String aHouseBuilding, String aFlatNumber, String aZipCode);
    public String getZipcode(Long aAddress5, Long aAddress6) ;

}
