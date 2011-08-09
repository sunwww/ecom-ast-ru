package ru.ecom.expomc.ejb.services.importservice;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Параметры импорта
 */
public class ImportFileForm extends BaseValidatorForm {

    /** Комментарий */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Комментарий */
    private String theComment ;
    /** Дата актуальности с */
    @DateString
    @Required
    public String getActualDateFrom() { return theActualDateFrom ; }
    public void setActualDateFrom(String aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }

    /** Дата актуальности по */
    @DateString
    public String getActualDateTo() { return theActualDateTo ; }
    public void setActualDateTo(String aActualDateTo) { theActualDateTo = aActualDateTo ; }

    /** Формат импорта Format */
    @Required
    public long getImportFormat() { return theImportFormat ; }
    public void setImportFormat(long aImportFormat) { theImportFormat = aImportFormat ; }

    /** Формат импорта */
    private long theImportFormat ;
    /** Дата актуальности по */
    private String theActualDateTo ;
    /** Дата актуальности с */
    private String theActualDateFrom ;

}
