package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
/**Противопоказания (онкология)*/
public class E2CancerRefusal extends BaseEntity {

    /** Случай рака */
    @Comment("Случай рака")
    @ManyToOne
    public E2CancerEntry getCancerEntry() {return theCancerEntry;}
    public void setCancerEntry(E2CancerEntry aCancerEntry) {theCancerEntry = aCancerEntry;}
    /** Случай рака */
    private E2CancerEntry theCancerEntry ;

    /** Код противопоказания */
    @Comment("Код противопоказания")
    public String getCode() {return theCode;}
    public void setCode(String aCode) {theCode = aCode;}
    /** Код противопоказания */
    private String theCode ;

    /** Дата регистрации противопоказания */
    @Comment("Дата регистрации противопоказания")
    public Date getDate() {return theDate;}
    public void setDate(Date aDate) {theDate = aDate;}
    /** Дата регистрации противопоказания */
    private Date theDate ;

    public E2CancerRefusal(){}
    public E2CancerRefusal(E2CancerEntry aCancerEntry) {theCancerEntry=aCancerEntry;}
    public E2CancerRefusal(E2CancerRefusal aRef, E2CancerEntry aCancerEntry) {
        theCancerEntry=aCancerEntry;
        theCode = aRef.theCode;
        theDate=aRef.theDate;
    }
}