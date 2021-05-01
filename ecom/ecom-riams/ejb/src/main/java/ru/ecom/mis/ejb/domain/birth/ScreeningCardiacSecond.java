package ru.ecom.mis.ejb.domain.birth;/**
 * Created by Milamesher on 23.01.2019.
 */

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.birth.voc.VocScreeningDiuresis;
import ru.ecom.mis.ejb.domain.birth.voc.VocScreeningLocalization;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Comment("Скрининг новорождённых на наличие врождённых пороков сердца II этап")
@Entity
@Getter
@Setter
public class ScreeningCardiacSecond extends ScreeningCardiacFirst {
    /** Локализация верхушечного толчка */
    @Comment("Локализация верхушечного толчка")
    @OneToOne
    public VocScreeningLocalization getApicalImpulseLocal() {return apicalImpulseLocal;}
    /** Локализация края печени */
    @Comment("Локализация края печени")
    @OneToOne
    public VocScreeningLocalization getLiverEdgeLocal() {return liverEdgeLocal;}
    /** Характеристика диуреза */
    @Comment("Характеристика диуреза")
    @OneToOne
    public VocScreeningDiuresis getDiuresis() {return diuresis;}
    /** Регистрация АД одновременная - правая рука*/
    private Long rightHandAD;
    /** Регистрация АД одновременная - нога*/
    private Long legAD;
    /** Локализация верхушечного толчка */
    private VocScreeningLocalization apicalImpulseLocal;
    /** Локализация края печени */
    private VocScreeningLocalization liverEdgeLocal;
    /** Характеристика диуреза */
    private VocScreeningDiuresis diuresis;
    /** ЭКГ (по показаниям) */
    private String eCG;
    /** Заключение для выписки */
    private String conclusion;
}