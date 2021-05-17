package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.birth.ScreeningCardiacSecond;
import ru.ecom.mis.ejb.form.birth.interceptors.ScreeningCardiacFirstPreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Created by Milamesher on 23.01.2019.
 */
@EntityForm
@EntityFormPersistance(clazz= ScreeningCardiacSecond.class)
@Comment("Скрининг новорождённых на наличие врождённых пороков сердца II этап")
@WebTrail(comment = "Скрининг новорождённых II этап", nameProperties= "id", view="entityParentView-stac_screeningCardiacSecond.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy/CardiacScreening")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(ScreeningCardiacFirstPreCreateInterceptor.class)
)
@Setter

public class ScreeningCardiacSecondForm extends ScreeningCardiacFirstForm {
    /** Регистрация АД одновременная - правая рука */
    @Comment("Регистрация АД одновременная - правая рука")
    @Persist
    @Required
    public Long getRightHandAD() {return rightHandAD;}
    /** Регистрация АД одновременная - нога */
    @Comment("Регистрация АД одновременная - нога")
    @Persist
    @Required
    public Long getLegAD() {return legAD;}
    /** Локализация верхушечного толчка */
    @Comment("Локализация верхушечного толчка")
    @Persist
    @Required
    public Long getApicalImpulseLocal() {return apicalImpulseLocal;}
    /** Локализация края печени */
    @Comment("Локализация края печени")
    @Persist
    @Required
    public Long getLiverEdgeLocal() {return liverEdgeLocal;}
    /** Характеристика диуреза */
    @Comment("Характеристика диуреза")
    @Persist
    @Required
    public Long getDiuresis() {return diuresis;}
    /** ЭКГ (по показаниям)  */
    @Comment("ЭКГ (по показаниям)")
    @Persist
    public String getECG() {return eCG;}
    /** Заключение для выписки  */
    @Comment("Заключение для выписки")
    @Persist
    public String getConclusion() {return conclusion;}


    /** Регистрация АД одновременная - правая рука*/
    private Long rightHandAD;
    /** Регистрация АД одновременная - нога*/
    private Long legAD;
    /** Локализация верхушечного толчка */
    private Long apicalImpulseLocal;
    /** Локализация края печени */
    private Long liverEdgeLocal;
    /** Характеристика диуреза */
    private Long diuresis;
    /** ЭКГ (по показаниям) */
    private String eCG;
    /** Заключение для выписки */
    private String conclusion;
}