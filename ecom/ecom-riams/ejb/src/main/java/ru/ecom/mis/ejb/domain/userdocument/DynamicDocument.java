package ru.ecom.mis.ejb.domain.userdocument;

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
public class DynamicDocument extends BaseEntity {
    /** Тип документа */
    @Comment("Тип документа")
    @OneToOne
    public VocDynamicDocument getType() {return theType;}
    public void setType(VocDynamicDocument aType) {theType = aType;}
    private VocDynamicDocument theType ;

    /** Содержимое документа */
    @Comment("Содержимое документа")
    @Column(length= ColumnConstants.TEXT_MAXLENGHT)
    public String getContent() {return theContent;}
    public void setContent(String aContent) {theContent = aContent;}
    private String theContent ;

    /** Случай мед. обслуживания */
    @Comment("Случай мед. обслуживания")
    @OneToOne
    public MedCase getMedCase() {return theMedCase;}
    public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
    private MedCase theMedCase ;

    /** Дата создания */
    @Comment("Дата создания")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
    private Date theCreateDate;

    /** Пользователь, создавший запись */
    @Comment("Пользователь, создавший запись")
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    private String theCreateUsername;

    /** Время создания */
    @Comment("Время создания")
    public Time getCreateTime() {return theCreateTime;}
    public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
    private Time theCreateTime ;

    public DynamicDocument() {
        long date = System.currentTimeMillis();
        setCreateDate(new java.sql.Date(date));
        setCreateTime(new java.sql.Time(date));
    }

}