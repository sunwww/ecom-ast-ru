package ru.ecom.mis.ejb.service.addresspoint;

import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressText;

/**
 *
 */
public interface IAddressPointService {

    void onUpdate(LpuAreaAddressText aText) ;
    void onRemove(LpuAreaAddressText aText) ;
    void onPersist(LpuAreaAddressText aText) ;

    void checkExists(long aLpuAreaId, Long aLpuAddressTextId, long aAddress, String aNumber, String aBuilding, String aFlat) ;

    void refresh() ;
}
