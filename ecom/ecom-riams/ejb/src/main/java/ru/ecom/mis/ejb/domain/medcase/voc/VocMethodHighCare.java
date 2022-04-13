package ru.ecom.mis.ejb.domain.medcase.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Методы ВМП
 */
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocMethodHighCare extends VocBaseEntity {
    /**
     * Список диагнозов
     */
    private String diagnosis;
    /**
     * Код вида ВМП
     */
    private String kindHighCare;
    /**
     * Дата начала
     */
    private Date dateFrom;
    /**
     * Дата окончания
     */
    private Date dateTo;
    /**
     * Модель пациента
     */
    private String patientModel;
    /**
     * ИД модели пациента
     */
    private Long patientModelId;
    /**
     * Цена
     */
    private BigDecimal cost;
}
