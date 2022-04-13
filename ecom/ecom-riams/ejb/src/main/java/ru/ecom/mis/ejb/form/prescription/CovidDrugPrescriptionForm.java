package ru.ecom.mis.ejb.form.prescription;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.DrugPrescription;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

import javax.persistence.Transient;

/**
 * Назначение на лекарства
 *
 * @author oegorova, rkurbanov
 */

@EntityForm
@EntityFormPersistance(clazz = DrugPrescription.class)
@Comment("Назначение лекарства")
@WebTrail(comment = "Назначение лекарства", nameProperties = "drug", list = "entityParentList-pres_covidDrugShortPrescription.do", view = "entityParentView-pres_covidDrugShortPrescription.do")
@Parent(property = "prescriptionList", parentForm = AbstractPrescriptionListForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/DrugPrescription")
@Setter
public class CovidDrugPrescriptionForm extends DrugPrescriptionForm {

    private Long vocDrug;
    private Long method;
    private Long frequencyUnit;
    private String frequency;
    private Long orderType;
    private String orderTime;
    private Long amountUnit;
    private String drugInfo;
    private Float amount;
    private String descriptionInfo;
    private String planEndDate;
    private String planEndTime;
    private Long drugCovidSchema;
    private PrescriptionFulfilmentForm fulfilmentForm = new PrescriptionFulfilmentForm();

    @Persist
    @Required
    @Comment("Схема лечения при назначении лекарств covid-19")
    public Long getDrugCovidSchema() {
        return drugCovidSchema;
    }

    /**
     * Плановая дата окончания
     */
    @Comment("Плановая дата окончания")
    @Persist
    @DateString
    @DoDateString
    public String getPlanEndDate() {
        return planEndDate;
    }

    /**
     * Плановое время окончания
     */
    @Comment("Плановое время окончания")
    @Persist
    @TimeString
    @DoTimeString
    public String getPlanEndTime() {
        return planEndTime;
    }

    @Comment("Метод введения")
    @Persist
    @Required
    public Long getMethod() {
        return method;
    }

    @Comment("Единица частоты использования")
    @Persist
    public Long getFrequencyUnit() {
        return frequencyUnit;
    }

    @Comment("Частота использования")
    @Persist
    @Required
    public String getFrequency() {
        return frequency;
    }

    @Comment("Тип порядка использования")
    @Persist
    public Long getOrderType() {
        return orderType;
    }

    @Comment("Время порядка использования")
    @Persist
    public String getOrderTime() {
        return orderTime;
    }

    @Comment("Единица измерения количества")
    @Persist
    @Required
    public Long getAmountUnit() {
        return amountUnit;
    }

    @Comment("Количество")
    @Persist
    @Required
    public Float getAmount() {
        return amount;
    }

    @Comment("Наименование лекарственного препарата")
    @Persist
    public String getDrugInfo() {
        return drugInfo;
    }

    @Comment("Описание лекарственного назначения")
    @Transient
    public String getDescriptionInfo() {
        return descriptionInfo;
    }

    /**
     * Лекарство
     */
    @Comment("Лекарство")
    @Persist
    @Required
    public Long getVocDrug() {
        return vocDrug;
    }

    /**
     * форма выполнения
     */
    @Comment("форма выполнения")
    public PrescriptionFulfilmentForm getFulfilmentForm() {
        return fulfilmentForm;
    }


}
