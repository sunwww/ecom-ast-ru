package ru.ecom.expert2.form;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = VocKsg.class)
@Comment("Диагноз по записи")
@WebTrail(comment = "КСГ", nameProperties = "id", view = "entityView-e2_vocKsg.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Setter
public class VocKsgForm extends IdEntityForm {

    private Long group;
    private Integer year;
    private Boolean longKsg;
    private Boolean isOperation;
    private Boolean isFullPayment;
    private Double kz;
    private String profile;
    private Long bedSubType;
    private Boolean doNotUseCusmo;
    private Boolean isCovid19;
    private String code;
    private String name;
    private String doctorCost;

    /**
     * Группа КСГ
     */
    @Comment("Группа КСГ")
    @Persist
    public Long getGroup() {
        return group;
    }

    /**
     * Год КСГ
     */
    @Comment("Год КСГ")
    @Persist
    public Integer getYear() {
        return year;
    }

    /**
     * Сверхдлительный КСГ (45 дней)
     */
    @Comment("Длительный срок лечения КСГ")
    @Persist
    public Boolean getLongKsg() {
        return longKsg;
    }

    /**
     * Является операцией
     */
    @Comment("Является операцией")
    @Persist
    public Boolean getIsOperation() {
        return isOperation;
    }

    /**
     * Оплачивать в полном объеме
     */
    @Comment("Оплачивать в полном объеме")
    @Persist
    public Boolean getIsFullPayment() {
        return isFullPayment;
    }

    /**
     * Коэффициент затрат
     */
    @Comment("Коэффициент затрат")
    @Persist
    @Required
    public Double getKz() {
        return kz;
    }

    /**
     * Профиль помощи
     */
    @Comment("Профиль помощи")
    @Persist
    public String getProfile() {
        return profile;
    }

    /**
     * Тип коек
     */
    @Comment("Тип коек")
    @Persist
    @Required
    public Long getBedSubType() {
        return bedSubType;
    }

    /**
     * Не учитывать КУСмо
     */
    @Comment("Не учитывать КУСмо")
    @Persist
    public Boolean getDoNotUseCusmo() {
        return doNotUseCusmo;
    }

    /**
     * Covid-19 КСГ
     */
    @Comment("Covid-19 КСГ")
    @Persist
    public Boolean getIsCovid19() {
        return isCovid19;
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
     * Название
     */
    @Comment("Название")
    @Persist
    public String getName() {
        return name;
    }

    @Comment("Доля врача в КСГ")
    @Persist
    public String getDoctorCost() {
        return doctorCost;
    }

}
