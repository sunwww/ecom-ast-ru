package ru.ecom.jaas.web.action.service;

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
public class ServiceImportRolesForm extends BaseValidatorForm {
    /** Роль */
    @Required
    public long getRoleId() { return theRoleId ; }
    public void setRoleId(long aRoleId) { theRoleId = aRoleId ; }


    /** Название в файле */
    @Required
    public String getUserInFile() { return theUserInFile ; }
    public void setUserInFile(String aUserInFile) { theUserInFile = aUserInFile ; }

    /** file */
    public FormFile getFile() { return theFormFile ; }
    public void setFile(FormFile aFormFile) { theFormFile = aFormFile ; }

    /** file */
    private FormFile theFormFile ;
    /** Название в файле */
    private String theUserInFile ;
    /** Роль */
    private long theRoleId ;
    
    /** Clear */
	public Boolean getIsClear() {
		return theIsClear;
	}

	public void setIsClear(Boolean aIsClear) {
		theIsClear = aIsClear;
	}

	/** Clear */
	private Boolean theIsClear;
	/** Формат импорта */
	public Long getImportFormat() {
		return theImportFormat;
	}

	public void setImportFormat(Long aImportFormat) {
		theImportFormat = aImportFormat;
	}

	/** Формат импорта */
	private Long theImportFormat;
}
