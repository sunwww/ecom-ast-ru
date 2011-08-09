package ru.ecom.mis.ejb.domain.patient;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;

/**
 * Документ, удостоверяющий личность
 */
@Entity
@AIndexes({
	@AIndex(properties={"patient"})
})
@Table(schema="SQLUser")
public class IdentityCard extends BaseEntity {


    /** Номер */
    public String getNumber() { return theNumber ; }
    public void setNumber(String aNumber) { theNumber = aNumber ; }

    /** Пациент */
    @ManyToOne
    public Patient getPatient() { return thePatient ; }
    public void setPatient(Patient aPatient) { thePatient = aPatient ; }

    /** Текст документа */
    @Transient
    public String getText() {
        return "Нужно переопределить" ;
    }

    public void setText(String aText) {}

    /** Номер */
    private String theNumber ;
    /** Пациент */
    private Patient thePatient ;




}
