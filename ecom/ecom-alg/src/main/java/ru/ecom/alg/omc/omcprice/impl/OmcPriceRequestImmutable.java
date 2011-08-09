package ru.ecom.alg.omc.omcprice.impl;

import ru.ecom.alg.omc.omcprice.IOmcPriceRequest;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Неизменяемые параметры для определения цены
 */
public class OmcPriceRequestImmutable implements IOmcPriceRequest, Serializable {

    public OmcPriceRequestImmutable(
            Date aBirthDate
            , Date aAdmissionDate
            , Date aDischargeDate
            , int aBedDays
            , String aMkb
            , boolean isPlanovaya
            , String aUsl
            , int aDepartmentLevel
            , String aDepartmentCode
            , int aOsl
            , boolean aCanUseEmergencyKoef
            , int aKodLpu) {
        theBirthDate = aBirthDate;
        theAdmissionDate = aAdmissionDate;
        theDischargeDate = aDischargeDate;
        theBedDays = aBedDays;
        theMkb = aMkb;
        theIsPlanovaya = isPlanovaya;
        theUsl = aUsl;
        theDepartmentLevel = aDepartmentLevel;
        theDepartmentCode = aDepartmentCode;
        theCanUseEmergencyKoef = aCanUseEmergencyKoef;
        theOsl = aOsl;
        theKodLpu = aKodLpu;
    }


    private String fmt(Date aDate) {
    	SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy") ;
    	return aDate !=null ? FORMAT.format(aDate) : "" ; 
    }
    @Override
	public String toString() {
		StringBuilder sb = new StringBuilder() ;
		sb.append("Запрос цены [дата_рождения=").append(fmt(theBirthDate)) ;
		sb.append(", дата_поступления=").append(fmt(theAdmissionDate)) ;
		sb.append(", дата_выписки=").append(fmt(theDischargeDate)) ;
		sb.append(", койко-дней=").append(theBedDays) ;
		sb.append(", МКБ=").append(theMkb) ;
		sb.append(theIsPlanovaya ? ", плановая":", экстренная") ;
		sb.append(", услуга=").append(theUsl) ;
		sb.append(", уровень_отделения=").append(theDepartmentLevel) ;
		sb.append(", код_отделения=").append(theDepartmentCode) ;
		sb.append(", успользовать_1.16_коэффициент=").append(theCanUseEmergencyKoef) ;
		sb.append(", осложнение=").append(theOsl) ;
		sb.append(", код ЛПУ=").append(theKodLpu) ;
		sb.append(" ]") ;
		
		return sb.toString();
	}


	public boolean canUseEmergencyKoef() {
        return theCanUseEmergencyKoef;
    }

    public boolean isPlanovaya() {
        return theIsPlanovaya;
    }

    public Date getBirthDate() {
        return theBirthDate;
    }

    public Date getAdmissionDate() {
        return theAdmissionDate;
    }

    public Date getDischargeDate() {
        return theDischargeDate;
    }

    public String getUslOmcCode() {
        return theUsl;
    }

    public String getMkbWithOmcExtension() {
        return theMkb;
    }

    public int getOsl() {
        return theOsl;
    }

    public String getDepartmentCode() {
        return theDepartmentCode;
    }

    public int getDepartmentLevel() {
        return theDepartmentLevel;
    }

    public int getBedDays() {
        return theBedDays;
    }
        
    public int getKodLpu() {
        return theKodLpu;
    
    }

    private final Date theBirthDate;
    private final Date theAdmissionDate;
    private final Date theDischargeDate;
    private final int theBedDays;
    private final String theMkb;
    private final boolean theIsPlanovaya;
    private final String theUsl;
    private final int theDepartmentLevel;
    private final String theDepartmentCode;
    private final boolean theCanUseEmergencyKoef;
    private final int theOsl;
    private final int theKodLpu;

    }
