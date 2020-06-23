package ru.ecom.mis.ejb.domain.patient;

import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.Entity;
import javax.persistence.Transient;
/**
 * Полис ДМС иногороднего
 * @author oegorova
 */
@Entity
public class MedPolicyDmcForeign  extends MedPolicyOmcForeign {
	///Вычисляемые поля
    @Transient
    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Полис ДМС иногороднего: ") ;
        sb.append(getPolNumber()) ;
        if(getActualDateFrom()!=null && getActualDateTo()!=null) {
            sb.append(", действителен с ") ;
            sb.append(DateFormat.formatToDate(getActualDateFrom())) ;
            sb.append(" по ") ;
            sb.append(DateFormat.formatToDate(getActualDateTo())) ;
        }
        sb.append("СМО:");
        if(getInsuranceCompanyRegion()!=null && !getInsuranceCompanyRegion().getName().equals("")) {
            sb.append(" область: ") ;
            sb.append(getInsuranceCompanyRegion().getName()) ;
        }
        if(getInsuranceCompanyCity()!=null && !getInsuranceCompanyCity().equals("")) {
            sb.append(" город: ") ;
            sb.append(getInsuranceCompanyCity()) ;
        }
        if(getInsuranceCompanyName()!=null && !getInsuranceCompanyName().equals("")) {
            sb.append(" наименование: ") ;
            sb.append(getInsuranceCompanyName()) ;
        }
        return sb.toString() ;
    }
}
