package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
public class E2CancerDiagnostic extends BaseEntity {

    /** Случай рака */
    @Comment("Случай рака")
    @ManyToOne
    public E2CancerEntry getCancerEntry() {return theCancerEntry;}
    public void setCancerEntry(E2CancerEntry aCancerEntry) {theCancerEntry = aCancerEntry;}
    /** Случай рака */
    private E2CancerEntry theCancerEntry ;
    
    /** Тип показателя */
    @Comment("Тип показателя")
    public String getType() {return theType;}
    public void setType(String aType) {theType = aType;}
    /** Тип показателя */
    private String theType ;
    
    /** Код показателя */
    @Comment("Код показателя")
    public String getCode() {return theCode;}
    public void setCode(String aCode) {theCode = aCode;}
    /** Код показателя */
    private String theCode ;

    /** Результат показателя */
    @Comment("Результат показателя")
    public String getResult() {return theResult;}
    public void setResult(String aResult) {theResult = aResult;}
    /** Результат показателя */
    private String theResult ;

    public E2CancerDiagnostic(){}
    public E2CancerDiagnostic(E2CancerEntry aCancerEntry){theCancerEntry=aCancerEntry;}
    public E2CancerDiagnostic(E2CancerDiagnostic aDiag, E2CancerEntry aCancerEntry){
        theCancerEntry=aCancerEntry;
        theType = aDiag.theType;
        theCode=aDiag.theCode;
        theResult=aDiag.theResult;
    }

    /** Дата взятия биопсийного материала * Олег */
    @Comment("Дата взятия биопсийного материала * Олег")
    public Date getBiopsyDate() {return theBiopsyDate;}
    public void setBiopsyDate(Date aBiopsyDate) {theBiopsyDate = aBiopsyDate;}
    private Date theBiopsyDate ;
}