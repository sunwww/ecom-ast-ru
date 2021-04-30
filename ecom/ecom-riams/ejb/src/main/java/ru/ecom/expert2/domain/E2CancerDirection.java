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
/** Направление  на лечение (онкология) */
public class E2CancerDirection extends BaseEntity {

    /** ЛПУ, куда сделали направление */
    private String directLpu;

    /** Случай рака */
    @Comment("Случай рака")
    @ManyToOne
    public E2CancerEntry getCancerEntry() {return cancerEntry;}
    private E2CancerEntry cancerEntry;
    
    /** Дата направления */
    private Date date;
    
    /** Вид направление */
    private String type;
    
    /** Метод диагностического исследования */
    private String surveyMethod;

    /** Медицинская услуга */
    private String medService;

    public E2CancerDirection() {}
    public E2CancerDirection (E2CancerEntry aCancerEntry) {
        cancerEntry =aCancerEntry;}
    public E2CancerDirection(E2CancerDirection aDirection, E2CancerEntry aCancerEntry) {
        cancerEntry =aCancerEntry;
        directLpu =aDirection.directLpu;
        medService =aDirection.medService;
        date = aDirection.date;
        type =aDirection.type;
        surveyMethod =aDirection.surveyMethod;
    }
    

}