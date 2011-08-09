package ru.ecom.expomc.web.actions.importtime;

import org.apache.struts.upload.FormFile;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Форма дял загрузки файла
 */
public class ImportTimeUploadForm extends BaseValidatorForm {

    /** Комментарий */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Дата актуальности с */
    @DateString
    public String getActualDateFrom() { return theActualDateFrom ; }
    public void setActualDateFrom(String aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }

    /** Дата актуальности по */
    @DateString
    public String getActualDateTo() { return theActualDateTo ; }
    public void setActualDateTo(String aActualDateTo) { theActualDateTo = aActualDateTo ; }

    /** Документ */
    @Required
    public long getDocument() { return theDocument ; }
    public void setDocument(long aDocument) { theDocument = aDocument ; }

    /** Формат */
    @Required
    public long getFormat() { return theFormat ; }
    public void setFormat(long aFormat) { theFormat = aFormat ; }

    /** Формат */
    private long theFormat ;
    /** Документ */
    private long theDocument ;
    /** Файл */
    public FormFile getFile() { return theFile ; }
    public void setFile(FormFile aFile) { theFile = aFile ; }

    /** Файл */
    private FormFile theFile ;
    /** Дата актуальности по */
    private String theActualDateTo ;
    /** Дата актуальности с */
    private String theActualDateFrom ;
    /** Комментарий */
    private String theComment ;
}
