package ru.ecom.expomc.ejb.services.registry;

import java.io.Serializable;

/**
 * Результат обработки файла для определения параметров импорта
 */
public class PreImportResult implements Serializable {
    /** Количество записей */
    public long getCount() { return theCount ; }
    public void setCount(long aCount) { theCount = aCount ; }

    /** Все в одном щете */
    public boolean getAllInOneBill() { return theAllInOneBill ; }
    public void setAllInOneBill(boolean aAllInOneBill) { theAllInOneBill = aAllInOneBill ; }

    /** Все записи в одном реестре */
    public boolean getAllInOneRegistry() { return theAllInOneRegistry ; }
    public void setAllInOneRegistry(boolean aAllInOneRegistry) { theAllInOneRegistry = aAllInOneRegistry ; }

    /** Номер счета */
    public String getBillNumber() { return theBillNumber ; }
    public void setBillNumber(String aBillNumber) { theBillNumber = aBillNumber ; }

    /** Номер реестра */
    public String getRegistryNumber() { return theRegistryNumber ; }
    public void setRegistryNumber(String aRegistryNumber) { theRegistryNumber = aRegistryNumber ; }

    /** Все в одной страховой компании */
    public boolean getAllInOneCompany() { return theAllInOneCompany ; }
    public void setAllInOneCompany(boolean aAllInOneCompany) { theAllInOneCompany = aAllInOneCompany ; }

    /** Код страховой компании */
    public String getCompanyOmcCode() { return theCompanyOmcCode ; }
    public void setCompanyOmcCode(String aCompanyOmcCode) { theCompanyOmcCode = aCompanyOmcCode ; }

    /** Название страховой компании */
    public String getCompanyName() { return theCompanyName ; }
    public void setCompanyName(String aCompanyName) { theCompanyName = aCompanyName ; }

    
    /** Название страховой компании */
    private String theCompanyName ;
    /** Код страховой компании */
    private String theCompanyOmcCode ;
    /** Все в одной страховой компании */
    private boolean theAllInOneCompany ;
    /** Номер реестра */
    private String theRegistryNumber ;
    /** Номер счета */
    private String theBillNumber ;
    /** Все записи в одном реестре */
    private boolean theAllInOneRegistry ;
    /** Все в одном щете */
    private boolean theAllInOneBill ;
    /** Количество записей */
    private long theCount ;
}
