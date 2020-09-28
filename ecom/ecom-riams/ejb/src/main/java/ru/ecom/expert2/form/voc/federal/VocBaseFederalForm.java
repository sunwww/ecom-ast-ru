package ru.ecom.expert2.form.voc.federal;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

import java.io.Serializable;

@EntityForm
public abstract class VocBaseFederalForm extends IdEntityForm implements IEntityForm, Serializable {
    /** Дата начала действия */
    @Comment("Дата начала действия")
    @Persist
    @DoDateString @DateString
    public String getStartDate() {return theStartDate;}
    public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
    /** Дата начала действия */
    private String theStartDate ;

    /** Дата окончания действия */
    @Comment("Дата окончания действия")
    @Persist @DateString @DoDateString
    public String getFinishDate() {return theFinishDate;}
    public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата окончания действия */
    private String theFinishDate ;

    /** Неактуальная запись */
    @Comment("Неактуальная запись")
    @Persist
    public Boolean getIsNoActual() {return theIsNoActual;}
    public void setIsNoActual(Boolean aIsNoActual) {theIsNoActual = aIsNoActual;}
    /** Неактуальная запись */
    private Boolean theIsNoActual =false;

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

    /** Добавить привязку */
    @Comment("Добавить привязку")
    public Long getMedServiceAdd() {return theMedServiceAdd;}
    public void setMedServiceAdd(Long aMedServiceAdd) {theMedServiceAdd = aMedServiceAdd;}
    private Long theMedServiceAdd ;

    /** Булево поле на форме */
    @Comment("Булево поле на форме")
    public Boolean getBooleanAdd() {return theBooleanAdd;}
    public void setBooleanAdd(Boolean aBooleanAdd) {theBooleanAdd = aBooleanAdd;}
    private Boolean theBooleanAdd ;

}
