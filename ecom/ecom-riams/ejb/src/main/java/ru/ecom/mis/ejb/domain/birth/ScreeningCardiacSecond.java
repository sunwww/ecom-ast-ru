package ru.ecom.mis.ejb.domain.birth;/**
 * Created by Milamesher on 23.01.2019.
 */

import ru.ecom.mis.ejb.domain.birth.voc.VocScreeningDiuresis;
import ru.ecom.mis.ejb.domain.birth.voc.VocScreeningLocalization;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Comment("Скрининг новорождённых на наличие врождённых пороков сердца II этап")
@Entity
public class ScreeningCardiacSecond extends ScreeningCardiacFirst {
    /** Регистрация АД одновременная - правая рука */
    @Comment("Регистрация АД одновременная - правая рука")
    public Long getRightHandAD() {return theRightHandAD;}
    public void setRightHandAD(Long aRightHandAD) {theRightHandAD = aRightHandAD;}
    /** Регистрация АД одновременная - нога */
    @Comment("Регистрация АД одновременная - нога")
    public Long getLegAD() {return theLegAD;}
    public void setLegAD(Long aLegAD) {theLegAD = aLegAD;}
    /** Локализация верхушечного толчка */
    @Comment("Локализация верхушечного толчка")
    @OneToOne
    public VocScreeningLocalization getApicalImpulseLocal() {return theApicalImpulseLocal;}
    public void setApicalImpulseLocal(VocScreeningLocalization aApicalImpulseLocal) {theApicalImpulseLocal = aApicalImpulseLocal;}
    /** Локализация края печени */
    @Comment("Локализация края печени")
    @OneToOne
    public VocScreeningLocalization getLiverEdgeLocal() {return theLiverEdgeLocal;}
    public void setLiverEdgeLocal(VocScreeningLocalization aLiverEdgeLocal) {theLiverEdgeLocal = aLiverEdgeLocal;}
    /** Характеристика диуреза */
    @Comment("Характеристика диуреза")
    @OneToOne
    public VocScreeningDiuresis getDiuresis() {return theDiuresis;}
    public void setDiuresis(VocScreeningDiuresis aDiuresis) {theDiuresis = aDiuresis;}
    /** ЭКГ (по показаниям)  */
    @Comment("ЭКГ (по показаниям)")
    public String getECG() {return theECG;}
    public void setECG(String aECG) {theECG = aECG;}
    /** Заключение для выписки  */
    @Comment("Заключение для выписки")
    public String getConclusion() {return theConclusion;}
    public void setConclusion(String aConclusion) {theConclusion = aConclusion;}

    /** Регистрация АД одновременная - правая рука*/
    private Long theRightHandAD;
    /** Регистрация АД одновременная - нога*/
    private Long theLegAD;
    /** Локализация верхушечного толчка */
    private VocScreeningLocalization theApicalImpulseLocal;
    /** Локализация края печени */
    private VocScreeningLocalization theLiverEdgeLocal;
    /** Характеристика диуреза */
    private VocScreeningDiuresis theDiuresis;
    /** ЭКГ (по показаниям) */
    private String theECG;
    /** Заключение для выписки */
    private String theConclusion;
}