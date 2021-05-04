package ru.ecom.queue.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.Date;

/**Талон в очереди*/
@Entity
@Getter
@Setter
public class QueueTicket extends BaseEntity {

    @Transient
    public String getWindowNumber(){
        return executor.getWindowNumber()!=null?executor.getWindowNumber():"---";
    }

    /** В какой очереди находится */
    @Comment("В какой очереди находится")
    @OneToOne
    public Queue getQueue() {return queue;}
    /** В какой очереди находится */
    private Queue queue ;

    /** Дата создания билета */
    @Temporal(TemporalType.TIMESTAMP)
    @Comment("Дата создания билета")
    public Date getCreateDate() {return createDate;}
    /** Дата создания билета */
    private Date createDate ;

    /** Номер талона */
    private Integer number ;

    /** Полный номер */
    @Comment("Полный номер")
    @Transient
    public String getFullNumber() {
        return queue.getType().getPrefix()+" "+number;
    }
    public QueueTicket(){}
    public QueueTicket(Queue aQueue, Integer aNumber){
        createDate=new Date();
        queue=aQueue;
        number=aNumber;

    }

    /** Оператор, обрабатывающий заявку */
    @Comment("Оператор, обрабатывающий заявку")
    @OneToOne
    public WorkFunction getExecutor() {return executor;}
    /** Оператор, обрабатывающий заявку */
    private WorkFunction executor ;

    /** Дата и время начала обработки талона */
    private Date startExecuteDate ;

    /** Оператор, закончивший обработку талона */
    @Comment("Оператор, закончивший обработку талона")
    @OneToOne
    public WorkFunction getFinishExecutor() {return finishExecutor;}
    /** Оператор, закончивший обработку талона */
    private WorkFunction finishExecutor ;

    /** Дата окончания обслуживания */
    private Date finishExecuteDate ;

}
