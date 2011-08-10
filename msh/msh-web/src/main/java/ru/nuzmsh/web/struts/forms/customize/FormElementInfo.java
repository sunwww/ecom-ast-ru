package ru.nuzmsh.web.struts.forms.customize;

/**
 * Данные об элементе формы
 */
public class FormElementInfo {

    public FormElementInfo(String aName) {
        theName = aName ;
    }

    public String getName() {
        return theName ;
    }
    /** Показывать на экране */
    public Boolean isVisible() { return theVisible ; }
    public Boolean getVisible() { return theVisible ; }
    public void setVisible(Boolean aVisible) { theVisible = aVisible ; }

    /** Значение по-умолчанию */
    public String getDefaultValue() { return theDefaultValue ; }
    public void setDefaultValue(String aDefaultValue) { theDefaultValue = aDefaultValue ; }

    /** Подпись к полю */
    public String getLabel() { return theLabel ; }
    public void setLabel(String aLabel) { theLabel = aLabel ; }

    /** Обязательный параметр */
    public Boolean isRequired() { return theRequired ; }
    public void setRequired(Boolean aRequired) { theRequired = aRequired ; }

    public String toString() {
        return new StringBuilder().append(getClass().getSimpleName())
                .append(" [ ").append(" name='").append(theName)
                .append("', visible=").append(theVisible)
                .append(", requred=").append(theRequired)
                .append(", label='").append(theLabel).append("' ]").toString() ;
    }

    /** Обязательный параметр */
    private Boolean theRequired ;
    /** Показывать на экране */
    private Boolean theVisible ;
    /** Значение по-умолчанию */
    private String theDefaultValue ;
    /** Подпись к полю */
    private String theLabel ;

    private final String theName ;

}
