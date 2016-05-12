package ru.ecom.jaas.web.action.service.genuser;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.Required;
import org.apache.struts.upload.FormFile;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 16.10.2006
 * Time: 2:54:50
 * To change this template use File | Settings | File Templates.
 */
public class ServiceGenUsersForm extends BaseValidatorForm {
    /** Роль */
    @Required
    public long getRoleId() { return theRoleId ; }
    public void setRoleId(long aRoleId) { theRoleId = aRoleId ; }


    /** file */
    public FormFile getFile() { return theFormFile ; }
    public void setFile(FormFile aFormFile) { theFormFile = aFormFile ; }

    /** file */
    private FormFile theFormFile ;
    /** Название в файле */
    private String theUserInFile ;
    /** Роль */
    private long theRoleId ;
}
