package ru.ecom.mis.ejb.domain.external;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class PatientInfoShubinok extends BaseEntity implements IImportData {
 

    /** Страховая компания */
    @Comment("Страховая компания")
    @AFormatFieldSuggest("COMPANY")
    public String getInsuranceCompanyCode() { return insuranceCompanyCode ; }

    /** Серия и номер полица */
    @Comment("Серия и номер полица")
    @AFormatFieldSuggest("POLIS")
    public String getPolicySeriesNumber() { return policySeriesNumber ; }

    /** Договор */
    @Comment("Договор")
    @AFormatFieldSuggest("DOGOVOR")
    public String getDogovor() { return dogovor ; }

    /** Фимилия */
    @Comment("Фимилия")
    @AFormatFieldSuggest("FIO")
    public String getLastname() {
        return lastname ;
    }

    @Transient
    @Comment("Фамилия (убраны лидирующие цифры)")
    public String getLastnameGood() {
        if(!StringUtil.isNullOrEmpty(lastname)
                && lastname.length()>2
                && Character.isDefined(lastname.charAt(0))) {
            return lastname.substring(1).toUpperCase() ;
        } else {
            return lastname ;
        }
    }


    /** Имя */
    @Comment("Имя")
    @AFormatFieldSuggest("F_NAME")
    public String getFirstname() { return firstname ; }

    /** Отчество */
    @Comment("Отчество")
    @AFormatFieldSuggest("L_NAME")
    public String getMiddlename() { return middlename ; }

    /** Тип страхования */
    @Comment("Тип страхования")
    @AFormatFieldSuggest("TIP_S")
    public String getInsuranceType() { return insuranceType ; }

    /** Дата рождения */
    @Comment("Дата рождения")
    @AFormatFieldSuggest("DATA_R")
    public Date getBirthdate() { return birthdate ; }

    /** Район */
    @Comment("Район")
    @AFormatFieldSuggest("ADRES")
    public String getRegion() { return region ; }

    /** Пол */
    @Comment("Пол")
    @AFormatFieldSuggest("SEX")
    public String getSex() { return sex ; }

    /** СНИЛС */
    @Comment("СНИЛС")
    @AFormatFieldSuggest("PENS")
    public String getSnils() { return snils ; }

    /** КЛАДР */
    @Comment("КЛАДР")
    @AFormatFieldSuggest("STREET_GNI")
    public String getKladr() { return kladr ; }

    /** Номер предприятия новый */
    @Comment("Номер предприятия новый")
    @AFormatFieldSuggest("RNUMBER15")
    public String getOrgCodeNew() { return orgCodeNre ; }
    public void setOrgCodeNew(String aOrgCodeNre) { orgCodeNre = aOrgCodeNre ; }

    /** Удрес (улица, дом, корпус, квартира) */
    @Comment("Удрес (улица, дом, корпус, квартира)")
    @AFormatFieldSuggest("STREET")
    public String getAddressString() { return addressString ; }


    @Transient
    @Comment("Дом")
    public String getHouseNumber() {
        return getPosition(addressString, 3, ',') ;
    }

    @Transient
    @Comment("Корпус")
    public String getBuildingNumber() {
        return getPosition(addressString, 4, ',') ;
    }

    @Transient
    @Comment("Квартира")
    public String getFlatNumber() {
        return getPosition(addressString, 5, ',') ;
    }

    @Transient
    @Comment("Серия полиса")
    public String getPolicySeries() {
        return getPosition(policySeriesNumber, 1, '/') ;
    }

    @Transient
    @Comment("Номер полиса")
    public String getPolicyNumber() {
        return getPosition(policySeriesNumber, 2, '/') ;
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
    private String addressString ;
    /** Номер предприятия новый */
    private String orgCodeNre ;
    /** КЛАДР */
    private String kladr ;
    /** СНИЛС */
    private String snils ;
    /** Пол */
    private String sex ;
    /** Район */
    private String region ;
    /** Дата рождения */
    private Date birthdate ;
    /** Тип страхования */
    private String insuranceType ;
    /** Отчество */
    private String middlename ;
    /** Имя */
    private String firstname ;
    /** Фимилия */
    private String lastname ;
    /** Договор */
    private String dogovor ;
    /** Серия и номер полица */
    private String policySeriesNumber ;
    /** Страховая компания */
    private String insuranceCompanyCode ;
    private long time ;
 

}
