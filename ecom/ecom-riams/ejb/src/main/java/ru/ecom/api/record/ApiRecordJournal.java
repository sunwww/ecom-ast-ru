package ru.ecom.api.record;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@Setter
/**Журнал записанных пациентов */
public class ApiRecordJournal extends BaseEntity {
    /** Дата и время создания */
    private Date createDate ;

    /** Запись */
    @Comment("Запись")
    @Column(length=10000)
    public String getRecord() {return record;}
    /** Запись */
    private String record ;

    public ApiRecordJournal(){setCreateDate(new Date());}
    public ApiRecordJournal(String aRecord){setCreateDate(new Date());setRecord(aRecord);}

}