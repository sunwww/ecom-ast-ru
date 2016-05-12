package ru.ecom.expomc.web.actions.format;

import ru.nuzmsh.forms.validator.BaseValidatorForm;

/**
 * @author esinev
 * Date: 22.08.2006
 * Time: 11:50:47
 */
public class FormatImportFromWordForm extends BaseValidatorForm {

    /** Идентификатор */
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Текст для импорта */
    public String getText() { return theText ; }
    public void setText(String aText) { theText = aText ; }

    /** Текст для импорта */
    private String theText ;
    /** Идентификатор */
    private long theId ;
}
