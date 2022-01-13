package ru.ecom.mis.ejb.domain.prescription;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV032;
import ru.ecom.mis.ejb.domain.pharmacy.PharmDrug;
import ru.ecom.mis.ejb.domain.pharmacy.VocDrug;
import ru.ecom.mis.ejb.domain.prescription.voc.VocDrugAmountUnit;
import ru.ecom.mis.ejb.domain.prescription.voc.VocFrequencyUnit;
import ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptOrderType;
import ru.ecom.mis.ejb.uc.privilege.domain.voc.VocDrugMethod;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Назначение на лекарства
 *
 * @author azviagin, rkurbanov
 */

@Comment("Назначение на лекарства")
@Entity
@Getter
@Setter
public class DrugPrescription extends Prescription {

    //unused временно не используется.
    private PharmDrug drug;
    private VocDrugMethod method;
    private VocFrequencyUnit frequencyUnit;
    private Integer frequency;
    private VocPrescriptOrderType orderType;
    private Integer orderTime;
    private VocDrugAmountUnit amountUnit;
    private Float amount;
    private VocDrug vocDrug;
    private VocE2FondV032 drugCovidSchema;

    @ManyToOne
    @Comment("Схема лечения при назначении лекарств covid-19")
    public VocE2FondV032 getDrugCovidSchema() {
        return drugCovidSchema;
    }

    @Comment("Лекарство")
    @OneToOne
    public PharmDrug getDrug() {
        return drug;
    }

    @Comment("Метод введения")
    @OneToOne
    public VocDrugMethod getMethod() {
        return method;
    }

    @Comment("Единица частоты использования")
    @OneToOne
    public VocFrequencyUnit getFrequencyUnit() {
        return frequencyUnit;
    }


    @Comment("Тип порядка использования")
    @OneToOne
    public VocPrescriptOrderType getOrderType() {
        return orderType;
    }


    @Comment("Единица измерения количества")
    @OneToOne
    public VocDrugAmountUnit getAmountUnit() {
        return amountUnit;
    }

    /**
     * Наименование лекарственного препарата
     */
    @Comment("Наименование лекарственного препарата")
    @Transient
    public String getDrugInfo() {
        return drug != null ? drug.getDrug().getName() : "";
    }

    /**
     * Описание лекарственного назначения
     */
    @Comment("Описание лекарственного назначения")
    @Transient
    public String getDescriptionInfo() {
        StringBuilder sb = new StringBuilder();
        if (getDrug() != null) {
            sb.append(getDrug().getDrug().getName())
                    .append(",").append(getAmount());
        }
        if (getAmountUnit() != null) {
            sb.append(" ")
                    .append(getAmountUnit().getName());
        }
        sb.append(",").append(getFrequency());
        if (getFrequencyUnit() != null) {
            sb.append(" раза в ")
                    .append(getFrequencyUnit().getName());
        }

        if (getMethod() != null) {
            sb.append(",")
                    .append(getMethod().getName());
        }
        if (getOrderTime() != null && (getOrderTime() != 0)) {
            sb.append(",")
                    .append(getOrderTime())
                    .append(" ")
                    .append("мин.");
        }
        if (getOrderType() != null) {
            sb.append(" ")
                    .append(getOrderType().getName());
        }
        return sb.toString();
    }

    /**
     * Лекарство
     */
    @Comment("Лекарство")
    @OneToOne
    public VocDrug getVocDrug() {
        return vocDrug;
    }
}