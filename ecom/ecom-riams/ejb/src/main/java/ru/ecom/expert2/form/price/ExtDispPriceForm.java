package ru.ecom.expert2.form.price;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.price.ExtDispPrice;
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
@EntityFormPersistance(clazz = ExtDispPrice.class)
@Comment("Цена ДД")
@WebTrail(comment = "Цена ДД", nameProperties = "id", view = "entityView-e2_extDispPrice.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Setter
public class ExtDispPriceForm extends IdEntityForm {

    /** Тип диспансеризации */
    @Comment("Тип диспансеризации")
    @Persist @Required
    public Long getDispType() {return dispType;}
    /** Тип диспансеризации */
    private Long dispType ;

    /** Пол */
    @Comment("Пол")
    @Persist
    public Long getSex() {return sex;}
    /** Пол */
    private Long sex ;

    /** Цена полного случая */
    @Comment("Цена полного случая")
    @Persist @Required
    public String getCost() {return cost;}
    /** Цена полного случая */
    private String cost ;

    /** Возраст с (мес) */
    @Comment("Возраст с (мес)")
    @Persist
    public Integer getAgeFrom() {return ageFrom;}
    private Integer ageFrom ;

    /** Возраст по (мес) */
    @Comment("Возраст по (мес)")
    @Persist
    public Integer getAgeTo() {return ageTo;}
    private Integer ageTo ;


    /** Ценовая (социальная) группа */
    @Comment("Ценовая (социальная) группа")
    @Persist
    public Integer getAgeGroup() {return ageGroup;}
    /** Ценовая (социальная) группа */
    private Integer ageGroup ;

    /** Дата начала действия цены */
    @Comment("Дата начала действия цены")
    @Persist @Required
    @DateString @DoDateString
    public String getDateFrom() {return dateFrom;}
    /** Дата начала действия цены */
    private String dateFrom ;

    /** Дата окончания действия */
    @Comment("Дата окончания действия")
    @Persist
    @DateString @DoDateString
    public String getDateTo() {return dateTo;}
    /** Дата окончания действия */
    private String dateTo ;

    /** Возраста  */
    @Comment("Возраста ")
    @Persist
    public String getAges() {return ages;}
    /** Возраста  */
    private String ages ;

    /** Минимальное кол-во услуг */
    @Comment("Минимальное кол-во услуг")
    @Persist @Required
    public Integer getMinServices() {return minServices;}
    private Integer minServices ;
}
