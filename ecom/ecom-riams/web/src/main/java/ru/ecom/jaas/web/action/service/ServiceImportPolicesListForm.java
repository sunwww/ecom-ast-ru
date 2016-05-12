package ru.ecom.jaas.web.action.service;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import org.apache.struts.upload.FormFile;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 16.10.2006
 * Time: 2:25:58
 * To change this template use File | Settings | File Templates.
 */
public class ServiceImportPolicesListForm extends BaseValidatorForm {

    /** file */
    public FormFile getFile() { return theFile ; }
    public void setFile(FormFile aFile) { theFile = aFile ; }

    /** file */
    private FormFile theFile ;
}
