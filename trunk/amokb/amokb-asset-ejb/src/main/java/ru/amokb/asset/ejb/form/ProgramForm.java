package ru.amokb.asset.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.amokb.asset.ejb.domain.Program;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.validators.IntegerString;

@EntityForm
@EntityFormPersistance(clazz = Program.class)
@Comment("Программа")
@WebTrail(comment = "Программа", nameProperties= "id", list="entityParentList-asset_programm.do", view="entityParentView-asset_programm.do")
@Parent(property="lpu", parentForm=MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/Program")
public class ProgramForm extends PermanentAssetForm{
	/**
	 * Версия
	 */
	@Comment("Версия")
	@Persist
	public String getVersion() {
		return theVersion;
	}
	public void setVersion(String aVersion) {
		theVersion = aVersion;
	}
	/**
	 * Версия
	 */
	private String theVersion;
	/**
	 * Количество лицензий
	 */
	@Comment("Количество лицензий")
	@Persist
	public int getLicenseAmount() {
		return theLicenseAmount;
	}
	public void setLicenseAmount(int aLicenseAmount) {
		theLicenseAmount = aLicenseAmount;
	}
	/**
	 * Количество лицензий
	 */
	private int theLicenseAmount;
}
