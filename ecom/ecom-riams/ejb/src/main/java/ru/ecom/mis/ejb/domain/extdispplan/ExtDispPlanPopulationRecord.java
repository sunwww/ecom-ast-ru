package ru.ecom.mis.ejb.domain.extdispplan;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity
@UnDeletable
@Getter
@Setter
public class ExtDispPlanPopulationRecord extends BaseEntity {

    /** План диспансеризации */
    @Comment("План диспансеризации")
    @OneToOne
    public ExtDispPlanPopulation getPlan() {return plan;}
    /** План диспансеризации */
    private ExtDispPlanPopulation plan ;

    /** Дата планируемой диспансеризации */
    private Date planDispDate ;

    /** Пациент */
    @Comment("Пациент")
    @OneToOne
    public Patient getPatient() {return patient;}
    /** Пациент */
    private Patient patient ;

    /** Удалено */
    private Boolean isDeleted ;

    /** Профосмотр пройден */
    private Boolean isExtDispMade ;

    @PrePersist
    void prePersist() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format2 = new SimpleDateFormat("MMdd");
        try {
            planDispDate = new java.sql.Date(format.parse(plan.getYear()+""+format2.format(patient.getBirthday())).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        isExtDispMade=false;isDeleted=false;
        //TODO дата планируемой ДД = дата рождения пациента + год плана ДД
    }

}
