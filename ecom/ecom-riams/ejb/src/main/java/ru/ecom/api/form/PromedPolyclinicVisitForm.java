package ru.ecom.api.form;

import com.google.gwt.codegen.server.StringGenerator;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * Посещение (аналог ShortMedCase, Ticket)
 */
@Data
public class PromedPolyclinicVisitForm {

    /**
     * Первый визит в случае
     */
    //TODO возможно, выпилить
    private Boolean firstVisit;

    /**
     * Дата начала приема
     */
    private LocalDateTime startTime;

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
    private String workplaceCode; //ServiceType_id //TODO сделать enum

    /**
     * Текст дневника
     */
    private String diary;

    /**
     * Вид медицинской помощи (НСИ 1.2.643.5.1.13.2.1.1.655) специализированная, первичная МСП, доврачебная,..
     */
    private String medicalCareKindCode;

    private String serviceTypeCode;

    /**
     * Исход случая (возможно, перенести в ТАП)
     */
    private String ishodCode;

    /**
     * Уровень МЭС (1,2,3,5)
     */
    private String mesCode;

    /**
     * Источник финансирования. <b>Временно не используется, всегда будет заполняться ОМС (1)</b>
     */
    private String serviceStream;

    /**
     * Цель посещения (код). Пока берем код фонда ОМС (V025, 3.0 обращение по заболеванию, 3.1 - обр. с проф. целью, 1.0- посещение по заболеванию,...)
     */
    //TODO высчитывать на стороне медоса
    private String visitPurpose;//VizitType_id //dbo.VizitType



}
