package ru.ecom.expert2.domain.financeplan;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
/**Вот тут находятся соответствия - в каком месяце ставить план, если в годовом плане случаев меньше 12 */
public class MonthLittleAmountTable extends BaseEntity {

    /** Месяцы */
    @Comment("Месяцы")
    public String getMonths() {return theMonths;}
    public void setMonths(String aMonths) {theMonths = aMonths;}
    /** Месяцы */
    private String theMonths ;

    /** Количество */
    @Comment("Количество")
    public Long getAmount() {return theAmount;}
    public void setAmount(Long aAmount) {theAmount = aAmount;}
    /** Количество */
    private Long theAmount ;
}
