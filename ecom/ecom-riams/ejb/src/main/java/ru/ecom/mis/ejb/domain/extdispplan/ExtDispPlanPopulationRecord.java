package ru.ecom.mis.ejb.domain.extdispplan;

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
public class ExtDispPlanPopulationRecord extends BaseEntity {

    /** План диспансеризации */
    @Comment("План диспансеризации")
    @OneToOne
    public ExtDispPlanPopulation getPlan() {return thePlan;}
    public void setPlan(ExtDispPlanPopulation aPlan) {thePlan = aPlan;}
    /** План диспансеризации */
    private ExtDispPlanPopulation thePlan ;

    /** Дата планируемой диспансеризации */
    @Comment("Дата планируемой диспансеризации")
    public Date getPlanDispDate() {return thePlanDispDate;}
    public void setPlanDispDate(Date aPlanDispDate) {thePlanDispDate = aPlanDispDate;}
    /** Дата планируемой диспансеризации */
    private Date thePlanDispDate ;

    /** Пациент */
    @Comment("Пациент")
    @OneToOne
    public Patient getPatient() {return thePatient;}
    public void setPatient(Patient aPatient) {thePatient = aPatient;}
    /** Пациент */
    private Patient thePatient ;

    /** Удалено */
    @Comment("Удалено")
    public Boolean getIsDeleted() {return theIsDeleted;}
    public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
    /** Удалено */
    private Boolean theIsDeleted ;

    /** Профосмотр пройден */
    @Comment("Профосмотр пройден")
    public Boolean getIsExtDispMade() {return theIsExtDispMade;}
    public void setIsExtDispMade(Boolean aIsExtDispMade) {theIsExtDispMade = aIsExtDispMade;}
    /** Профосмотр пройден */
    private Boolean theIsExtDispMade ;

    @PrePersist
    void prePersist() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format2 = new SimpleDateFormat("MMdd");
        try {
            thePlanDispDate = new java.sql.Date(format.parse(thePlan.getYear()+""+format2.format(thePatient.getBirthday())).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        theIsExtDispMade=false;theIsDeleted=false;
        //TODO дата планируемой ДД = дата рождения пациента + год плана ДД
    }

}
