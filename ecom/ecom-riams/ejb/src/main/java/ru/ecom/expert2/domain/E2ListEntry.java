package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.expert2.domain.voc.VocListEntryType;

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
@Getter
@Setter
public class E2ListEntry extends BaseEntity {
    public E2ListEntry(){}
    public E2ListEntry(E2ListEntry aListEntry, String aNewName) {
        name =(aNewName!=null?aNewName:"КОПИЯ_"+aListEntry.getName());
        startDate =aListEntry.getStartDate();
        finishDate =aListEntry.getFinishDate();
        entryType =aListEntry.getEntryType();
        lpuOmcCode =aListEntry.lpuOmcCode;

    }

    /** ИД монитора процесса проверки */
    private Long monitorId;
    /** Черновик */
    private Boolean isDraft;

    /** Закрыто для редакторирования */
    private Boolean isClosed =false;

    /** Имя заполнения */
    private String name;

    /** Дата начала периода */
    private Date startDate;

    /** Дата окончания периода */
    private Date finishDate;

    /** Тип заполнения */
    @OneToOne
    public VocListEntryType getEntryType() {return entryType;}
    private VocListEntryType entryType;

    /** Код ЛПУ, создавшее заполнение */
    private String lpuOmcCode;

    /** Дата создания */
    private Date createDate;

    /** Время создания */
    private Time createTime;

    @PrePersist
    void onPrePersist() {
        Long currentTime = System.currentTimeMillis();
        createDate =new java.sql.Date(currentTime);
        createTime =new java.sql.Time(currentTime);
    }

    /** Удаленная запись */
    private Boolean isDeleted = false;

    /** Список записей по заполнению */
    @OneToMany(mappedBy = "listEntry",cascade = CascadeType.REMOVE)
    public List<E2Entry> getEntryList() {return entryList;}
    private List<E2Entry> entryList;

    /** Дата последней проверки */
    private Date checkDate;

    /** Время последней проверки */
    private Time checkTime;




}
