package ru.ecom.expomc.ejb.services.form.check;

import java.util.Collection;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 *  Таблица проверок по формату
 */
public class ChecksTableForm extends BaseValidatorForm {
    /** Формат  */
    public long getFormat() { return format ; }
    public void setFormat(long aFormat) { format = aFormat ; }

    /** Поля */
    public Collection<ChecksTableFormRow> getChecksTableFormRows() { return checksTableFormRows ; }
    public void setChecksTableFormRows(Collection<ChecksTableFormRow> aChecksTableFormRows) { checksTableFormRows = aChecksTableFormRows ; }

    /** Поля */
    private Collection<ChecksTableFormRow> checksTableFormRows ;
    /** Формат  */
    private long format ;

}
