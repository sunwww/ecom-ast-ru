package ru.ecom.expert2.form;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;

@Setter
@Getter
public class FinancePlanJournalForm extends BaseValidatorForm {

    /** Профиль мед. помощи */
    private Long profile ;

    /** Отделение */
    private Long department ;

    /** КСГ */
    private Long ksg ;

    /** Группа КСГ */
    private Long ksgGroup ;

    /** Тип коек */
    private Long bedSubType ;

    /** Вид случая */
    private Long vidSluch ;

    /** Профиль коек V020 */
    private Long bedProfile ;


}
