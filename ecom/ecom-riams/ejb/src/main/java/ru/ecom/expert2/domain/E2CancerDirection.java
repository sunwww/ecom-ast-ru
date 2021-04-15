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
/** Направление  на лечение (онкология) */
public class E2CancerDirection extends BaseEntity {

    /** ЛПУ, куда сделали направление */
    private String theDirectLpu ;

    /** Случай рака */
    @ManyToOne
    private E2CancerEntry theCancerEntry ;
    
    /** Дата направления */
    private Date theDate ;
    
    /** Вид направление */
    private String theType ;
    
    /** Метод диагностического исследования */
    private String theSurveyMethod ;

    /** Медицинская услуга */
    private String theMedService ;

    public E2CancerDirection() {}
    public E2CancerDirection (E2CancerEntry aCancerEntry) {theCancerEntry=aCancerEntry;}
    public E2CancerDirection(E2CancerDirection aDirection, E2CancerEntry aCancerEntry) {
        theCancerEntry=aCancerEntry;
        theDirectLpu=aDirection.theDirectLpu;
        theMedService=aDirection.theMedService;
        theDate = aDirection.theDate;
        theType=aDirection.theType;
        theSurveyMethod=aDirection.theSurveyMethod;
    }
    

}