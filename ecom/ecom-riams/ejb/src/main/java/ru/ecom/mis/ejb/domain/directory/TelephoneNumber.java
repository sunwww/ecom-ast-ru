package ru.ecom.mis.ejb.domain.directory;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.directory.voc.VocTypeNumber;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/** Номер телефона */
@Comment("Номер телефона")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class TelephoneNumber extends BaseEntity{
    
    /** номер телефона */
    private String telNumber;
    
    /** Ссылка на запись */
    @Comment("Ссылка на запись")
    @ManyToOne
    public Entry getEntry() {return entry;}
    private Entry entry;
    
    /** Тип номера */
    @Comment("Тип номера")
    @OneToOne
    public VocTypeNumber getTypeNumber() {return typeNumber;}
    private VocTypeNumber typeNumber;
}