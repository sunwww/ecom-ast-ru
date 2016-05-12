package ru.ecom.mis.ejb.domain.external;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.LogicTokenizer;
import ru.nuzmsh.util.StringUtil;

/**
 * Информация о пациенте из базы П.Г.Шубинка
 */
@Comment("Информация о пациенте из базы П.Г.Шубинка")
@Entity
@Table(schema="SQLUser")
public class PatientInfoShubinok extends BaseEntity implements IImportData {
 

    public long getTime() {
        return theTime  ;
    }

    public void setTime(long aTime) {
        theTime = aTime ;
    }

    /** Страховая компания */
    @Comment("Страховая компания")
    @AFormatFieldSuggest("COMPANY")
    public String getInsuranceCompanyCode() { return theInsuranceCompanyCode ; }
    public void setInsuranceCompanyCode(String aInsuranceCompanyCode) { theInsuranceCompanyCode = aInsuranceCompanyCode ; }

    /** Серия и номер полица */
    @Comment("Серия и номер полица")
    @AFormatFieldSuggest("POLIS")
    public String getPolicySeriesNumber() { return thePolicySeriesNumber ; }
    public void setPolicySeriesNumber(String aPolicySeriesNumber) { thePolicySeriesNumber = aPolicySeriesNumber ; }

    /** Договор */
    @Comment("Договор")
    @AFormatFieldSuggest("DOGOVOR")
    public String getDogovor() { return theDogovor ; }
    public void setDogovor(String aDogovor) { theDogovor = aDogovor ; }

    /** Фимилия */
    @Comment("Фимилия")
    @AFormatFieldSuggest("FIO")
    public String getLastname() {
        return theLastname ;
    }

    @Transient
    @Comment("Фамилия (убраны лидирующие цифры)")
    public String getLastnameGood() {
        if(!StringUtil.isNullOrEmpty(theLastname)
                && theLastname.length()>2
                && Character.isDefined(theLastname.charAt(0))) {
            return theLastname.substring(1).toUpperCase() ;
        } else {
            return theLastname ;
        }
    }

    public void setLastname(String aLastname) { theLastname = aLastname ; }

    /** Имя */
    @Comment("Имя")
    @AFormatFieldSuggest("F_NAME")
    public String getFirstname() { return theFirstname ; }
    public void setFirstname(String aFirstname) { theFirstname = aFirstname ; }

    /** Отчество */
    @Comment("Отчество")
    @AFormatFieldSuggest("L_NAME")
    public String getMiddlename() { return theMiddlename ; }
    public void setMiddlename(String aMiddlename) { theMiddlename = aMiddlename ; }

    /** Тип страхования */
    @Comment("Тип страхования")
    @AFormatFieldSuggest("TIP_S")
    public String getInsuranceType() { return theInsuranceType ; }
    public void setInsuranceType(String aInsuranceType) { theInsuranceType = aInsuranceType ; }

    /** Дата рождения */
    @Comment("Дата рождения")
    @AFormatFieldSuggest("DATA_R")
    public Date getBirthdate() { return theBirthdate ; }
    public void setBirthdate(Date aBirthdate) { theBirthdate = aBirthdate ; }

    /** Район */
    @Comment("Район")
    @AFormatFieldSuggest("ADRES")
    public String getRegion() { return theRegion ; }
    public void setRegion(String aRegion) { theRegion = aRegion ; }

    /** Пол */
    @Comment("Пол")
    @AFormatFieldSuggest("SEX")
    public String getSex() { return theSex ; }
    public void setSex(String aSex) { theSex = aSex ; }

    /** СНИЛС */
    @Comment("СНИЛС")
    @AFormatFieldSuggest("PENS")
    public String getSnils() { return theSnils ; }
    public void setSnils(String aSnils) { theSnils = aSnils ; }

    /** КЛАДР */
    @Comment("КЛАДР")
    @AFormatFieldSuggest("STREET_GNI")
    public String getKladr() { return theKladr ; }
    public void setKladr(String aKladr) { theKladr = aKladr ; }

    /** Номер предприятия новый */
    @Comment("Номер предприятия новый")
    @AFormatFieldSuggest("RNUMBER15")
    public String getOrgCodeNew() { return theOrgCodeNre ; }
    public void setOrgCodeNew(String aOrgCodeNre) { theOrgCodeNre = aOrgCodeNre ; }

    /** Удрес (улица, дом, корпус, квартира) */
    @Comment("Удрес (улица, дом, корпус, квартира)")
    @AFormatFieldSuggest("STREET")
    public String getAddressString() { return theAddressString ; }
    public void setAddressString(String aAddressString) { theAddressString = aAddressString ; }


    @Transient
    @Comment("Дом")
    public String getHouseNumber() {
        return getPosition(theAddressString, 3, ',') ;
    }

    @Transient
    @Comment("Корпус")
    public String getBuildingNumber() {
        return getPosition(theAddressString, 4, ',') ;
    }

    @Transient
    @Comment("Квартира")
    public String getFlatNumber() {
        return getPosition(theAddressString, 5, ',') ;
    }

    @Transient
    @Comment("Серия полиса")
    public String getPolicySeries() {
        return getPosition(thePolicySeriesNumber, 1, '/') ;
    }

    @Transient
    @Comment("Номер полиса")
    public String getPolicyNumber() {
        return getPosition(thePolicySeriesNumber, 2, '/') ;
    }

    private static String getPosition(String aStr, int aPosition, char aDelimeter) {
        String ret = null;
        if(!StringUtil.isNullOrEmpty(aStr)) {
            LogicTokenizer st = new LogicTokenizer(aStr, aDelimeter);
            for(int i=0; i<aPosition ; i++) {
                boolean hasNext = st.hasNext();
                if(hasNext) {
                    ret = st.getNextString() ;
                } else {
                    ret = null ;
                    break ;
                }
            }
        }
        return ret !=null ? ret.trim() : null ;
    }
    /** Удрес (улица, дом, корпус, квартира) */
    private String theAddressString ;
    /** Номер предприятия новый */
    private String theOrgCodeNre ;
    /** КЛАДР */
    private String theKladr ;
    /** СНИЛС */
    private String theSnils ;
    /** Пол */
    private String theSex ;
    /** Район */
    private String theRegion ;
    /** Дата рождения */
    private Date theBirthdate ;
    /** Тип страхования */
    private String theInsuranceType ;
    /** Отчество */
    private String theMiddlename ;
    /** Имя */
    private String theFirstname ;
    /** Фимилия */
    private String theLastname ;
    /** Договор */
    private String theDogovor ;
    /** Серия и номер полица */
    private String thePolicySeriesNumber ;
    /** Страховая компания */
    private String theInsuranceCompanyCode ;
    private long theTime ;
 

}
