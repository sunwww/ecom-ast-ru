package ru.ecom.mis.ejb.domain.userdocument;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.userdocument.voc.VocDynamicDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@Setter
public class DynamicDocument extends BaseEntity {
    /** Тип документа */
    @Comment("Тип документа")
    @OneToOne
    public VocDynamicDocument getType() {return type;}
    private VocDynamicDocument type ;

    /** Содержимое документа */
    @Comment("Содержимое документа")
    @Column(length= ColumnConstants.TEXT_MAXLENGHT)
    public String getContent() {return content;}
    private String content ;

    /** Случай мед. обслуживания */
    @Comment("Случай мед. обслуживания")
    @OneToOne
    public MedCase getMedCase() {return medCase;}
    private MedCase medCase ;

    /** Дата создания */
    private Date createDate;

    /** Пользователь, создавший запись */
    private String createUsername;

    /** Время создания */
    private Time createTime ;

    public DynamicDocument() {
        long date = System.currentTimeMillis();
        setCreateDate(new java.sql.Date(date));
        setCreateTime(new java.sql.Time(date));
    }

}