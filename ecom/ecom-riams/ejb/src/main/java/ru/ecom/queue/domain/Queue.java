package ru.ecom.queue.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.queue.domain.voc.VocQueue;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

/* Очередь */
@Entity
public class Queue extends BaseEntity {
    /** Тип очереди */
    @Comment("Тип очереди")
    @OneToOne
    public VocQueue getType() {return theType;}
    public void setType(VocQueue aType) {theType = aType;}
    /** Тип очереди */
    private VocQueue theType ;

    /** Дата очереди */
    @Comment("Дата очереди")
    public Date getDate() {return theDate;}
    public void setDate(Date aDate) {theDate = aDate;}
    /** Дата очереди */
    private Date theDate ;

    public Queue() {
        theDate=new Date(System.currentTimeMillis());
    }
    public Queue(VocQueue aVocQueue) {
        theType=aVocQueue;
        theDate=new Date(System.currentTimeMillis());
    }

    public Queue(VocQueue aVocQueue, Date aDate) {theType=aVocQueue;theDate=aDate;}
}
