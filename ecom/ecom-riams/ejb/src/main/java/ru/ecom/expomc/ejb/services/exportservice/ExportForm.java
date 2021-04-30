package ru.ecom.expomc.ejb.services.exportservice;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Параметры экспорта
 */
public class ExportForm extends BaseValidatorForm {

    /** Формат */
    @Required
    public Long getFormat() { return format ; }
    public void setFormat(Long aFormat) { format = aFormat ; }

    /** Документ */
    @Required
    public Long getDocument() { return document ; }
    public void setDocument(Long aDocument) { document = aDocument ; }

    
    /** Документ */
    private Long document ;
    /** Формат */
    private Long format ;
}
