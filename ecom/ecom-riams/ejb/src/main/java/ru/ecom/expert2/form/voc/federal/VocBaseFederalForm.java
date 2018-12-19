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

}
