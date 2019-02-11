package ru.ecom.mis.ejb.domain.patient.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
/**Справочник причин снятия с Д учета*/
public class VocDispensaryEnd extends VocBaseEntity {
    
    /** Примечание */
    @Comment("Примечание")
    public String getComment() {return theComment;}
    public void setComment(String aComment) {theComment = aComment;}
    /** Примечание */
    private String theComment ;
}
