package ru.ecom.expert2.domain.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV020;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV021;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

/**
 * Справочник профилей медицинской помощи
 */

@Entity
public class VocE2MedHelpProfile  extends VocBaseEntity{
    /** Дата начала действия */
    @Comment("Дата начала действия")
    public Date getStartDate() {return theStartDate;}
    public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
    /** Дата начала действия */
    private Date theStartDate ;

    /** Дата окончания действия */
    @Comment("Дата окончания действия")
    public Date getFinishDate() {return theFinishDate;}
    public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата окончания действия */
    private Date theFinishDate ;

    /** Архивная запись */
    @Comment("Архивная запись")
    public Boolean getNoActuality() {return theNoActuality;}
    public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}
    /** Архивная запись */
    private Boolean theNoActuality ;

    /** Профиль ФОМС */
    @Comment("Профиль ФОМС")
    public String getProfileK() {return theProfileK;}
    public void setProfileK(String aProfileK) {theProfileK = aProfileK;}
    /** Профиль ФОМС */
    private String theProfileK ;

    /** Мед. специальность V021 по профилю */
    @Comment("Мед. специальность V021 по профилю")
    @OneToOne
    public VocE2FondV021 getMedSpecV021() {return theMedSpecV021;}
    public void setMedSpecV021(VocE2FondV021 aMedSpecV021) {theMedSpecV021 = aMedSpecV021;}
    /** Мед. специальность по профилю */
    private VocE2FondV021 theMedSpecV021 ;

    /** Профиль койки V020 */
    @Comment("Профиль койки V020 ")
    @OneToOne
    @Deprecated
    public VocE2FondV020 getProfileBed() {return theProfileBed;}
    public void setProfileBed(VocE2FondV020 aProfileBed) {theProfileBed = aProfileBed;}
    /** Профиль койки V020 */
    private VocE2FondV020 theProfileBed ;

    /** Услуга по профилю для стационар по умолчанию */
    @Comment("Услуга по профилю для стационар по умолчанию")
    @OneToOne
    @Deprecated
    public VocMedService getDefaultStacMedService() {return theDefaultStacMedService;}
    public void setDefaultStacMedService(VocMedService aDefaultStacMedService) {theDefaultStacMedService = aDefaultStacMedService;}
    /** Услуга по профилю для стационар по умолчанию */
    private VocMedService theDefaultStacMedService ;
}
