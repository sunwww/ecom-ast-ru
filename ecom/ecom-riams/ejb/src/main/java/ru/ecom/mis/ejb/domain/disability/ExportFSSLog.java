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

    private String result ;
    private String responseCode ;
    private String status ;
    private Long disabilityDocument ;
    private String disabilityNumber ;
    private String requestCode ;
    private Date requestDate ;
    private Time requestTime ;
    private String requestType ;
    private String request_id;

    @Comment("Результат")
    @Column(length= ColumnConstants.TEXT_MAXLENGHT)
    public String getResult() {return result;}
    public void setResult(String aResult) {result = aResult;}

    @Comment("Текст ответа")
    @Column(length= ColumnConstants.TEXT_MAXLENGHT)
    public String getResponseCode() {return responseCode;}
    public void setResponseCode(String aResponseCode) {responseCode = aResponseCode;}

    @Comment("Статус")
    public String getStatus() {return status;}
    public void setStatus(String aStatus) {status = aStatus;}

    @Comment("ИД больничного листа")
    public Long getDisabilityDocument() {return disabilityDocument;}
    public void setDisabilityDocument(Long aDisabilityDocument) {disabilityDocument = aDisabilityDocument;}

    @Comment("Номер больничного листа")
    public String getDisabilityNumber() {return disabilityNumber;}
    public void setDisabilityNumber(String aDisabilityNumber) {disabilityNumber = aDisabilityNumber;}

    @Comment("Код запроса")
    @Column(length= ColumnConstants.TEXT_MAXLENGHT)
    public String getRequestCode() {return requestCode;}
    public void setRequestCode(String aRequestCode) {requestCode = aRequestCode;}

    @Comment("Дата запроса")
    public Date getRequestDate() {return requestDate;}
    public void setRequestDate(Date aRequestDate) {requestDate = aRequestDate;}

    @Comment("Время запроса")
    public Time getRequestTime() {return requestTime;}
    public void setRequestTime(Time aRequestTime) {requestTime = aRequestTime;}

    @Comment("Тип запроса")
    public String getRequestType() {return requestType;}
    public void setRequestType(String aRequestType) {requestType = aRequestType;}

    @Comment("Присвоенный id запроса")
    public String getRequest_id() {
        return request_id;
    }
    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
