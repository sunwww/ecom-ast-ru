package ru.ecom.mis.ejb.domain.pharmacy;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Created by rkurbanov on 14.09.2017.
 */
@Comment("Номенклатр ЛС")
@Entity
@Table(schema="SQLUser")
public class PharmDrugNomenklatr extends BaseEntity {

    private VocDrug vocDrug;
    private String party;
    private String serial;
    private Date bestBefore;
    private double amount;
    private Boolean ended;
    private PharmOperation incomeOperation;
    private PharmStorage storage;
    //private VocVender vender;


    @OneToOne
    public VocDrug getVocDrug() {
        return vocDrug;
    }
    public void setVocDrug(VocDrug vocDrug) {
        this.vocDrug = vocDrug;
    }

    public String getParty() {
        return party;
    }
    public void setParty(String party) {
        this.party = party;
    }

    public String getSerial() {
        return serial;
    }
    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Date getBestBefore() {
        return bestBefore;
    }
    public void setBestBefore(Date bestBefore) {
        this.bestBefore = bestBefore;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Boolean getEnded() {
        return ended;
    }
    public void setEnded(Boolean ended) {
        this.ended = ended;
    }

    @OneToOne
    public PharmOperation getIncomeOperation() {
        return incomeOperation;
    }
    public void setIncomeOperation(PharmOperation incomeOperation) {
        this.incomeOperation = incomeOperation;
    }

    @OneToOne
    public PharmStorage getStorage() {
        return storage;
    }
    public void setStorage(PharmStorage storage) {
        this.storage = storage;
    }
}
