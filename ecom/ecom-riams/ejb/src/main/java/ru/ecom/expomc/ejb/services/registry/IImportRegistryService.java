package ru.ecom.expomc.ejb.services.registry;

import java.sql.Date;

/**
 *
 */
public interface IImportRegistryService {

    /**
     * Идентификатор LPU_FOND
     */
    public long getRegistryDocumentId() ;

    /**
     * Идентификатор LPU_FOND
     */
    public long getRegistryForeignDocumentId() ;

    
    public PreImportResult preImportRegistry(
             long aFormatId
            , String aRegistryFilename
    ) throws ImportRegistryException ;

    public void importRegistry (
            String aOmcInsuranceCompanyCode
            , String aBillNumber
            , String aReestrNumber
            , Date aBillDate
            , boolean aForeign
            , String aRegistryFilename
    ) throws ImportRegistryException ;

}
