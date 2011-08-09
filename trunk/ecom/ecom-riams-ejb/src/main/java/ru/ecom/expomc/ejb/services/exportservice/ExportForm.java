package ru.ecom.expomc.ejb.services.exportservice;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Параметры экспорта
 */
public class ExportForm extends BaseValidatorForm {

    /** Формат */
    @Required
    public Long getFormat() { return theFormat ; }
    public void setFormat(Long aFormat) { theFormat = aFormat ; }

    /** Документ */
    @Required
    public Long getDocument() { return theDocument ; }
    public void setDocument(Long aDocument) { theDocument = aDocument ; }

    
    /** Документ */
    private Long theDocument ;
    /** Формат */
    private Long theFormat ;
}
