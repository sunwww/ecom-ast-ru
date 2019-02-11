package ru.ecom.expert2.form.voc;

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
public class VocE2PolyclinicCoefficientForm extends VocCoefficientForm {
    /** Профиль мед. помощи */
    @Comment("Профиль мед. помощи")
    @Persist
    public Long getProfile() {return theProfile;}
    public void setProfile(Long aProfile) {theProfile = aProfile;}
    /** Медицинская специальность */
    private Long theProfile ;

    /** Тип случая */
    @Comment("Тип случая")
    @Persist @Deprecated
    public Long getEntryType() {return theEntryType;}
    public void setEntryType(Long aEntryType) {theEntryType = aEntryType;}
    /** Тип случая */
    private Long theEntryType ;

    /** Коэффициент для мобильной поликлиники */
    @Comment("Коэффициент для мобильной поликлиники")
    @Persist
    public Boolean getIsMobilePolyclinic() {return theIsMobilePolyclinic;}
    public void setIsMobilePolyclinic(Boolean aIsMobilePolyclinic) {theIsMobilePolyclinic = aIsMobilePolyclinic;}
    /** Коэффициент для мобильной поликлиники */
    private Boolean theIsMobilePolyclinic ;

    /** Для консультативного приема */
    @Comment("Для консультативного приема")
    @Persist
    public Boolean getIsConsultation() {return theIsConsultation;}
    public void setIsConsultation(Boolean aIsConsultation) {theIsConsultation = aIsConsultation;}
    /** Для консультативного приема */
    private Boolean theIsConsultation ;

    /** Для консультативно-диагностического обращений */
    @Comment("Для консультативно-диагностического обращений")
    @Persist
    public Boolean getIsDiagnosticSpo() {return theIsDiagnosticSpo;}
    public void setIsDiagnosticSpo(Boolean aIsDiagnosticSpo) {theIsDiagnosticSpo = aIsDiagnosticSpo;}
    /** Для консультативно-диагностического обращений */
    private Boolean theIsDiagnosticSpo ;

    /** Вид случая */
    @Comment("Вид случая")
    @Persist
    public Long getVidSluch() {return theVidSluch;}
    public void setVidSluch(Long aVidSluch) {theVidSluch = aVidSluch;}
    /** Вид случая */
    private Long theVidSluch ;
}
