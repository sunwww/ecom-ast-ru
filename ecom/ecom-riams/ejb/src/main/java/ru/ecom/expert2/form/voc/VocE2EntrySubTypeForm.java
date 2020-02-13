package ru.ecom.expert2.form.voc;

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
public class VocE2EntrySubTypeForm extends IdEntityForm {

    /** Название */
    @Comment("Название")
    @Persist
    public String getName() {return theName;}
    public void setName(String aName) {theName = aName;}
    /** Название */
    private String theName ;

    /** Код */
    @Comment("Код")
    @Persist
    public String getCode() {return theCode;}
    public void setCode(String aCode) {theCode = aCode;}
    /** Код */
    private String theCode ;

    /** Код для определения тарифа */
    @Comment("Код для определения тарифа")
    @Persist
    public String getTariffCode() {return theTariffCode;}
    public void setTariffCode(String aTariffCode) {theTariffCode = aTariffCode;}
    /** Код для определения тарифа */
    private String theTariffCode ;

    /** Условия оказания мед. помощи для подачи */
    @Comment("Условия оказания мед. помощи для подачи")
    @Persist
    public Long getUslOk() {return theUslOk;}
    public void setUslOk(Long aUslOk) {theUslOk = aUslOk;}
    /** Условия оказания мед. помощи для подачи */
    private Long theUslOk ;

    /** В архиве */
    @Comment("В архиве")
    @Persist
    public Boolean getIsArchival() {return theIsArchival;}
    public void setIsArchival(Boolean aIsArchival) {theIsArchival = aIsArchival;}
    /** В архиве */
    private Boolean theIsArchival ;

    /** Посещение в консультативной поликлинике */
    @Comment("Посещение в консультативной поликлинике")
    @Persist
    public Boolean getIsConsultation() {return theIsConsultation;}
    public void setIsConsultation(Boolean aIsConsultation) {theIsConsultation = aIsConsultation;}
    /** Посещение в консультативной поликлинике */
    private Boolean theIsConsultation ;

    /** Вид случая */
    @Comment("Вид случая")
    @Persist
    public Long getVidSluch() {return theVidSluch;}
    public void setVidSluch(Long aVidSluch) {theVidSluch = aVidSluch;}
    /** Вид случая */
    private Long theVidSluch ;

    /** Цель посещения */
    @Comment("Цель посещения")
    @Persist
    public Long getVisitPurpose() {return theVisitPurpose;}
    public void setVisitPurpose(Long aVisitPurpose) {theVisitPurpose = aVisitPurpose;}
    /** Цель посещения */
    private Long theVisitPurpose ;

    /** Способ оплаты */
    @Comment("Способ оплаты")
    @Persist
    public Long getIdsp() {return theIdsp;}
    public void setIdsp(Long aIdsp) {theIdsp = aIdsp;}
    /** Способ оплаты */
    private Long theIdsp ;

    /** Вид доп. диспансеризации */
    @Comment("Вид доп. диспансеризации")
    @Persist
    public Long getExtDispType() {return theExtDispType;}
    public void setExtDispType(Long aExtDispType) {theExtDispType = aExtDispType;}
    /** Вид доп. диспансеризации */
    private Long theExtDispType ;

    /** Тип файла */
    @Comment("Тип файла")
    @Persist
    public String getFileType() {return theFileType;}
    public void setFileType(String aFileType) {theFileType = aFileType;}
    private String theFileType ;

    /** Назначение платежа в счете */
    @Comment("Назначение платежа в счете")
    @Persist
    public String getBillProperty() {return theBillProperty;}
    public void setBillProperty(String aBillProperty) {theBillProperty = aBillProperty;}
    private String theBillProperty ;

}
