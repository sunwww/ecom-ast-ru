package ru.ecom.mis.ejb.domain.patient;


import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcSprSmo;
import ru.ecom.mis.ejb.domain.patient.voc.VocMedPolicyOmc;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
/**
 * Полис ОМС иногороднего
 * @author oegorova
 *
 */
@Entity
@AIndexes(value = { @AIndex(properties = { "insuranceCompanyCode" },table="MedPolicy") })
public class MedPolicyOmcForeign extends MedPolicy{
	/** Город Страховщика */
	@Comment("Город Страховщика")
	public String getInsuranceCompanyCity() {return theInsuranceCompanyCity;}
	public void setInsuranceCompanyCity(String aOMCCity) {theInsuranceCompanyCity = aOMCCity;}

	/** Область Страховщика */
	@Comment("Область Страховщика")
	@OneToOne
	public OmcKodTer getInsuranceCompanyRegion() {return theInsuranceCompanyRegion;}
	public void setInsuranceCompanyRegion(OmcKodTer aOMCRegion) {theInsuranceCompanyRegion = aOMCRegion;}

	/** Название Страховщика */
	@Comment("Название Страховщика")
	public String getInsuranceCompanyName() {return theInsuranceCompanyName;}
	public void setInsuranceCompanyName(String aOMCName) {theInsuranceCompanyName = aOMCName;}

	
	/** СМО, выдавшей паспорт */
	@Comment("СМО, выдавшей паспорт")
	@OneToOne
	public OmcSprSmo getInsuranceCompanyCode() {return theInsuranceCompanyCode;}
	public void setInsuranceCompanyCode(OmcSprSmo aInsuranceCompanyOgrn) {
		theInsuranceCompanyCode = aInsuranceCompanyOgrn;
	}

	/** СМО, выдавшей паспорт */
	private OmcSprSmo theInsuranceCompanyCode;
	///Вычисляемые поля
    @Transient
    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Полис ОМС иногороднего: ") ;
        sb.append(getPolNumber()) ;
        if(getActualDateFrom()!=null) {
            sb.append(", действителен с ") ;
            sb.append(DateFormat.formatToDate(getActualDateFrom())) ;
            sb.append(" по ") ;
            if (getActualDateTo()!=null) {
            	sb.append(DateFormat.formatToDate(getActualDateTo())) ;
            } else {
            	sb.append("нет даты окончания") ;
            }
        }
        sb.append("Страховщика:");
        if(theInsuranceCompanyRegion!=null && !theInsuranceCompanyRegion.getName().equals("")) {
            sb.append(" область: ") ;
            sb.append(theInsuranceCompanyRegion.getName()) ;
        }
        if(theInsuranceCompanyCity!=null && !theInsuranceCompanyCity.equals("")) {
            sb.append(" город: ") ;
            sb.append(theInsuranceCompanyCity) ;
        }
        if(theInsuranceCompanyName!=null && !theInsuranceCompanyName.equals("")) {
            sb.append(" наименование: ") ;
            sb.append(theInsuranceCompanyName) ;
        }
        return sb.toString() ;
    }
	/** Тип полиса */
	@Comment("Тип полиса")
	@OneToOne
	public VocMedPolicyOmc getType() {return theType;}
	public void setType(VocMedPolicyOmc aType) {theType = aType;}

	/** Тип полиса */
	private VocMedPolicyOmc theType;
	/** Название Страховщика */
	private String theInsuranceCompanyName;
	/** Область Страховщика */
	private OmcKodTer theInsuranceCompanyRegion;
	/** Город Страховщика */
	private String theInsuranceCompanyCity;
	
	/** ОГРН страховщика */
	@Comment("ОГРН страховщика")
	public String getInsuranceCompanyOgrn() {return theInsuranceCompanyOgrn;}
	public void setInsuranceCompanyOgrn(String aInsuranceCompanyOgrn) {theInsuranceCompanyOgrn = aInsuranceCompanyOgrn;}

	/** ОГРН страховщика */
	private String theInsuranceCompanyOgrn;
	
	@Transient
	public String getOgrn() {
		if (theInsuranceCompanyCode!=null) return theInsuranceCompanyCode.getCode() ;
		if (getCompany()!=null) return getCompany().getOgrn() ;
		return "" ;
	}

}
