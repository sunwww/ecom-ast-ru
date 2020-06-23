package ru.ecom.mis.ejb.domain.patient;

import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Полис ДМС
 */
@Entity
public class MedPolicyDmc extends MedPolicy {
    @Transient
    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append("ДМС: ") ;
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
            sb.append(", код -") ;
            sb.append(getCompany().getOmcCode());
        }
        return sb.toString() ;
    }
}
