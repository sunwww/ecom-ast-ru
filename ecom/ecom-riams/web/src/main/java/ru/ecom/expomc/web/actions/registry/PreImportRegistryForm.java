package ru.ecom.expomc.web.actions.registry;

import org.apache.struts.upload.FormFile;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 * Форма для подготовки к импорту
 */
public class PreImportRegistryForm extends BaseValidatorForm {

    /** Иногородние */
    public boolean getForeign() { return theForeign ; }
    public void setForeign(boolean aForeign) { theForeign = aForeign ; }

    /** Иногородние */
    private boolean theForeign ;
    /** Файл */
    public FormFile getFile() { return theFile ; }
    public void setFile(FormFile aFile) { theFile = aFile ; }

    /** Документ для астраханцев */
    public long getDocumentAstrakhan() { return theDocumentAstrakhan ; }
    public void setDocumentAstrakhan(long aDocumentAstrakhan) { theDocumentAstrakhan = aDocumentAstrakhan ; }

    /** Документ для иногородних */
    public long getDocumentForeign() { return theDocumentForeign ; }
    public void setDocumentForeign(long aDocumentForeign) { theDocumentForeign = aDocumentForeign ; }

    /** Формат импорта */
    public long getFormat() { return theFormat ; }
    public void setFormat(long aFormat) { theFormat = aFormat ; }

    /** Формат импорта */
    /** Документ для астраханцев */
    private long theDocumentAstrakhan ;
    private long theFormat ;

    /** Файл */
    private FormFile theFile ;
    /** Документ для иногородних */
    private long theDocumentForeign ;

}
