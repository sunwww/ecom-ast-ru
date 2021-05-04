package ru.ecom.expert2.form.voc.federal;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

import java.io.Serializable;

@EntityForm
@Setter
public abstract class VocBaseFederalForm extends IdEntityForm implements IEntityForm, Serializable {
    /** Дата начала действия */
    @Comment("Дата начала действия")
    @Persist
    @DoDateString @DateString
    public String getStartDate() {return startDate;}
    /** Дата начала действия */
    private String startDate ;

    /** Дата окончания действия */
    @Comment("Дата окончания действия")
    @Persist @DateString @DoDateString
    public String getFinishDate() {return finishDate;}
    /** Дата окончания действия */
    private String finishDate ;

    /** Неактуальная запись */
    @Comment("Неактуальная запись")
    @Persist
    public Boolean getIsNoActual() {return isNoActual;}
    /** Неактуальная запись */
    private Boolean isNoActual =false;

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

    /** Добавить привязку */
    @Comment("Добавить привязку")
    public Long getMedServiceAdd() {return medServiceAdd;}
    private Long medServiceAdd ;

    /** Булево поле на форме */
    @Comment("Булево поле на форме")
    public Boolean getBooleanAdd() {return booleanAdd;}
    private Boolean booleanAdd ;

}
