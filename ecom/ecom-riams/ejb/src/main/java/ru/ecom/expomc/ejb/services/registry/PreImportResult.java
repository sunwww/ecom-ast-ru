package ru.ecom.expomc.ejb.services.registry;

import java.io.Serializable;

/**
 * Результат обработки файла для определения параметров импорта
 */
public class PreImportResult implements Serializable {
    /** Количество записей */
    public long getCount() { return count ; }
    public void setCount(long aCount) { count = aCount ; }

    /** Все в одном щете */
    public boolean getAllInOneBill() { return allInOneBill ; }
    public void setAllInOneBill(boolean aAllInOneBill) { allInOneBill = aAllInOneBill ; }

    /** Все записи в одном реестре */
    public boolean getAllInOneRegistry() { return allInOneRegistry ; }
    public void setAllInOneRegistry(boolean aAllInOneRegistry) { allInOneRegistry = aAllInOneRegistry ; }

    /** Номер счета */
    public String getBillNumber() { return billNumber ; }
    public void setBillNumber(String aBillNumber) { billNumber = aBillNumber ; }

    /** Номер реестра */
    public String getRegistryNumber() { return registryNumber ; }
    public void setRegistryNumber(String aRegistryNumber) { registryNumber = aRegistryNumber ; }

    /** Все в одной страховой компании */
    public boolean getAllInOneCompany() { return allInOneCompany ; }
    public void setAllInOneCompany(boolean aAllInOneCompany) { allInOneCompany = aAllInOneCompany ; }

    /** Код страховой компании */
    public String getCompanyOmcCode() { return companyOmcCode ; }
    public void setCompanyOmcCode(String aCompanyOmcCode) { companyOmcCode = aCompanyOmcCode ; }

    /** Название страховой компании */
    public String getCompanyName() { return companyName ; }
    public void setCompanyName(String aCompanyName) { companyName = aCompanyName ; }

    
    /** Название страховой компании */
    private String companyName ;
    /** Код страховой компании */
    private String companyOmcCode ;
    /** Все в одной страховой компании */
    private boolean allInOneCompany ;
    /** Номер реестра */
    private String registryNumber ;
    /** Номер счета */
    private String billNumber ;
    /** Все записи в одном реестре */
    private boolean allInOneRegistry ;
    /** Все в одном щете */
    private boolean allInOneBill ;
    /** Количество записей */
    private long count ;
}
