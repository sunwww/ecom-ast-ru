package ru.ecom.mis.ejb.domain.patient;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.nuzmsh.util.format.DateFormat;

/**
 * Полис ДМС
 */
@Entity
@Table(schema="SQLUser")
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
