package ru.ecom.mis.ejb.domain.usl;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.registry.LpuFond;
import ru.ecom.mis.ejb.domain.patient.Patient;

/**
 * Услуга
 */
@Entity
@Table(name="MIS_USL",schema="SQLUser")
@AIndexes({
        @AIndex(unique = false, properties= {"render","dischargeDate","patient"})
})
public class MisUsl extends LpuFond {

    /** Пациент */
    @ManyToOne(fetch = FetchType.LAZY)
    public Patient getPatient() { return thePatient ; }
    public void setPatient(Patient aPatient) { thePatient = aPatient ; }

    /** Пациент */
    private Patient thePatient ;
}
