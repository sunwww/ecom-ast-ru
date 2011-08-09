package ru.ecom.poly.ejb.domain.voc;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;

/**
 * @author ikouzmin 28.04.2007 15:04:47
 */
@Entity
@Deprecated
@Table(schema="SQLUser")
public class MedUslugaTariff extends VocIdNameOmcCode {
    
    /** Стоимость услуги */
    public BigDecimal getPrice() { return thePrice ; }
    public void setPrice(BigDecimal aPrice) { thePrice = aPrice ; }
    
    /** Стоимость услуги */
    private BigDecimal thePrice ;
    
}
