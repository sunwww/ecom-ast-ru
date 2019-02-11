package ru.ecom.queue.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.Date;

/**Талон в очереди*/
@Entity
public class QueueTicket extends BaseEntity {

    @Transient
    public String getWindowNumber(){
        return theExecutor.getWindowNumber()!=null?theExecutor.getWindowNumber():"---";
    }

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
        return theQueue.getType().getPrefix()+" "+theNumber;
    }
    public QueueTicket(){}
    public QueueTicket(Queue aQueue, Integer aNumber){
        theCreateDate=new Date();
        theQueue=aQueue;
        theNumber=aNumber;

    }

    /** Оператор, обрабатывающий заявку */
    @Comment("Оператор, обрабатывающий заявку")
    @OneToOne
    public WorkFunction getExecutor() {return theExecutor;}
    public void setExecutor(WorkFunction aExecutor) {theExecutor = aExecutor;}
    /** Оператор, обрабатывающий заявку */
    private WorkFunction theExecutor ;

    /** Дата и время начала обработки талона */
    @Comment("Дата и время начала обработки талона")
    public Date getStartExecuteDate() {return theStartExecuteDate;}
    public void setStartExecuteDate(Date aStartExecuteDate) {theStartExecuteDate = aStartExecuteDate;}
    /** Дата и время начала обработки талона */
    private Date theStartExecuteDate ;

    /** Оператор, закончивший обработку талона */
    @Comment("Оператор, закончивший обработку талона")
    @OneToOne
    public WorkFunction getFinishExecutor() {return theFinishExecutor;}
    public void setFinishExecutor(WorkFunction aFinishExecutor) {theFinishExecutor = aFinishExecutor;}
    /** Оператор, закончивший обработку талона */
    private WorkFunction theFinishExecutor ;

    /** Дата окончания обслуживания */
    @Comment("Дата окончания обслуживания")
    public Date getFinishExecuteDate() {return theFinishExecuteDate;}
    public void setFinishExecuteDate(Date aFinishExecuteDate) {theFinishExecuteDate = aFinishExecuteDate;}
    /** Дата окончания обслуживания */
    private Date theFinishExecuteDate ;

}
