package ru.ecom.expert2.form.voc;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocOmcMedServiceCost;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = VocOmcMedServiceCost.class)
@Comment("Цена по ОМС услуги")
@WebTrail(comment = "Цена по ОМС услуги", nameProperties = "id", view = "entityView-e2_vocMedServiceCost.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Setter
public class VocOmcMedServiceCostForm extends IdEntityForm {
    /** Услуга */
    @Comment("Услуга")
    @Persist
    @Required
    public Long getMedService() {return medService;}
    private Long medService ;

    /** Цена */
    @Comment("Цена")
    @Persist
    @Required
    public String getCost() {return cost;}
    private String cost ;

    /** Дата начала действия цены */
    @Comment("Дата начала действия цены")
    @Persist
    @Required @DateString @DoDateString
    public String getStartDate() {return startDate;}
    private String startDate ;

    /** Дата окончания действия цены */
    @Comment("Дата окончания действия цены")
    @Persist
    @DateString @DoDateString
    public String getFinishDate() {return finishDate;}
    private String finishDate ;

    /** Рабочая функция врача по умолчанию */
    @Comment("Рабочая функция врача по умолчанию")
    @Persist
    public Long getWorkFunction() {return workFunction;}
    private Long workFunction ;
}
