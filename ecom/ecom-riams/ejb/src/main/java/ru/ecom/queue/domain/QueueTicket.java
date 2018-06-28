package ru.ecom.queue.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.Date;

/**Талон в очереди*/
@Entity
public class QueueTicket extends BaseEntity {

    /** В какой очереди находится */
    @Comment("В какой очереди находится")
    @OneToOne
    public Queue getQueue() {return theQueue;}
    public void setQueue(Queue aQueue) {theQueue = aQueue;}
    /** В какой очереди находится */
    private Queue theQueue ;

    /** Дата создания билета */
    @Temporal(TemporalType.TIMESTAMP)
    @Comment("Дата создания билета")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата создания билета */
    private Date theCreateDate ;

    /** Номер талона */
    @Comment("Номер талона")
    public Integer getNumber() {return theNumber;}
    public void setNumber(Integer aNumber) {theNumber = aNumber;}
    /** Номер талона */
    private Integer theNumber ;

    /** Полный номер */
    @Comment("Полный номер")
    @Transient
    public String getFullNumber() {
        return theQueue.getType().getPrefix()+theNumber;
    }
    public QueueTicket(){}
    public QueueTicket(Queue aQueue, Integer aNumber){
        theCreateDate=new Date();
        theQueue=aQueue;
        theNumber=aNumber;

    }

}
