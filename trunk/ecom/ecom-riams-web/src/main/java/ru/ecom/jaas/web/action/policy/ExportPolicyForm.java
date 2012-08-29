package ru.ecom.jaas.web.action.policy;

import org.apache.struts.upload.FormFile;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;

public class ExportPolicyForm extends BaseValidatorForm {
	/** Название файла */
	@Comment("Название файла")
	public FormFile getFile() {return theFile;}
	public void setFile(FormFile aFileName) {theFile = aFileName;}

	/** Путь хранения */
	@Comment("Путь хранения")
	public String getDirName() {return theDirName;}
	public void setDirName(String aDirName) {theDirName = aDirName;}

	/** Политики */
	@Comment("Политики")
	public String getPolicies() {return thePolicies;}
	public void setPolicies(String aPolicies) {thePolicies = aPolicies;}

	/** Удалять политики? */
	@Comment("Удалять политики?")
	public Boolean getClear() {return theClear;}
	public void setClear(Boolean aClear) {theClear = aClear;}

	/** Тип  */
	@Comment("Тип ")
	public Long getType() {
		return theType;
	}

	public void setType(Long aType) {
		theType = aType;
	}
	
	/** Tmp file */
	@Comment("Tmp file")
	public String getTmpFile() {
		return theTmpFile;
	}

	public void setTmpFile(String aTmpFile) {
		theTmpFile = aTmpFile;
	}

	/** Tmp file */
	private String theTmpFile;
	/** Тип  */
	private Long theType;
	/** Удалять политики? */
	private Boolean theClear;
	/** Политики */
	private String thePolicies;
	/** Путь хранения */
	private String theDirName;
	/** Название файла */
	private FormFile theFile;
	/** ObjectId */
	@Comment("ObjectId")
	public Long getObjectId() {
		return theObjectId;
	}

	public void setObjectId(Long aObjectId) {
		theObjectId = aObjectId;
	}

	/** ObjectId */
	private Long theObjectId;

}
