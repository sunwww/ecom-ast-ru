package ru.ecom.mis.ejb.form.birth;

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
public class ScreeningCardiacSecondForm extends ScreeningCardiacFirstForm {
    /** Регистрация АД одновременная - правая рука */
    @Comment("Регистрация АД одновременная - правая рука")
    @Persist
    @Required
    public Long getRightHandAD() {return theRightHandAD;}
    public void setRightHandAD(Long aRightHandAD) {theRightHandAD = aRightHandAD;}
    /** Регистрация АД одновременная - нога */
    @Comment("Регистрация АД одновременная - нога")
    @Persist
    @Required
    public Long getLegAD() {return theLegAD;}
    public void setLegAD(Long aLegAD) {theLegAD = aLegAD;}
    /** Локализация верхушечного толчка */
    @Comment("Локализация верхушечного толчка")
    @Persist
    @Required
    public Long getApicalImpulseLocal() {return theApicalImpulseLocal;}
    public void setApicalImpulseLocal(Long aApicalImpulseLocal) {theApicalImpulseLocal = aApicalImpulseLocal;}
    /** Локализация края печени */
    @Comment("Локализация края печени")
    @Persist
    @Required
    public Long getLiverEdgeLocal() {return theLiverEdgeLocal;}
    public void setLiverEdgeLocal(Long aLiverEdgeLocal) {theLiverEdgeLocal = aLiverEdgeLocal;}
    /** Характеристика диуреза */
    @Comment("Характеристика диуреза")
    @Persist
    @Required
    public Long getDiuresis() {return theDiuresis;}
    public void setDiuresis(Long aDiuresis) {theDiuresis = aDiuresis;}
    /** ЭКГ (по показаниям)  */
    @Comment("ЭКГ (по показаниям)")
    @Persist
    public String getECG() {return theECG;}
    public void setECG(String aECG) {theECG = aECG;}
    /** Заключение для выписки  */
    @Comment("Заключение для выписки")
    @Persist
    public String getConclusion() {return theConclusion;}
    public void setConclusion(String aConclusion) {theConclusion = aConclusion;}

    /** СМО */
    private Long theMedCase;
    /** Регистрация АД одновременная - правая рука*/
    private Long theRightHandAD;
    /** Регистрация АД одновременная - нога*/
    private Long theLegAD;
    /** Локализация верхушечного толчка */
    private Long theApicalImpulseLocal;
    /** Локализация края печени */
    private Long theLiverEdgeLocal;
    /** Характеристика диуреза */
    private Long theDiuresis;
    /** ЭКГ (по показаниям) */
    private String theECG;
    /** Заключение для выписки */
    private String theConclusion;
}