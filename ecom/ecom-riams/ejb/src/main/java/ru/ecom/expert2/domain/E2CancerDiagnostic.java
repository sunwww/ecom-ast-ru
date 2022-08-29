package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
@Getter
@Setter
@AIndexes({
        @AIndex(properties = {"cancerEntry"})
})
public class E2CancerDiagnostic extends BaseEntity {

    /** Случай рака */
    @Comment("Случай рака")
    @ManyToOne
    public E2CancerEntry getCancerEntry() {return cancerEntry;}
    private E2CancerEntry cancerEntry;
    
    /** Тип показателя */
    private String type;
    
    /** Код показателя */
    private String code;

    /** Результат показателя */
    private String result;

    public E2CancerDiagnostic(){}
    public E2CancerDiagnostic(E2CancerEntry aCancerEntry){
        cancerEntry =aCancerEntry;}
    public E2CancerDiagnostic(E2CancerDiagnostic aDiag, E2CancerEntry aCancerEntry){
        cancerEntry =aCancerEntry;
        type = aDiag.type;
        code =aDiag.code;
        result =aDiag.result;
    }

    /** Дата взятия биопсийного материала * Олег */
    private Date biopsyDate;
}