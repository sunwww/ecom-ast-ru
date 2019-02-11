package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.expert2.domain.voc.VocListEntryType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Список записей (заполнение
 */
@NamedQueries({
        @NamedQuery( name="E2ListEntry.findAllEntries"
                , query="from E2Entry where listEntry=:list and (isDeleted is null or isDeleted='0') and (doNotSend is null or doNotSend='0')")
})
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

    /** ИД монитора процесса проверки */
    @Comment("ИД монитора процесса проверки")
    public Long getMonitorId() {return theMonitorId;}
    public void setMonitorId(Long aMonitorId) {theMonitorId = aMonitorId;}
    /** ИД монитора процесса проверки */
    private Long theMonitorId ;
    /** Черновик */
    @Comment("Черновик")
    public Boolean getIsDraft() {return theIsDraft;}
    public void setIsDraft(Boolean aIsDraft) {theIsDraft = aIsDraft;}
    /** Черновик */
    private Boolean theIsDraft ;

    /** Закрыто для редакторирования */
    @Comment("Закрыто для редакторирования")
    public Boolean getIsClosed() {return theIsClosed;}
    public void setIsClosed(Boolean aIsClosed) {theIsClosed = aIsClosed;}
    /** Закрыто для редакторирования */
    private Boolean theIsClosed =false;

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
    }

    /** Удаленная запись */
    @Comment("Удаленная запись")
    public Boolean getIsDeleted() {return theIsDeleted;}
    public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
    /** Удаленная запись */
    private Boolean theIsDeleted = false;

    /** Список записей по заполнению */
    @Comment("Список записей по заполнению")
    @OneToMany(mappedBy = "listEntry",cascade = CascadeType.REMOVE)
    public List<E2Entry> getEntryList() {return theEntryList;}
    public void setEntryList(List<E2Entry> aEntryList) {theEntryList = aEntryList;}
    /** Список записей по заполнению */
    private List<E2Entry> theEntryList ;

    /** Дата последней проверки */
    @Comment("Дата последней проверки")
    public Date getCheckDate() {return theCheckDate;}
    public void setCheckDate(Date aCheckDate) {theCheckDate = aCheckDate;}
    /** Дата последней проверки */
    private Date theCheckDate ;

    /** Время последней проверки */
    @Comment("Время последней проверки")
    public Time getCheckTime() {return theCheckTime;}
    public void setCheckTime(Time aCheckTime) {theCheckTime = aCheckTime;}
    /** Время последней проверки */
    private Time theCheckTime ;




}
