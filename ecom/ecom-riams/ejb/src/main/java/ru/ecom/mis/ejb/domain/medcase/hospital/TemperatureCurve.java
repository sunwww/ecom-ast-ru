package ru.ecom.mis.ejb.domain.medcase.hospital;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDayTime;
import ru.ecom.mis.ejb.domain.medcase.voc.VocStoolType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

/**
 * Температурная кривая
 *
 * @author oegorova
 */

@Comment("Температурная кривая")
@Entity
@Table(schema = "SQLUser")
@Getter
@Setter
public class TemperatureCurve extends BaseEntity {

    /**
     * Время суток
     */
    @Comment("Время суток")
    @OneToOne
    public VocDayTime getDayTime() {
        return dayTime;
    }

    /**
     * Время суток
     */
    private VocDayTime dayTime;

    /**
     * Температурный градус
     */
    private BigDecimal degree;

    /**
     * Дата измерения температуры
     */
    private Date takingDate;

    /**
     * Случай медицинского обслуживания
     */
    @Comment("Случай медицинского обслуживания")
    @OneToOne
    public MedCase getMedCase() {
        return medCase;
    }

    /**
     * Случай медицинского обслуживания
     */
    private MedCase medCase;

    /**
     * День пребывания в стационаре
     */
    @Comment("День пребывания в стационаре")
    @Transient
    public Integer getHospDayNumber() {
        long dateFinish = takingDate.getTime();
        long dateStart = dateFinish;
        if (medCase instanceof HospitalMedCase) {
            dateStart = medCase.getDateStart().getTime();
        }
        if (medCase instanceof DepartmentMedCase) {
            dateStart = medCase.getParent().getDateStart().getTime();
        }

        final int msecinday = 1000 * 60 * 60 * 24;

        return (int) (1 + ((dateFinish - dateStart) / msecinday));
    }


    /**
     * День болезни
     */
    private Integer illnessDayNumber;


    /**
     * Время суток (текст)
     */
    @Comment("Время суток (текст)")
    @Transient
    public String getDayTimeText() {
        return dayTime != null ? dayTime.getName() : "";
    }

    /**
     * Дата создания
     */
    private Date date;


    /**
     * Время создания
     */
    private Time time;


    /**
     * Время редактирования
     */
    private Time editTime;


    /**
     * Дата редактирования
     */
    private Date editDate;


    /**
     * Пользователь последний, изменявший запись
     */
    private String editUsername;

    /**
     * Пользователь
     */
    private String username;
}