package ru.ecom.expert2.form.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;


/**
 * Справочник профилей медицинской помощи
 */
@EntityForm
@EntityFormPersistance(clazz = VocE2MedHelpProfile.class)
@Comment("Справочник профилей медицинской помощи")
@WebTrail(comment = "Справочник профилей медицинской помощи", nameProperties = "id", view = "entityView-e2_vocMedHelpProfile.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class VocE2MedHelpProfileForm extends IdEntityForm {
    private String theStartDate;
    private String theFinishDate;
    private Boolean theNoActuality;
    private String theProfileK;
    private String theName;
    private String theCode;
    private Long theMedSpecV021;
    private String theDefaultDepartmentCode;

    /**
     * Дата начала действия
     */
    @Comment("Дата начала действия")
    @Persist
    @DateString
    @DoDateString
    public String getStartDate() {
        return theStartDate;
    }

    public void setStartDate(String aStartDate) {
        theStartDate = aStartDate;
    }

    /**
     * Дата окончания действия
     */
    @Comment("Дата окончания действия")
    @Persist
    @DateString
    @DoDateString
    public String getFinishDate() {
        return theFinishDate;
    }

    public void setFinishDate(String aFinishDate) {
        theFinishDate = aFinishDate;
    }

    /**
     * Архивная запись
     */
    @Comment("Архивная запись")
    @Persist
    public Boolean getNoActuality() {
        return theNoActuality;
    }

    public void setNoActuality(Boolean aNoActuality) {
        theNoActuality = aNoActuality;
    }

    /**
     * Профиль ФОМС
     */
    @Comment("Профиль ФОМС")
    @Persist
    public String getProfileK() {
        return theProfileK;
    }

    public void setProfileK(String aProfileK) {
        theProfileK = aProfileK;
    }

    /**
     * Название
     */
    @Comment("Название")
    @Persist
    public String getName() {
        return theName;
    }

    public void setName(String aName) {
        theName = aName;
    }

    /**
     * Код
     */
    @Comment("Код")
    @Persist
    public String getCode() {
        return theCode;
    }

    public void setCode(String aCode) {
        theCode = aCode;
    }

    /**
     * Мед. специальность V021 по профилю
     */
    @Comment("Мед. специальность V021 по профилю")
    @Persist
    public Long getMedSpecV021() {
        return theMedSpecV021;
    }

    public void setMedSpecV021(Long aMedSpecV021) {
        theMedSpecV021 = aMedSpecV021;
    }

    @Persist
    @Comment("Код адреса отделения по умолчанию")
    public String getDefaultDepartmentCode() {
        return theDefaultDepartmentCode;
    }

    public void setDefaultDepartmentCode(String aDefaultDepartmentCode) {
        theDefaultDepartmentCode = aDefaultDepartmentCode;
    }
}
