package ru.ecom.expert2.form.voc;

import lombok.Setter;
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
@Setter
public class VocE2MedHelpProfileForm extends IdEntityForm {
    private String startDate;
    private String finishDate;
    private Boolean noActuality;
    private String profileK;
    private String name;
    private String code;
    private Long medSpecV021;
    private String defaultDepartmentCode;

    /**
     * Дата начала действия
     */
    @Comment("Дата начала действия")
    @Persist
    @DateString
    @DoDateString
    public String getStartDate() {
        return startDate;
    }

    /**
     * Дата окончания действия
     */
    @Comment("Дата окончания действия")
    @Persist
    @DateString
    @DoDateString
    public String getFinishDate() {
        return finishDate;
    }

    /**
     * Архивная запись
     */
    @Comment("Архивная запись")
    @Persist
    public Boolean getNoActuality() {
        return noActuality;
    }

    /**
     * Профиль ФОМС
     */
    @Comment("Профиль ФОМС")
    @Persist
    public String getProfileK() {
        return profileK;
    }

    /**
     * Название
     */
    @Comment("Название")
    @Persist
    public String getName() {
        return name;
    }

    /**
     * Код
     */
    @Comment("Код")
    @Persist
    public String getCode() {
        return code;
    }

    /**
     * Мед. специальность V021 по профилю
     */
    @Comment("Мед. специальность V021 по профилю")
    @Persist
    public Long getMedSpecV021() {
        return medSpecV021;
    }

    @Persist
    @Comment("Код адреса отделения по умолчанию")
    public String getDefaultDepartmentCode() {
        return defaultDepartmentCode;
    }
}
