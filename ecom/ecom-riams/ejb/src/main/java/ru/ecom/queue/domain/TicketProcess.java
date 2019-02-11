package ru.ecom.queue.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.OneToOne;
import java.util.Date;

//@Entity
public class TicketProcess extends BaseEntity {
    /** Талон */
    @Comment("Талон")
    @OneToOne
    public QueueTicket getTicket() {return theTicket;}
    public void setTicket(QueueTicket aTicket) {theTicket = aTicket;}
    /** Талон */
    private QueueTicket theTicket ;

    /** Дата начала обслуживания */
    @Comment("Дата начала обслуживания")
    public Date getStartDate() {return theStartDate;}
    public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
    /** Дата начала обслуживания */
    private Date theStartDate ;
}
