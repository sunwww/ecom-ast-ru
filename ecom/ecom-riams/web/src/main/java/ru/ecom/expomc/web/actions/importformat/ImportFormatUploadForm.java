/**
 * @author ikouzmin 08.03.2007 20:09:53
 */
package ru.ecom.expomc.web.actions.importformat;

import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import org.apache.struts.upload.FormFile;

public class ImportFormatUploadForm extends BaseValidatorForm {
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


    /** Отображать журнал */
    public boolean getViewLog() { return theViewLog ; }
    public void setViewLog(boolean aViewLog) { theViewLog = aViewLog ; }

    /** Отладка импорта */
    public boolean getDebug() { return theDebug ; }
    public void setDebug(boolean aDebug) { theDebug = aDebug ; }

    /** Обновлять только измененные записи */
    public boolean getUpdateModifiedOnly() { return theUpdateModifiedOnly ; }
    public void setUpdateModifiedOnly(boolean aUpdateModifiedOnly) { theUpdateModifiedOnly = aUpdateModifiedOnly ; }
    

    /** Проверять после сохранения */
    public boolean getVerifyAfterSave() { return theVerifyAfterSave ; }
    public void setVerifyAfterSave(boolean aVerifyAfterSave) { theVerifyAfterSave = aVerifyAfterSave ; }

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

    /** Отображать журнал */
    private boolean theViewLog;
    /** Отладка импорта */
    private boolean theDebug ;


    /** Обновлять только измененные записи */
    private boolean theUpdateModifiedOnly ;

    /** Проверять после сохранения */
    private boolean theVerifyAfterSave ;
}
