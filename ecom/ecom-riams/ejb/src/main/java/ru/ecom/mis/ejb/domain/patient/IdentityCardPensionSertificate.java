package ru.ecom.mis.ejb.domain.patient;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Пенсионное свидетельство
 */
@Entity
@Table(schema="SQLUser")
public class IdentityCardPensionSertificate extends IdentityCard {
    @Transient
    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Пенсионное свидетельство № ") ;
        sb.append(getNumber()) ;
        return sb.toString() ;
    }
}
