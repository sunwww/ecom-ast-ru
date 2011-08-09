package ru.amokb.asset.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.amokb.asset.ejb.domain.TransitoryAsset;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = TransitoryAsset.class)
@Comment("")
@WebTrail(comment = "", nameProperties= "id", list="entityParentList-asset_transitoryAsset.do", view="entityParentView-asset_transitoryAsset.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
@Subclasses(value={ComponentForm.class, ExpensesMaterialForm.class
		,CPUForm.class,MBForm.class,RAMForm.class,LaserDriveForm.class,
		NetworkCardForm.class,OtherComponentForm.class,FloppyDriveForm.class
		,VideoCardForm.class,HDDForm.class})
public class TransitoryAssetForm extends AssetForm{
	/**
	 * Модель
	 */
	@Comment("Модель")
	@Persist
	public String getModel() {
		return theModel;
	}
	public void setModel(String aModel) {
		theModel = aModel;
	}
	/**
	 * Модель
	 */
	private String theModel;
}
