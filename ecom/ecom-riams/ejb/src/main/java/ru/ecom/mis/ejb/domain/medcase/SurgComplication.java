package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocComplication;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Comment("Осложнения при операции")
@Table(schema="SQLUser")
@Getter
@Setter
public class SurgComplication extends BaseEntity {
    /** Дата осложнения */
    private Date dateComp;

    @Comment("Осложнение")
    @OneToOne
    public VocComplication getComplication() {
        return complication;
    }

    /**Осложнение*/
    private VocComplication complication;

    /**Причина осложнения текст*/
    private String compReasonString;

    /**Осложнение текст*/
    private String complicationString;

    /** Хирургическая операция */
    @Comment("Хирургическая операция")
    @ManyToOne(fetch = FetchType.LAZY)
    public SurgicalOperation getSurgicalOperation() {return surgicalOperation;}
    private SurgicalOperation surgicalOperation;
}