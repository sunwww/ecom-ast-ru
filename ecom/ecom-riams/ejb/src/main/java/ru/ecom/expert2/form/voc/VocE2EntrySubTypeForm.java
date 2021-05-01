package ru.ecom.expert2.form.voc;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocE2EntrySubType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = VocE2EntrySubType.class)
@Comment("Подтип экономического случая")
@WebTrail(comment = "Подтип экономического случая", nameProperties = "id", view = "entityView-e2_vocEntrySubType.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Setter
public class VocE2EntrySubTypeForm extends IdEntityForm {

    /** Название */
    @Comment("Название")
    @Persist
    public String getName() {return name;}
    /** Название */
    private String name ;

    /** Код */
    @Comment("Код")
    @Persist
    public String getCode() {return code;}
    /** Код */
    private String code ;

    /** Код для определения тарифа */
    @Comment("Код для определения тарифа")
    @Persist
    public Long getTariffCode() {return tariffCode;}
    /** Код для определения тарифа */
    private Long tariffCode ;

    /** Условия оказания мед. помощи для подачи */
    @Comment("Условия оказания мед. помощи для подачи")
    @Persist
    public Long getUslOk() {return uslOk;}
    /** Условия оказания мед. помощи для подачи */
    private Long uslOk ;

    /** В архиве */
    @Comment("В архиве")
    @Persist
    public Boolean getIsArchival() {return isArchival;}
    /** В архиве */
    private Boolean isArchival ;

    /** Посещение в консультативной поликлинике */
    @Comment("Посещение в консультативной поликлинике")
    @Persist
    public Boolean getIsConsultation() {return isConsultation;}
    /** Посещение в консультативной поликлинике */
    private Boolean isConsultation ;

    /** Вид случая */
    @Comment("Вид случая")
    @Persist
    public Long getVidSluch() {return vidSluch;}
    /** Вид случая */
    private Long vidSluch ;

    /** Цель посещения */
    @Comment("Цель посещения")
    @Persist
    public Long getVisitPurpose() {return visitPurpose;}
    /** Цель посещения */
    private Long visitPurpose ;

    /** Способ оплаты */
    @Comment("Способ оплаты")
    @Persist
    public Long getIdsp() {return idsp;}
    /** Способ оплаты */
    private Long idsp ;

    /** Вид доп. диспансеризации */
    @Comment("Вид доп. диспансеризации")
    @Persist
    public Long getExtDispType() {return extDispType;}
    /** Вид доп. диспансеризации */
    private Long extDispType ;

    /** Тип файла */
    @Comment("Тип файла")
    @Persist
    public String getFileType() {return fileType;}
    private String fileType ;

    /** Назначение платежа в счете */
    @Comment("Назначение платежа в счете")
    @Persist
    public String getBillProperty() {return billProperty;}
    private String billProperty ;

}
