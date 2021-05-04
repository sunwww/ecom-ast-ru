package ru.ecom.expert2.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV006;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV010;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV016;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV025;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@Getter
@Setter
public class VocE2EntrySubType extends VocBaseEntity {
    /**
     * Код для определения тарифа
     */
    @Comment("Код для определения тарифа")
    @OneToOne
    public VocE2BaseTariffType getTariffCode() {return tariffCode;}
    private VocE2BaseTariffType tariffCode;

    /**
     * Условия оказания мед. помощи для подачи
     */
    @Comment("Условия оказания мед. помощи для подачи")
    @OneToOne
    public VocE2FondV006 getUslOk() {
        return uslOk;
    }

    /**
     * Условия оказания мед. помощи для подачи
     */
    private VocE2FondV006 uslOk;


    /**
     * В архиве
     */
    private Boolean isArchival;

    /**
     * Посещение в консультативной поликлинике
     */
    private Boolean isConsultation;

    @Comment("Вид случая")
    @OneToOne
    public VocE2VidSluch getVidSluch() {
        return vidSluch;
    }

    /**
     * Вид случая
     */
    private VocE2VidSluch vidSluch;

    @Comment("Цель посещения")
    @OneToOne
    public VocE2FondV025 getVisitPurpose() {
        return visitPurpose;
    }

    /**
     * Цель посещения
     */
    private VocE2FondV025 visitPurpose;

    @Comment("Вид доп. диспансеризации")
    @OneToOne
    public VocE2FondV016 getExtDispType() {
        return extDispType;
    }


    /**
     * Вид доп. диспансеризации
     */
    private VocE2FondV016 extDispType;

    @Comment("Способ оплаты")
    @OneToOne
    public VocE2FondV010 getIdsp() {
        return idsp;
    }


    /**
     * Способ оплаты
     */
    private VocE2FondV010 idsp;

    private String fileType;

    private String billProperty;

    @Transient
    public String getTariffCodeString() {return tariffCode == null ? null : tariffCode.getCode();}

}
