package ru.ecom.mis.ejb.domain.pharmacy;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.prescription.Prescription;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by rkurbanov on 05.09.2017.
 */
@Comment("Операции")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public class PharmOperation extends BaseEntity {

    private PharmDrug pharmDrug;
    private Float amount;
    private WorkFunction workFunction;
    private Prescription prescript;
    private Long prescription;
    private PharmStorage pharmStorage;
    private Date dateOperation;
    private Time timeOperation;


    @OneToOne
    public Prescription getPrescript() {
        return prescript;
    }
    public void setPrescript(Prescription prescript) {
        this.prescript = prescript;
    }

    @OneToOne
    public PharmDrug getPharmDrug() {
        return pharmDrug;
    }
    public void setPharmDrug(PharmDrug pharmDrug) {
        this.pharmDrug = pharmDrug;
    }

    public Float getAmount() {
        return amount;
    }
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @OneToOne
    public WorkFunction getWorkFunction() {
        return workFunction;
    }
    public void setWorkFunction(WorkFunction workFunction) {
        this.workFunction = workFunction;
    }

    public Long getPrescription() {
        return prescription;
    }
    public void setPrescription(Long prescription) {
        this.prescription = prescription;
    }

    @OneToOne
    public PharmStorage getPharmStorage() {
        return pharmStorage;
    }
    public void setPharmStorage(PharmStorage pharmStorage) {
        this.pharmStorage = pharmStorage;
    }

    public Date getDateOperation() {
        return dateOperation;
    }
    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public Time getTimeOperation() {
        return timeOperation;
    }
    public void setTimeOperation(Time timeOperation) {
        this.timeOperation = timeOperation;
    }
}
