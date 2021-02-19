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
public class VocE2MedHelpProfile extends VocBaseEntity {
    private Date theStartDate;
    private Date theFinishDate;
    private Boolean theNoActuality;
    private String theProfileK;
    private VocE2FondV021 theMedSpecV021;
    private VocE2FondV020 theProfileBed;
    private VocMedService theDefaultStacMedService;
    private String theDefaultDepartmentCode;

    @Comment("Дата начала действия")
    public Date getStartDate() {
        return theStartDate;
    }

    public void setStartDate(Date aStartDate) {
        theStartDate = aStartDate;
    }

    @Comment("Дата окончания действия")
    public Date getFinishDate() {
        return theFinishDate;
    }

    public void setFinishDate(Date aFinishDate) {
        theFinishDate = aFinishDate;
    }

    @Comment("Архивная запись")
    public Boolean getNoActuality() {
        return theNoActuality;
    }

    public void setNoActuality(Boolean aNoActuality) {
        theNoActuality = aNoActuality;
    }

    @Comment("Профиль ФОМС")
    public String getProfileK() {
        return theProfileK;
    }

    public void setProfileK(String aProfileK) {
        theProfileK = aProfileK;
    }

    @Comment("Мед. специальность V021 по профилю")
    @OneToOne
    public VocE2FondV021 getMedSpecV021() {
        return theMedSpecV021;
    }

    public void setMedSpecV021(VocE2FondV021 aMedSpecV021) {
        theMedSpecV021 = aMedSpecV021;
    }

    @Comment("Профиль койки V020 ")
    @OneToOne
    @Deprecated
    public VocE2FondV020 getProfileBed() {
        return theProfileBed;
    }

    public void setProfileBed(VocE2FondV020 aProfileBed) {
        theProfileBed = aProfileBed;
    }

    @Comment("Услуга по профилю для стационар по умолчанию")
    @OneToOne
    @Deprecated
    public VocMedService getDefaultStacMedService() {
        return theDefaultStacMedService;
    }

    public void setDefaultStacMedService(VocMedService aDefaultStacMedService) {
        theDefaultStacMedService = aDefaultStacMedService;
    }

    @Comment("Код адреса отделения по умолчанию")
    public String getDefaultDepartmentCode() {
        return theDefaultDepartmentCode;
    }

    public void setDefaultDepartmentCode(String aDefaultDepartmentCode) {
        theDefaultDepartmentCode = aDefaultDepartmentCode;
    }
}
