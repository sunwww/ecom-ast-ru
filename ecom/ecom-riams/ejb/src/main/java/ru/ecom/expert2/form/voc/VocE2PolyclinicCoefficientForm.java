package ru.ecom.expert2.form.voc;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocE2PolyclinicCoefficient;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Тариф случая поликлинического обслуживания
 */
@EntityForm
@EntityFormPersistance(clazz = VocE2PolyclinicCoefficient.class)
@Comment("Coefficient случая поликлинического обслуживания")
@WebTrail(comment = "Коэффициент", nameProperties = "id", view = "entityParentView-e2_polyclinicCoefficient.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "profile", parentForm = VocE2MedHelpProfileForm.class)
@Setter
public class VocE2PolyclinicCoefficientForm extends VocCoefficientForm {
    /** Профиль мед. помощи */
    @Comment("Профиль мед. помощи")
    @Persist
    public Long getProfile() {return profile;}
    /** Медицинская специальность */
    private Long profile ;

    /**
     * Код для определения тарифа
     */
    @Comment("Код для определения тарифа")
    @Persist
    public Long getTariffType() {return tariffType;}
    private Long tariffType;

    /** Коэффициент для мобильной поликлиники */
    @Comment("Коэффициент для мобильной поликлиники")
    @Persist
    public Boolean getIsMobilePolyclinic() {return isMobilePolyclinic;}
    /** Коэффициент для мобильной поликлиники */
    private Boolean isMobilePolyclinic ;

    /** Для консультативного приема */
    @Comment("Для консультативного приема")
    @Persist
    public Boolean getIsConsultation() {return isConsultation;}
    /** Для консультативного приема */
    private Boolean isConsultation ;

    /** Для консультативно-диагностического обращений */
    @Comment("Для консультативно-диагностического обращений")
    @Persist
    public Boolean getIsDiagnosticSpo() {return isDiagnosticSpo;}
    /** Для консультативно-диагностического обращений */
    private Boolean isDiagnosticSpo ;

    /** Вид случая */
    @Comment("Вид случая")
    @Persist
    public Long getVidSluch() {return vidSluch;}
    /** Вид случая */
    private Long vidSluch ;
}
