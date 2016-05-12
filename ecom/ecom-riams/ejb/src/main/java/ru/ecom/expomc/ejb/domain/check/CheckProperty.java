package ru.ecom.expomc.ejb.domain.check;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;

/**
 * @author esinev
 * Date: 23.08.2006
 * Time: 13:34:32
 *
 * Changes:
 *  IKO-002 070321 *** Задан размер поля
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name="CHECKER_PROPERTY",schema="SQLUser")
public class CheckProperty extends BaseEntity {
    /** Свойство */
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Значение свойства */
    // IKO002 ***
    //@Column(columnDefinition="TEXT")
    @Column(length = 400)
    // IKO002 ===
    public String getValue() { return theValue ; }
    public void setValue(String aValue) { theValue = aValue ; }

    /** Проверка */
    @ManyToOne
    public Check getCheck() { return theCheck ; }
    public void setCheck(Check aCheck) { theCheck = aCheck ; }

    /** Проверка */
    private Check theCheck ;
    /** Значение свойства */
    private String theValue ;
    /** Свойство */
    private String theProperty ;
}
