package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@Setter
@Accessors(prefix = "the")
public class E2ExportPacketJournal extends BaseEntity {
    /** Номер счета */
    private String theBillNumber ;

    /** Дата счета */
    private Date theBillDate ;

    /** Имя файла */
    private String theFilename ;

    /** Дата формирования файла */
    private Date theCreateDate ;

    /** Время формирования файла */
    private Time theCreateTime ;

    public E2ExportPacketJournal(String aBillNumber, Date aBillDate, String aFilename) {
        Long time = System.currentTimeMillis();
        Date currentDate = new java.sql.Date(time);
        Time currentTime = new Time(time);
        theBillDate=aBillDate;theBillNumber=aBillNumber;theFilename=aFilename;
        theCreateDate=currentDate;theCreateTime=currentTime;
    }
    public E2ExportPacketJournal() {}
}
