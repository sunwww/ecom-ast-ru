package ru.ecom.api.form;

import lombok.Builder;
import lombok.Data;

import java.util.List;


/**
 * Посещение (аналог ShortMedCase, Ticket)
 */
@Data
@Builder
public class PromedPolyclinicVisitForm extends BasePromedForm {

    /**
     * Дата начала приема
     */
    private String startTime;

    /**
     * ИД случая во внутренней системе
     */
    private String internalId;

    /**
     * Основной диагноз случая
     */
    private PromedDiagnosis diagnosis;

    /**
     * Врач, осуществивший прием
     */
    private PromedDoctor doctor;
    /**
     * Место обслуживания (промед dbo.ServiceType) 1 полка, 2 на дому, 3 на дому актив,5 на дому НМП, 6 пол-ка НМП, 7 моб бригада
     */
    private String workPlaceCode; //ServiceType_id //TODO сделать enum

    /**
     * Текст дневника
     */
    private String diary;
    /**
     * Состояние больного
     */
    private String illnessState;
    /**
     * Вид медицинской помощи (НСИ 1.2.643.5.1.13.2.1.1.655) специализированная, первичная МСП, доврачебная,..
     */
    private String medicalCareKindCode;

    /**
     * Исход случая (возможно, перенести в ТАП)
     */
    private String ishodCode;

    /**
     * Уровень МЭС (1,2,3,5)
     */
//    private String mesCode;

    /**
     * Источник финансирования. <b>Временно не используется, всегда будет заполняться ОМС (1)</b>
     */
    private String serviceStream;

    /**
     * Цель посещения (код). Пока берем код фонда ОМС (V025, 3.0 обращение по заболеванию, 3.1 - обр. с проф. целью, 1.0- посещение по заболеванию,...)
     */
    //TODO высчитывать на стороне медоса
    private String visitPurpose;//VizitType_id //dbo.VizitType
    private String promedCode; //ИД визита в промеде

    /**
     * Список мед. услуг в рамках визита (не СПО)
     */
    private List<PromedMedService> services;


}
