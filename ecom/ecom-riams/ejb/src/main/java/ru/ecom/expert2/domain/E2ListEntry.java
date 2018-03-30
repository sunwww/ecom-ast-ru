package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.expert2.domain.voc.VocListEntryType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

/**
 * Список записей (заполнение
 */
@Entity
@UnDeletable
public class E2ListEntry extends BaseEntity {
    public E2ListEntry(){}
    public E2ListEntry(E2ListEntry aListEntry, String aNewName) {
        theName=(aNewName!=null?aNewName:"КОПИЯ_"+aListEntry.getName());
        theStartDate=aListEntry.getStartDate();
        theFinishDate=aListEntry.getFinishDate();
        theEntryType=aListEntry.getEntryType();
        theLpuOmcCode=aListEntry.theLpuOmcCode;

    }

    /** Закрыто для редакторирования */
    @Comment("Закрыто для редакторирования")
    public Boolean getIsClosed() {return theIsClosed;}
    public void setIsClosed(Boolean aIsClosed) {theIsClosed = aIsClosed;}
    /** Закрыто для редакторирования */
    private Boolean theIsClosed ;

    /** Имя заполнения */
    @Comment("Имя заполнения")
    public String getName() {return theName;}
    public void setName(String aName) {theName = aName;}
    /** Имя заполнения */
    private String theName ;

    /** Дата начала периода */
    @Comment("Дата начала периода")
    public Date getStartDate() {return theStartDate;}
    public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
    /** Дата начала */
    private Date theStartDate ;

    /** Дата окончания периода */
    @Comment("Дата окончания периода")
    public Date getFinishDate() {return theFinishDate;}
    public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата окончания периода */
    private Date theFinishDate ;

    /** Тип заполнения */
    @Comment("Тип заполнения")
    @OneToOne
    public VocListEntryType getEntryType() {return theEntryType;}
    public void setEntryType(VocListEntryType aEntryType) {theEntryType = aEntryType;}
    /** Тип заполнения */
    private VocListEntryType theEntryType ;

    /** Код ЛПУ, создавшее заполнение */
    @Comment("Код ЛПУ, создавшее заполнение")
    public String getLpuOmcCode() {return theLpuOmcCode;}
    public void setLpuOmcCode(String aLpuOmcCode) {theLpuOmcCode = aLpuOmcCode;}
    /** Код ЛПУ, создавшее заполнение */
    private String theLpuOmcCode ;

    /** Дата создания */
    @Comment("Дата создания")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата создания */
    private Date theCreateDate ;

    /** Время создания */
    @Comment("Время создания")
    public Time getCreateTime() {return theCreateTime;}
    public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
    /** Время создания */
    private Time theCreateTime ;

    @PrePersist
    void onPrePersist() {
        Long currentTime = System.currentTimeMillis();
        theCreateDate=new java.sql.Date(currentTime);
        theCreateTime=new java.sql.Time(currentTime);
        theIsDeleted=false;
        theIsClosed=false;

    }

    /** Удаленная запись */
    @Comment("Удаленная запись")
    public Boolean getIsDeleted() {return theIsDeleted;}
    public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
    /** Удаленная запись */
    private Boolean theIsDeleted ;

}
