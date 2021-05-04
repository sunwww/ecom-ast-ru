package ru.ecom.queue.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.queue.domain.voc.VocQueue;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

/* Очередь */
@Entity
@Getter
@Setter
public class Queue extends BaseEntity {
    /** Тип очереди */
    @Comment("Тип очереди")
    @OneToOne
    public VocQueue getType() {return type;}
    /** Тип очереди */
    private VocQueue type ;

    /** Дата очереди */
    private Date date ;

    public Queue() {
        date=new Date(System.currentTimeMillis());
    }
    public Queue(VocQueue aVocQueue) {
        type=aVocQueue;
        date=new Date(System.currentTimeMillis());
    }

    public Queue(VocQueue aVocQueue, Date aDate) {type=aVocQueue;date=aDate;}
}
