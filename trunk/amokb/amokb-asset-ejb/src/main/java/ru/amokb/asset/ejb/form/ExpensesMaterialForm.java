package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.ExpensesMaterial;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExpensesMaterial.class)
@Comment("Расходный материал")
@WebTrail(comment = "Расходный материал", nameProperties= "id", list="entityParentList-asset_expensesMaterial.do", view="entityParentView-asset_expensesMaterial.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class ExpensesMaterialForm extends TransitoryAssetForm {
	/**
	 * Тип расходного материала
	 */
	@Comment("Тип расходного материала")
	@Persist
	public Long getMaterialType() {
		return theMaterialType;
	}
	public void setMaterialType(Long aMaterialType) {
		theMaterialType = aMaterialType;
	}
	/**
	 * Тип расходного материала
	 */
	private Long theMaterialType;
}
