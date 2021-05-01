package ru.ecom.mis.ejb.domain.patient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.voc.VocMedPolicyOmc;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Полис ОМС
 */
@Entity
@Getter
@Setter
public class MedPolicyOmc extends MedPolicy {

	/** Город нахождения СМО */
	private String insuranceCompanyCity;

    @Transient
    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append("ОМС: ") ;
        sb.append(getSeries()) ;
        sb.append(" ") ;
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
    public VocOrg getOrg() { return org ; }
    @Deprecated
    private VocOrg org ;

    /** Прикрепленное ЛПУ */
	@Comment("Прикрепленное ЛПУ")
	@OneToOne
    @Deprecated
	public MisLpu getAttachedLpu() {
		return attachedLpu;
	}
    @Deprecated
    private MisLpu attachedLpu;

	/** Тип полиса */
	@Comment("Тип полиса")
	@OneToOne
	public VocMedPolicyOmc getType() {return type;}

	/** Тип полиса */
	private VocMedPolicyOmc type;

}
