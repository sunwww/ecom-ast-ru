package ru.ecom.mis.ejb.domain.patient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.patient.voc.VocDispensaryEnd;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.sql.Time;

/**Карта диспансерного наблюдения*/
@Entity
@Getter
@Setter
public class DispensaryCard extends BaseEntity {
    /** Пациент */
    @Comment("Пациент")
    @OneToOne
    public Patient getPatient() {return patient;}
    /** Пациент */
    private Patient patient ;
    
    /** Дата постановки на Д учет */
    private Date startDate ;
    
    /** Дата снятия с учета */
    private Date finishDate ;
    
    /** Диагноз постановки на Д учет */
    @Comment("Диагноз постановки на Д учет")
    @OneToOne
    public VocIdc10 getDiagnosis() {return diagnosis;}
    /** Диагноз постановки на Д учет */
    private VocIdc10 diagnosis ;

    /** Рабочая функция врача установившего наблюдение */
    @Comment("Рабочая функция врача установившего наблюдение")
    @OneToOne
    public WorkFunction getWorkFunction() {return workFunction;}
    /** Рабочая функция врача установившего наблюдение */
    private WorkFunction workFunction ;

    /** Причина снятия с учета */
    @Comment("Причина снятия с учета")
    @OneToOne
    public VocDispensaryEnd getEndReason() {return endReason;}
    /** Причина снятия с учета */
    private VocDispensaryEnd endReason ;

    /** Дата создания */
    private Date createDate ;

    /** Время создания */
    private Time createTime ;

    /** Создатель */
    private String createUsername ;

    /** Редактировал */
    private String editUsername ;

    /** Дата редактирования */
    private Date editDate ;

    /** Время редактирования */
    private Time editTime ;

}
