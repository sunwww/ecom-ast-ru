package ru.ecom.mis.ejb.domain.pharmacy;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by rkurbanov on 05.09.2017.
 */

@Comment("Приходная операция")
@Entity
public class PharmIncomeOperation extends PharmOperation {

    private Integer countPack;
    private VocDrug vocDrug;

    @Comment("Количество упаковок")
    public Integer getCountPack() {
        return countPack;
    }
    public void setCountPack(Integer countPack) {
        this.countPack = countPack;
    }

    @Comment("Лек средство")
    @OneToOne
    public VocDrug getVocDrug() {
        return vocDrug;
    }
    public void setVocDrug(VocDrug vocDrug) {
        this.vocDrug = vocDrug;
    }
}
