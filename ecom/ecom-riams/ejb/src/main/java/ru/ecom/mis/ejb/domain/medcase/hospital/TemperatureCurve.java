package ru.ecom.mis.ejb.domain.medcase.hospital;

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
public class TemperatureCurve extends BaseEntity {

    /**
     * Время суток
     */
    @Comment("Время суток")
    @OneToOne
    public VocDayTime getDayTime() {
        return theDayTime;
    }

    public void setDayTime(VocDayTime aDayTime) {
        theDayTime = aDayTime;
    }

    /**
     * Время суток
     */
    private VocDayTime theDayTime;

    /**
     * Температурный градус
     */
    @Comment("Температурный градус")
    public BigDecimal getDegree() {
        return theDegree;
    }

    public void setDegree(BigDecimal aDegree) {
        theDegree = aDegree;
    }

    /**
     * Температурный градус
     */
    private BigDecimal theDegree;

    /**
     * Дата измерения температуры
     */
    @Comment("Дата измерения температуры")
    public Date getTakingDate() {
        return theTakingDate;
    }

    public void setTakingDate(Date aTakingDate) {
        theTakingDate = aTakingDate;
    }

    /**
     * Дата измерения температуры
     */
    private Date theTakingDate;

    /**
     * Случай медицинского обслуживания
     */
    @Comment("Случай медицинского обслуживания")
    @OneToOne
    public MedCase getMedCase() {
        return theMedCase;
    }

    public void setMedCase(MedCase aMedCase) {
        theMedCase = aMedCase;
    }

    /**
     * Случай медицинского обслуживания
     */
    private MedCase theMedCase;

    /**
     * День пребывания в стационаре
     */
    @Comment("День пребывания в стационаре")
    @Transient
    public Integer getHospDayNumber() {
        Long dateFinish = theTakingDate.getTime();
        Long dateStart = dateFinish;
        if (theMedCase instanceof HospitalMedCase) {
            dateStart = theMedCase.getDateStart().getTime();
        }
        if (theMedCase instanceof DepartmentMedCase) {
            dateStart = theMedCase.getParent().getDateStart().getTime();
        }

        final int msecinday = 1000 * 60 * 60 * 24;

        return Integer.valueOf((int) (1 + ((dateFinish - dateStart) / msecinday)));
    }

    /**
     * Время суток (текст)
     */
    @Comment("Время суток (текст)")
    @Transient
    public String getDayTimeText() {
        return theDayTime != null ? theDayTime.getName() : "";
    }

    /**
     * Дата создания
     */
    @Comment("Дата создания")
    public Date getDate() {
        return theDate;
    }

    public void setDate(Date aDate) {
        theDate = aDate;
    }

    /**
     * Дата создания
     */
    private Date theDate;

    /**
     * Время создания
     */
    @Comment("Время создания")
    public Time getTime() {
        return theTime;
    }

    public void setTime(Time aTime) {
        theTime = aTime;
    }

    /**
     * Время создания
     */
    private Time theTime;

    /**
     * Время редактирования
     */
    @Comment("Время редактирования")
    public Time getEditTime() {
        return theEditTime;
    }

    public void setEditTime(Time aEditTime) {
        theEditTime = aEditTime;
    }

    /**
     * Время редактирования
     */
    private Time theEditTime;

    /**
     * Дата редактирования
     */
    @Comment("Дата редактирования")
    public Date getEditDate() {
        return theEditDate;
    }

    public void setEditDate(Date aEditDate) {
        theEditDate = aEditDate;
    }

    /**
     * Дата редактирования
     */
    private Date theEditDate;

    /**
     * Пользователь последний, изменявший запись
     */
    @Comment("Пользователь последний, изменявший запись")
    public String getEditUsername() {
        return theEditUsername;
    }

    public void setEditUsername(String aEditUsername) {
        theEditUsername = aEditUsername;
    }

    /**
     * Пользователь последний, изменявший запись
     */
    private String theEditUsername;

    /**
     * Пользователь
     */
    @Comment("Пользователь")
    public String getUsername() {
        return theUsername;
    }

    public void setUsername(String aUsername) {
        theUsername = aUsername;
    }

    /**
     * Пользователь
     */
    private String theUsername;

}