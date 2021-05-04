package ru.ecom.mis.ejb.domain.patient;/**
 * Created by Milamesher on 11.07.2019.
 */

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.voc.VocObservationResult;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

/** Лист наблюдения пациента выездной
 * анестезиолого-реанимационной неонатальной
 * бригады ОПЦ*/
@Entity
@Table(schema="SQLUser")
@AIndexes({
        @AIndex(properties="patient"),
        @AIndex(properties="startDate")
})
@Getter
@Setter
public class ObservationSheet extends BaseEntity {
    /** Дата установки */
    private Date startDate ;

    /** Дата снятия */
    private Date finishDate ;

    /** Пользователь, который последний редактировал запись */
    private String editUsername;

    /** Пользователь, который создал запись */
    private String createUsername;

    /** Специалист, открывший ЛН */
    @Comment("Специалист, открывший ЛН")
    @OneToOne
    public WorkFunction getSpecialistStart() {return specialistStart;}
    /** Специалист, открывший ЛН */
    private WorkFunction specialistStart;

    /** Специалист, закрывший ЛН */
    @Comment("Специалист, закрывший ЛН")
    @OneToOne
    public WorkFunction getSpecialistFin() {return specialistFin;}
    /** Специалист, закрывший ЛН */
    private WorkFunction specialistFin;

    /** Результат наблюдения */
    @Comment("Результат наблюдения")
    @OneToOne
    public VocObservationResult getObservResult() {return observResult;}
    /** Результат наблюдения */
    private VocObservationResult observResult;

    /** Пациент */
    @Comment("Пациент")
    @ManyToOne
    public Patient getPatient() {return patient;}
    /**Пациент */
    private Patient patient;
}