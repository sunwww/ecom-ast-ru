package ru.ecom.mis.ejb.service.addresspoint;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressText;

/**
 *
 */
public interface IAddressPointService {
	String createAttachmentFromPatient(String needUpdateIns);
	String setInsuranceCompany(String needUpdateAll) ;
    void onUpdate(LpuAreaAddressText aText) ;
    void onRemove(LpuAreaAddressText aText) ;
    void onPersist(LpuAreaAddressText aText) ;

    void checkExists(long aLpuAreaId, Long aLpuAddressTextId, long aAddress, String aNumber, String aBuilding, String aFlat) ;

    void refresh() ;
    public WebQueryResult export(String aAge, boolean aLpuCheck, Long aLpu, Long aArea, String aDateFrom, String aDateTo,String aPeriodByReestr, String aNReestr, String aNPackage) throws ParserConfigurationException, TransformerException ;
    public WebQueryResult exportNoAddress(String aAge, boolean aLpuCheck, Long aLpu,Long aArea, String aDateFrom, String aDateTo,String aPeriodByReestr, String aNReestr, String aNPackage) throws ParserConfigurationException, TransformerException ;
    public WebQueryResult exportAll(String aAge, String aFilenameAddSuffix, String aAddSql, boolean aLpuCheck, Long aLpu, Long aArea, String aDateFrom, String aDateTo, String aPeriodByReestr, String aNReestr, String aNPackage) throws ParserConfigurationException, TransformerException;
    public WebQueryResult exportAll(String aAge, String aFilenameAddSuffix, String aAddSql, boolean aLpuCheck, Long aLpu, Long aArea, String aDateFrom, String aDateTo, String aPeriodByReestr, String aNReestr, String aNPackage, Long aCompnay, boolean needDivide) throws ParserConfigurationException, TransformerException;
    public WebQueryResult exportFondAll(String aAge, String aFilenameAddSuffix, String aAddSql, boolean aLpuCheck, Long aLpu, Long aArea, String aDateFrom, String aDateTo, String aPeriodByReestr, String aNReestr, String aNPackage, Long aCompnay, boolean needDivide) throws ParserConfigurationException, TransformerException;
}
