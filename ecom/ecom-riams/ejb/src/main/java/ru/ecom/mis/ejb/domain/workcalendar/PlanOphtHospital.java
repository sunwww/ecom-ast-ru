package ru.ecom.mis.ejb.domain.workcalendar;/**
 * Created by Milamesher on 31.10.2019.
 */

import ru.ecom.mis.ejb.domain.medcase.voc.VocEye;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

/**
 * Таблица учёта пациентов, нуждающихся во введении ингибиторов ангиогенеза
 * на основе предварительной госпитализации
 * @author Milamesher
 *
 */

@Entity
public class PlanOphtHospital extends WorkCalendarHospitalBed {
    /** Дата ОКТ */
    @Comment("Дата ОКТ")
    public Date getDateOKT() {return theDateOKT;}
    public void setDateOKT(Date aDateOKT) {theDateOKT = aDateOKT;}
    /** Дата ОКТ */
    private Date theDateOKT;

    /** Глаз, в который будут вводить ингибиторы ангиогенеза */
    @Comment("Глаз, в который будут вводить ингибиторы ангиогенеза")
    @OneToOne
    public VocEye getEye() {return theEye;}
    public void setEye(VocEye aEye) {theEye = aEye;}
    /** Глаз, в который будут вводить ингибиторы ангиогенеза */
    private VocEye theEye;
}