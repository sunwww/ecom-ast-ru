package ru.ecom.api.record;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
/**Журнал записанных пациентов */
public class ApiRecordJournal extends BaseEntity {
    /** Дата и время создания */
    @Comment("Дата и время создания")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата и время создания */
    private Date theCreateDate ;

    /** Запись */
    @Comment("Запись")
    @Column(length=10000)
    public String getRecord() {return theRecord;}
    public void setRecord(String aRecord) {theRecord = aRecord;}
    /** Запись */
    private String theRecord ;

    public ApiRecordJournal(){setCreateDate(new Date());}
    public ApiRecordJournal(String aRecord){setCreateDate(new Date());setRecord(aRecord);}

}