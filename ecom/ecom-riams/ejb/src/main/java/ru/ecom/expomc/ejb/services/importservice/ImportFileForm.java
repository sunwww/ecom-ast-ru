package ru.ecom.expomc.ejb.services.importservice;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Параметры импорта
 */
public class ImportFileForm extends BaseValidatorForm {

    /** Комментарий */
    public String getComment() { return comment ; }
    public void setComment(String aComment) { comment = aComment ; }

    /** Комментарий */
    private String comment ;
    /** Дата актуальности с */
    @DateString
    @Required
    public String getActualDateFrom() { return actualDateFrom ; }
    public void setActualDateFrom(String aActualDateFrom) { actualDateFrom = aActualDateFrom ; }

    /** Дата актуальности по */
    @DateString
    public String getActualDateTo() { return actualDateTo ; }
    public void setActualDateTo(String aActualDateTo) { actualDateTo = aActualDateTo ; }

    /** Формат импорта Format */
    @Required
    public long getImportFormat() { return importFormat ; }
    public void setImportFormat(long aImportFormat) { importFormat = aImportFormat ; }

    /** Формат импорта */
    private long importFormat ;
    /** Дата актуальности по */
    private String actualDateTo ;
    /** Дата актуальности с */
    private String actualDateFrom ;

}
