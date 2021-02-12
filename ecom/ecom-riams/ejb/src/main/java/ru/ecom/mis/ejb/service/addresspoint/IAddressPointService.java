package ru.ecom.mis.ejb.service.addresspoint;

import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressText;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.List;

/**
 *
 */
public interface IAddressPointService {

    void onUpdate(LpuAreaAddressText aText) ;
    void onPersist(LpuAreaAddressText aText) ;

    void checkExists(long aLpuAreaId, Long aLpuAddressTextId, long aAddress, String aNumber, String aBuilding, String aFlat) ;

    void refresh() ;

    WebQueryResult exportExtDispPlanAll(String aAge, String aFilenameAddSuffix
            , String aAddSql, Long aLpu, Long aArea
            , String aDateFrom, String aDateTo, String aPeriodByReestr
            , String aNReestr, String aNPackage, Long aCompany, String aDispPlanType) throws ParserConfigurationException, TransformerException ;
    
    /*
    Выгрузка информации о прикрепленном населении
    * */
    WebQueryResult exportAll( String aFilenameAddSuffix, String aAddSql, Long aLpu, Long aArea, String aDateFrom, String aDateTo, String aPeriodByReestr, String aNReestr, String aNPackage, Long aCompnay, boolean needDivide, String xmlFormat, String fileType,  String aReturnType) throws ParserConfigurationException, TransformerException;
}
