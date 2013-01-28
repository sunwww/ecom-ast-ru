package ru.ecom.mis.ejb.domain.patient;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.voc.VocMedPolicyOmc;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.format.DateFormat;

/**
 * Полис ОМС
 */
@Entity
@Table(schema="SQLUser")
public class MedPolicyOmc extends MedPolicy {
	
	

	
	/** Город нахождения СМО */
	@Comment("Город нахождения СМО")
	public String getInsuranceCompanyCity() {
		return theInsuranceCompanyCity;
	}

	public void setInsuranceCompanyCity(String aInsuranceCompanyCity) {
		theInsuranceCompanyCity = aInsuranceCompanyCity;
	}

	/** Город нахождения СМО */
	private String theInsuranceCompanyCity;
	


    @Transient
    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append("ОМС: ") ;
        sb.append(getSeries()) ;
        sb.append(" ") ;
        sb.append(getPolNumber()) ;
        if(getActualDateFrom()!=null && getActualDateTo()!=null) {
            sb.append(", действителен с ") ;
            sb.append(DateFormat.formatToDate(getActualDateFrom())) ;
            sb.append(" по ") ;
            sb.append(DateFormat.formatToDate(getActualDateTo())) ;
        }
        if(getCompany()!=null) {
            sb.append(", ") ;
            sb.append(getCompany().getName()) ;
            sb.append(", код - ") ;
            sb.append(getCompany().getOmcCode());
        }
        return sb.toString() ;
    }

    /** Предприятие */
    @OneToOne
    public VocOrg getOrg() { return theOrg ; }
    public void setOrg(VocOrg aOrg) { theOrg = aOrg ; }

    /** Прикрепленное ЛПУ */
	@Comment("Прикрепленное ЛПУ")
	@OneToOne
	public MisLpu getAttachedLpu() {
		return theAttachedLpu;
	}

	public void setAttachedLpu(MisLpu aAttachedLpu) {
		theAttachedLpu = aAttachedLpu;
	}

	/** Тип полиса */
	@Comment("Тип полиса")
	@OneToOne
	public VocMedPolicyOmc getType() {return theType;}
	public void setType(VocMedPolicyOmc aType) {theType = aType;}

	/** Тип полиса */
	private VocMedPolicyOmc theType;
	/** Прикрепленное ЛПУ */
	private MisLpu theAttachedLpu;
    /** Предприятие */
    private VocOrg theOrg ;
}
