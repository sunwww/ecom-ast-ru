// GENERATED. DO NOT EDIT !!!
package ru.ecom.web.actions.formcustomize ;

import java.io.Serializable;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.fields.ComboBox;

public class FormCustomizeForm extends  BaseValidatorForm implements Serializable {

    /**
     * null
     */
    public String getFormName() { return theFormName ; }
    public void setFormName(String aFormName) { theFormName = aFormName ; }
    private String theFormName ;
    /** null  */
    public static final String FIELD_FORM_NAME = "formName" ;

    /**
     * null
     */
    public String getElementName() { return theElementName ; }
    public void setElementName(String aElementName) { theElementName = aElementName ; }
    private String theElementName ;
    /** null  */
    public static final String FIELD_ELEMENT_NAME = "elementName" ;

    /**
     * null
     */
    public String getOrigLabel() { return theOrigLabel ; }
    public void setOrigLabel(String aOrigLabel) { theOrigLabel = aOrigLabel ; }
    private String theOrigLabel ;
    /** null  */
    public static final String FIELD_ORIG_LABEL = "origLabel" ;

    /**
     * null
     */
    public String getRequired() { return theRequired ; }
    public void setRequired(String aRequired) { theRequired = aRequired ; }
    private String theRequired ;
    /** null  */
    public static final String FIELD_REQUIRED = "required" ;

    /**
     * null
     */
    @ComboBox(voc="visible")
    public String getVisible() { return theVisible ; }
    public void setVisible(String aVisible) { theVisible = aVisible ; }
    private String theVisible ;
    /** null  */
    public static final String FIELD_VISIBLE = "visible" ;

    /**
     * null
     */
    public String getDefault() { return theDefault ; }
    public void setDefault(String aDefault) { theDefault = aDefault ; }
    private String theDefault ;
    /** null  */
    public static final String FIELD_DEFAULT = "default" ;

    /**
     * null
     */
    public String getNewLabel() { return theLabel ; }
    public void setNewLabel(String aLabel) { theLabel = aLabel ; }
    private String theLabel ;
    /** null  */
    public static final String FIELD_LABEL = "newLabel" ;

    /**
     * null
     */
    public String getNextUrl() { return theNextUrl ; }
    public void setNextUrl(String aNextUrl) { theNextUrl = aNextUrl ; }
    private String theNextUrl ;
    /** null  */
    public static final String FIELD_NEXT_URL = "nextUrl" ;

}
