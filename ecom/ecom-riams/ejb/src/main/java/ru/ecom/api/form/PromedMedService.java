package ru.ecom.api.form;

import lombok.Data;

/**
 * Медицинская услуга для её подачи в промед
 */
@Data
public class PromedMedService extends BasePromedForm {
    /**
     * ИД медкейса
     */
    private Long medserviceId;

    /**
     * Код мед. услуги (V001)
     */
    private String medserviceCode;

    /**
     * Врач, выполнивший услугу
     */
    private PromedDoctor doctor;
    /**
     * Дата и время начала оказания услуги "2021-09-13 10:17:00"
     */
    private String startTime;
    /**
     * Дата и время окончания оказания услуги
     */
    private String finishTime;
    /**
     * Количество оказанных услуг
     */
    private Integer amount;
}
