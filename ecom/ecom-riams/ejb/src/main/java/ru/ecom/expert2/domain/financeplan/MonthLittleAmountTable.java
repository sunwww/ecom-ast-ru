package ru.ecom.expert2.domain.financeplan;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
/**Вот тут находятся соответствия - в каком месяце ставить план, если в годовом плане случаев меньше 12 */
public class MonthLittleAmountTable extends BaseEntity {

    /** Месяцы */
    private String months;

    /** Количество */
    private Long amount;
}
