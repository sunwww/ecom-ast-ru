package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
@Getter
@Setter
/**Противопоказания (онкология)*/
public class E2CancerRefusal extends BaseEntity {

    /** Случай рака */
    @Comment("Случай рака")
    @ManyToOne
    public E2CancerEntry getCancerEntry() {return cancerEntry;}
    private E2CancerEntry cancerEntry;

    /** Код противопоказания */
    private String code;

    /** Дата регистрации противопоказания */
    private Date date;

    public E2CancerRefusal(){}
    public E2CancerRefusal(E2CancerEntry aCancerEntry) {
        cancerEntry =aCancerEntry;}
    public E2CancerRefusal(E2CancerRefusal aRef, E2CancerEntry aCancerEntry) {
        cancerEntry =aCancerEntry;
        code = aRef.code;
        date =aRef.date;
    }
}