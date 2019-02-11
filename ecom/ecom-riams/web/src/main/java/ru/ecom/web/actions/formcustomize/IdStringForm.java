package ru.ecom.web.actions.formcustomize;

import org.apache.struts.action.ActionForm;


public class IdStringForm extends ActionForm {

    /** Идентификатор */
    public String getId() { return theId ; }
    public void setId(String aId) { theId = aId ; }

    /** Идентификатор */
    private String theId ;
}


