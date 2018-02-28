package ru.ecom.mis.ejb.domain.pharmnetPharmacy;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by rkurbanov on 07.02.2018.
 */
@Comment("Внутренний документ")
@Entity
@Table(name = "internalDocument", schema="pharmnet")
public class InternalDocumentEntity extends BaseEntity {

    private Integer storageId;//откуда
    private Integer goodsleavePrevId;//откуда
    private Integer goodsleaveNextId;//откуда
    private Integer goodId; // что
    private Float amount; //кол-во ед
    private Integer workFunctionId;//кто
    private String username;//кто
    private Integer medcaseId;//медкейс
    private Date dateOperation;//когда
    private Time timeOperation;//когда
    private Integer typeDocument;//списание
    private Integer prevState;//прошлое состояние
    private Integer nextState;//след состояние
private Integer medserviceId;

    @Column(name = "storageId")
    public Integer getStorageId() {
        return storageId;
    }
    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    @Column(name = "goodsleavePrevId")
    public Integer getGoodsleaveId() {
        return goodsleavePrevId;
    }
    public void setGoodsleaveId(Integer goodsleavePrevId) {
        this.goodsleavePrevId = goodsleavePrevId;
    }

    @Column(name = "goodsleaveNextId")
    public Integer getGoodsleaveNextId() {
        return goodsleaveNextId;
    }
    public void setGoodsleaveNextId(Integer goodsleaveNextId) {
        this.goodsleaveNextId = goodsleaveNextId;
    }

    @Column(name = "goodId")
    public Integer getGoodId() {
        return goodId;
    }
    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    @Column(name = "amount")
    public Float getAmount() {
        return amount;
    }
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Column(name = "workFunctionId")
    public Integer getWorkFunctionId() {
        return workFunctionId;
    }
    public void setWorkFunctionId(Integer workFunctionId) {
        this.workFunctionId = workFunctionId;
    }

    @Column(name = "medcaseId")
    public Integer getMedcaseId() {
        return medcaseId;
    }
    public void setMedcaseId(Integer medcaseId) {
        this.medcaseId = medcaseId;
    }

    @Column(name = "dateOperation")
    public Date getDateOperation() {
        return dateOperation;
    }
    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    @Column(name = "timeOperation")
    public Time getTimeOperation() {
        return timeOperation;
    }
    public void setTimeOperation(Time timeOperation) {
        this.timeOperation = timeOperation;
    }

    @Column(name = "typeDocument")
    public Integer getTypeDocument() {
        return typeDocument;
    }
    public void setTypeDocument(Integer typeDocument) {
        this.typeDocument = typeDocument;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "prevState")
    public Integer getPrevState() {
        return prevState;
    }
    public void setPrevState(Integer prevState) {
        this.prevState = prevState;
    }

    @Column(name = "nextState")
    public Integer getNextState() {
        return nextState;
    }
    public void setNextState(Integer nextState) {
        this.nextState = nextState;
    }

    @Column(name = "medserviceId")
    public Integer getMedserviceId() {
        return medserviceId;
    }
    public void setMedserviceId(Integer medserviceId) {
        this.medserviceId = medserviceId;
    }
}
