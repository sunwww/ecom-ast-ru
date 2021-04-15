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
/**Противопоказания (онкология)*/
public class E2CancerRefusal extends BaseEntity {

    /** Случай рака */
    @ManyToOne
    private E2CancerEntry theCancerEntry ;

    /** Код противопоказания */
    private String theCode ;

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