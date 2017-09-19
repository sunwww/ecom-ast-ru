package ru.ecom.mis.ejb.domain.pharmacy;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by rkurbanov on 05.09.2017.
 */
@Comment("Остатки")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public class PharmDrug extends BaseEntity  {

    private PharmStorage pharmStorage;
    private VocDrug drug;
    private Date bestBefore;
    private Float amount;
    private PharmDrug lastState;
    private PharmDrug nextState;

    @OneToOne
    public PharmStorage getPharmStorage() {
        return pharmStorage;
    }
    public void setPharmStorage(PharmStorage pharmStorage) {
        this.pharmStorage = pharmStorage;
    }

    @OneToOne
    public VocDrug getDrug() {
        return drug;
    }
    public void setDrug(VocDrug drug) {
        this.drug = drug;
    }

    public Float getAmount() {
        return amount;
    }
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getBestBefore() {
        return bestBefore;
    }
    public void setBestBefore(Date bestBefore) {
        this.bestBefore = bestBefore;
    }

    @Comment("Предыдущее состояние")
    @ManyToOne
    public PharmDrug getLastState() {
        return lastState;
    }
    public void setLastState(PharmDrug lastState) {
        this.lastState = lastState;
    }

    @Comment("Следующее состояние")
    @ManyToOne
    public PharmDrug getNextState() {
        return nextState;
    }
    public void setNextState(PharmDrug nextState) {
        this.nextState = nextState;
    }
}
