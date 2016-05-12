package ru.ecom.expomc.web.actions.format.importfromdbf;

import org.apache.struts.upload.FormFile;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 * Форма для импорта DBF
 */
public class DbfImportForm extends BaseValidatorForm {

    /** Идентфикатор формата */
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Файл */
    public FormFile getFile() { return theFile ; }
    public void setFile(FormFile aFile) { theFile = aFile ; }

    /** Не импортировать WHAT, WHEN, etc */
    public boolean getDoNotImportWhatWhen() { return theDoNotImportWhatWhen ; }
    public void setDoNotImportWhatWhen(boolean aDoNotImportWhatWhen) { theDoNotImportWhatWhen = aDoNotImportWhatWhen ; }

    /** Не импортировать WHAT, WHEN, etc */
    private boolean theDoNotImportWhatWhen ;
    /** Файл */
    private FormFile theFile ;
    /** Идентфикатор формата */
    private long theId ;

}
