package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;

import javax.persistence.Entity;
import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@Setter
public class E2ExportPacketJournal extends BaseEntity {
    /** Номер счета */
    private String billNumber;

    /** Дата счета */
    private Date billDate;

    /** Имя файла */
    private String filename;

    /** Дата формирования файла */
    private Date createDate;

    /** Время формирования файла */
    private Time createTime;

    public E2ExportPacketJournal(String aBillNumber, Date aBillDate, String aFilename) {
        Long time = System.currentTimeMillis();
        Date currentDate = new java.sql.Date(time);
        Time currentTime = new Time(time);
        billDate =aBillDate;
        billNumber =aBillNumber;
        filename =aFilename;
        createDate =currentDate;
        createTime =currentTime;
    }
    public E2ExportPacketJournal() {}
}
