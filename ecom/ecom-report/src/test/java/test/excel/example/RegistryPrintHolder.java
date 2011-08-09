package test.excel.example;

import java.sql.Date;
import java.util.List;
import java.util.LinkedList;

/**
 * Данные для печати реестра
 */
public class RegistryPrintHolder {

    /** Номер реестра */
    public String getRegistryNumber() { return theRegistryNumber ; }
    public void setRegistryNumber(String aRegistryNumber) { theRegistryNumber = aRegistryNumber ; }

    /** Дата счета */
    public Date getBillDate() { return theBillDate ; }
    public void setBillDate(Date aBillDate) { theBillDate = aBillDate ; }

    /** Номер счета */
    public String getBillNumber() { return theBillNumber ; }
    public void setBillNumber(String aBillNumber) { theBillNumber = aBillNumber ; }

    /** Наименование лечебного учреждения */
    public String getLpuName() { return theLpuName ; }
    public void setLpuName(String aLpuName) { theLpuName = aLpuName ; }

    /** Категория ЛПУ */
    public String getLpuCategoryName() { return theLpuCategoryName ; }
    public void setLpuCategoryName(String aLpuCategoryName) { theLpuCategoryName = aLpuCategoryName ; }

    /** Работающие или нет? */
    public String getWorkingName() { return theWorkingName ; }
    public void setWorkingName(String aWorkingName) { theWorkingName = aWorkingName ; }

    /** Работающие или нет? */
    private String theWorkingName ;

    /** Период с */
    public Date getPeriodFrom() { return thePeriodFrom ; }
    public void setPeriodFrom(Date aPeriodFrom) { thePeriodFrom = aPeriodFrom ; }

    /** Период по */
    public Date getPeriodTo() { return thePeriodTo ; }
    public void setPeriodTo(Date aPeriodTo) { thePeriodTo = aPeriodTo ; }

    /** Имя файла DBF */
    public String getDbfFilename() { return theDbfFilename ; }
    public void setDbfFilename(String aDbfFilename) { theDbfFilename = aDbfFilename ; }

    /** Размер файла DBF */
    public long getDbfSizeBytes() { return theDbfSizeBytes ; }
    public void setDbfSizeBytes(long aDbfSizeBytes) { theDbfSizeBytes = aDbfSizeBytes ; }

    /** Количество записей */
    public long getCount() { return theCount ; }
    public void setCount(long aCount) { theCount = aCount ; }

    /** Услуги */
    public List<RegistryPrintRenderHolder> getRenders() { return theRenders ; }
    //public void setRenders(List<RegistryPrintRenderHolder> aRenders) { theRenders = aRenders ; }

    /** По районам */
    public List<RegistryPrintGroupHolder> getRegionGroups() { return theRegionGroups ; }
    //public void setRegionGroups(List<RegistryPrintGroupHolder> aRegionGroups) { theRegionGroups = aRegionGroups ; }

    /** По районам */
    private List<RegistryPrintGroupHolder> theRegionGroups = new LinkedList<RegistryPrintGroupHolder>();
    /** Услуги */
    private List<RegistryPrintRenderHolder> theRenders = new LinkedList<RegistryPrintRenderHolder>();
    /** Количество записей */
    private long theCount ;
    /** Размер файла DBF */
    private long theDbfSizeBytes ;
    /** Имя файла DBF */
    private String theDbfFilename ;
    /** Период по */
    private Date thePeriodTo ;
    /** Период с */
    private Date thePeriodFrom ;
    /** Работающие или нет */
    private boolean theWorking ;
    /** Категория ЛПУ */
    private String theLpuCategoryName ;
    /** Наименование лечебного учреждения */
    private String theLpuName ;
    /** Номер счета */
    private String theBillNumber ;
    /** Дата счета */
    private Date theBillDate ;
    /** Номер реестра */
    private String theRegistryNumber ;
}
