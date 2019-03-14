package ru.ecom.address.ejb.service;

/**
 */
public interface IAddressService {
    String getAddressString(long aAddressPk, String aHouse, String aCorpus, String aFlat, String aZipCode) ;
    Long getIdForLevel(long aDomain, Long aAddressId) ;
    String getAddressNonresidentString(Long aTerritory, String aRegion
            , Long aTypeSettlement
            , String aSettlement
            , Long aTypeStreet
            , String aStreet
            , String aHouseNumber
            , String aHouseBuilding, String aFlatNumber, String aZipCode);
    String getZipcode(Long aAddress5, Long aAddress6) ;
    String getRayon(Long aAddressId, String aHouse);

}
