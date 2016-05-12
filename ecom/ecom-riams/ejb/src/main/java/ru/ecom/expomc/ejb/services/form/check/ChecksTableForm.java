package ru.ecom.expomc.ejb.services.form.check;

import java.util.Collection;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 *  Таблица проверок по формату
 */
public class ChecksTableForm extends BaseValidatorForm {
    /** Формат  */
    public long getFormat() { return theFormat ; }
    public void setFormat(long aFormat) { theFormat = aFormat ; }

    /** Поля */
    public Collection<ChecksTableFormRow> getChecksTableFormRows() { return theChecksTableFormRows ; }
    public void setChecksTableFormRows(Collection<ChecksTableFormRow> aChecksTableFormRows) { theChecksTableFormRows = aChecksTableFormRows ; }

    /** Поля */
    private Collection<ChecksTableFormRow> theChecksTableFormRows ;
    /** Формат  */
    private long theFormat ;

}
