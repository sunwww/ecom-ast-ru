package ru.ecom.expert2.form.financeplan;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.financeplan.MonthLittleAmountTable;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = MonthLittleAmountTable.class)
@Comment("Настроечная таблица месяцы-объемы")
@WebTrail(comment = "Настроечная таблица месяцы-объемы", nameProperties = "amount", view = "entityView-e2_litteAmountMonth.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class MonthLittleAmountTableForm extends IdEntityForm {
    /** Месяцы */
    @Comment("Месяцы")
    @Persist
    public String getMonths() {return theMonths;}
    public void setMonths(String aMonths) {theMonths = aMonths;}
    /** Месяцы */
    private String theMonths ;

    /** Количество */
    @Comment("Количество")
    @Persist
    public Long getAmount() {return theAmount;}
    public void setAmount(Long aAmount) {theAmount = aAmount;}
    /** Количество */
    private Long theAmount ;
}
