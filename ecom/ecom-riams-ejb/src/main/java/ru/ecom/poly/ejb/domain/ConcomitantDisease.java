package ru.ecom.poly.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcMkb10;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 15.01.2007
 * Time: 20:59:34
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Deprecated
@Table(schema="SQLUser")
public class ConcomitantDisease extends BaseEntity {

    /** Талоны **/
    @ManyToOne
    public Ticket getTicket() { return theTicket; }
    public void setTicket(Ticket aTicket) { theTicket = aTicket; }

    /** @return Диагноз **/
    @ManyToOne
    public OmcMkb10 getDiagnosis() { return theDiagnosis; }
    public void setDiagnosis(OmcMkb10 aDiagnosis) { theDiagnosis = aDiagnosis; }

    /** @return Диагноз **/
    @Transient
    public String getDiagnosisName() { return theDiagnosis!=null ? theDiagnosis.getName() : "" ; }
    public void setDiagnosisName(String aDiagnosisName) {  }
     
    /** Диагноз **/
    private OmcMkb10 theDiagnosis;

    /** Талоны **/
    private Ticket theTicket;

}
