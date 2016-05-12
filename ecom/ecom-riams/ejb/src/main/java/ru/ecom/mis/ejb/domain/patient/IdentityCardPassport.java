package ru.ecom.mis.ejb.domain.patient;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.mis.ejb.domain.patient.voc.VocPassportWhomIssue;
import ru.nuzmsh.util.format.DateFormat;

/**
 * @author  azviagin
 */
@Entity
@Table(schema="SQLUser")
public class IdentityCardPassport extends IdentityCard {

    /** Серия */
    public String getSeries() { return theSeries ; }
    public void setSeries(String aSeries) { theSeries = aSeries ; }

    /** Удостоверение личности выдан */
    public Date getDateIssue() { return theDateIssue ; }
    public void setDateIssue(Date aDateIssue) { theDateIssue = aDateIssue ; }

    /** Кто выдaл */
    public VocPassportWhomIssue getWhomIssue() { return theWhomIssue ; }
    public void setWhomIssue(VocPassportWhomIssue aWhomIssue) { theWhomIssue = aWhomIssue ; }

    @Transient
    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Паспорт ") ;
        sb.append(theSeries) ;
        sb.append(" ") ;
        sb.append(getNumber()) ;
        if(getDateIssue()!=null || getWhomIssue()!=null) {
            sb.append(", выдан") ;
            if(getWhomIssue()!=null) {
                sb.append(" ") ;
                sb.append(getWhomIssue().getName()) ;
            }
            if(getDateIssue()!=null) {
                sb.append(" ") ;
                sb.append(DateFormat.formatToDate(getDateIssue())) ;
            }
        }
        return sb.toString() ;
    }

    /** Кто выдaл */
    private VocPassportWhomIssue theWhomIssue ;
    /** Удостоверение личности выдан */
    private Date theDateIssue ;
    /** Серия */
    private String theSeries ;

}
