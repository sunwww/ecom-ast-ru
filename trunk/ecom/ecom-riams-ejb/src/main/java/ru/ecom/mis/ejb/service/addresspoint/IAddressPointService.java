package ru.ecom.mis.ejb.service.addresspoint;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

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
    public String export(boolean aLpuCheck, Long aLpu, String aPeriod, String aNReestr, String aNPackage) throws ParserConfigurationException, TransformerException ;
}
