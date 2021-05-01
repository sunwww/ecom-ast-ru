package ru.ecom.mis.ejb.domain.workcalendar;/**
 * Created by Milamesher on 31.10.2019.
 */

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class PlanOphtHospital extends WorkCalendarHospitalBed {
    /** Дата ОКТ */
    private Date dateOKT;

    /** Глаз, в который будут вводить ингибиторы ангиогенеза */
    @Comment("Глаз, в который будут вводить ингибиторы ангиогенеза")
    @OneToOne
    public VocEye getEye() {return eye;}
    private VocEye eye;
}