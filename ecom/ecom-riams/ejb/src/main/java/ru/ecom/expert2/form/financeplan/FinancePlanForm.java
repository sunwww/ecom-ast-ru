package ru.ecom.expert2.form.financeplan;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.financeplan.FinancePlan;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = FinancePlan.class)
@Comment("Финансовый план в стационаре")
@WebTrail(comment = "Финансовый план ", nameProperties = "id", view = "entityView-e2_stacFinancePlan.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Setter
public class FinancePlanForm extends IdEntityForm {

    /** Профиль медицинской помощи */
    @Comment("Профиль медицинской помощи")
    @Persist @Required
    public Long getProfile() {return profile;}
    /** Профиль медицинской помощи */
    private Long profile ;

    /** Отделение */
    @Comment("Отделение")
    @Persist
    public Long getDepartment() {return department;}
    /** Отделение */
    private Long department ;

    /** Количество случаев */
    @Comment("Количество случаев")
    @Persist @Required
    public Long getCount() {return count;}
    /** Количество случаев */
    private Long count ;

    /** Дата начала периода */
    @Comment("Дата начала периода")
    @Persist @Required
    @DateString @DoDateString
    public String getStartDate() {return startDate;}
    /** Дата начала периода */
    private String startDate ;

    /** Дата окончания периода */
    @Comment("Дата окончания периода")
    @Persist @Required
    @DateString @DoDateString
    public String getFinishDate() {return finishDate;}
    /** Дата окончания периода */
    private String finishDate ;

    /** Цена случая */
    @Comment("Цена случая")
    @Persist
    public String getCost() {return cost;}
    /** Цена случая */
    private String cost ;

    /** Вид случай */
    @Comment("Вид случай")
    @Persist @Required
    public Long getVidSluch() {return vidSluch;}
    /** Вид случай */
    private Long vidSluch ;


}
