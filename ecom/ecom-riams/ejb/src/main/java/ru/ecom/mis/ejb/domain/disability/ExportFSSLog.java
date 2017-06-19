package ru.ecom.mis.ejb.domain.disability;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by vtsybulin on 16.06.2017.
 */
@Entity
@AIndexes({
        @AIndex(unique = false, properties = {"disabilityNumber"})
        ,@AIndex(unique = false, properties = {"disabilityDocument"})
})
public class ExportFSSLog extends BaseEntity {
    /** Результат */
    @Comment("Результат")
    @Column(length= ColumnConstants.TEXT_MAXLENGHT)
    public String getResult() {return theResult;}
    public void setResult(String aResult) {theResult = aResult;}
    /** Результат */
    private String theResult ;

    /** Текст ответа */
    @Comment("Текст ответа")
    @Column(length= ColumnConstants.TEXT_MAXLENGHT)
    public String getResponseCode() {return theResponseCode;}
    public void setResponseCode(String aResponseCode) {theResponseCode = aResponseCode;}
    /** Текст ответа */
    private String theResponseCode ;

    /** Статус */
    @Comment("Статус")
    public String getStatus() {return theStatus;}
    public void setStatus(String aStatus) {theStatus = aStatus;}
    /** Статус */
    private String theStatus ;

    /** ИД больничного листа */
    @Comment("ИД больничного листа")
    public Long getDisabilityDocument() {return theDisabilityDocument;}
    public void setDisabilityDocument(Long aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}
    /** ИД больничного листа */
    private Long theDisabilityDocument ;

    /** Номер больничного листа */
    @Comment("Номер больничного листа")
    public String getDisabilityNumber() {return theDisabilityNumber;}
    public void setDisabilityNumber(String aDisabilityNumber) {theDisabilityNumber = aDisabilityNumber;}
    /** Номер больничного листа */
    private String theDisabilityNumber ;

    /** Код запроса */
    @Comment("Код запроса")
    @Column(length= ColumnConstants.TEXT_MAXLENGHT)
    public String getRequestCode() {return theRequestCode;}
    public void setRequestCode(String aRequestCode) {theRequestCode = aRequestCode;}
    /** Код запроса */
    private String theRequestCode ;
    /** Дата запроса */
    @Comment("Дата запроса")
    public Date getRequestDate() {return theRequestDate;}
    public void setRequestDate(Date aRequestDate) {theRequestDate = aRequestDate;}
    /** Дата запроса */
    private Date theRequestDate ;

    /** Время запроса */
    @Comment("Время запроса")
    public Time getRequestTime() {return theRequestTime;}
    public void setRequestTime(Time aRequestTime) {theRequestTime = aRequestTime;}
    /** Время запроса */
    private Time theRequestTime ;
    
    /** Тип запроса */
    @Comment("Тип запроса")
    public String getRequestType() {return theRequestType;}
    public void setRequestType(String aRequestType) {theRequestType = aRequestType;}
    /** Тип запроса */
    private String theRequestType ;
}
