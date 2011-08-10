package ru.nuzmsh.web.struts.forms.customize;

/**
 *  Данные о форме
 */
public class FormInfo {

    /** Название формы в Struts */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Название формы */
    private String theName ;
}
