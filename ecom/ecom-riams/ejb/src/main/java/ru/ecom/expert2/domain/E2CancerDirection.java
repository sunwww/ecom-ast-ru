package ru.ecom.expert2.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
/** Направление  на лечение (онкология) */
public class E2CancerDirection extends BaseEntity {

    /** ЛПУ, куда сделали направление */
    @Comment("ЛПУ, куда сделали направление")
    public String getDirectLpu() {return theDirectLpu;}
    public void setDirectLpu(String aDirectLpu) {theDirectLpu = aDirectLpu;}
    /** ЛПУ, куда сделали направление */
    private String theDirectLpu ;

    /** Случай рака */
    @Comment("Случай рака")
    @ManyToOne
    public E2CancerEntry getCancerEntry() {return theCancerEntry;}
    public void setCancerEntry(E2CancerEntry aCancerEntry) {theCancerEntry = aCancerEntry;}
    /** Случай рака */
    private E2CancerEntry theCancerEntry ;
    
    /** Дата направления */
    @Comment("Дата направления")
    public Date getDate() {return theDate;}
    public void setDate(Date aDate) {theDate = aDate;}
    /** Дата направления */
    private Date theDate ;
    
    /** Вид направление */
    @Comment("Вид направление")
    public String getType() {return theType;}
    public void setType(String aType) {theType = aType;}
    /** Вид направление */
    private String theType ;
    
    /** Метод диагностического исследования */
    @Comment("Метод диагностического исследования")
    public String getSurveyMethod() {return theSurveyMethod;}
    public void setSurveyMethod(String aSurveyMethod) {theSurveyMethod = aSurveyMethod;}
    /** Метод диагностического исследования */
    private String theSurveyMethod ;

    /** Медицинская услуга */
    @Comment("Медицинская услуга")
    public String getMedService() {return theMedService;}
    public void setMedService(String aMedService) {theMedService = aMedService;}
    /** Медицинская услуга */
    private String theMedService ;

    public E2CancerDirection() {}
    public E2CancerDirection (E2CancerEntry aCancerEntry) {theCancerEntry=aCancerEntry;}
    public E2CancerDirection(E2CancerDirection aDirection, E2CancerEntry aCancerEntry) {
        theCancerEntry=aCancerEntry;
        theDirectLpu=aDirection.theDirectLpu;
        theMedService=aDirection.theMedService;
        theDate = aDirection.theDate;
        theType=aDirection.theType;
        theSurveyMethod=aDirection.theSurveyMethod;
    }
    

}