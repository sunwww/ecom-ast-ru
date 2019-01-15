package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import java.sql.Date;
import java.sql.Time;

@Entity
public class E2ExportPacketJournal extends BaseEntity {
    /** Номер счета */
    @Comment("Номер счета")
    public String getBillNumber() {return theBillNumber;}
    public void setBillNumber(String aBillNumber) {theBillNumber = aBillNumber;}
    /** Номер счета */
    private String theBillNumber ;

    /** Дата счета */
    @Comment("Дата счета")
    public Date getBillDate() {return theBillDate;}
    public void setBillDate(Date aBillDate) {theBillDate = aBillDate;}
    /** Дата счета */
    private Date theBillDate ;

    /** Имя файла */
    @Comment("Имя файла")
    public String getFilename() {return theFilename;}
    public void setFilename(String aFilename) {theFilename = aFilename;}
    /** Имя файла */
    private String theFilename ;

    /** Дата формирования файла */
    @Comment("Дата формирования файла")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата формирования файла */
    private Date theCreateDate ;

    /** Время формирования файла */
    @Comment("Время формирования файла")
    public Time getCreateTime() {return theCreateTime;}
    public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
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
