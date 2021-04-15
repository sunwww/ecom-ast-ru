package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
@Getter
@Setter
@Accessors(prefix = "the")
public class E2CancerDiagnostic extends BaseEntity {

    /** Случай рака */
    @ManyToOne
    private E2CancerEntry theCancerEntry ;
    
    /** Тип показателя */
    private String theType ;
    
    /** Код показателя */
    private String theCode ;

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
    private Date theBiopsyDate ;
}