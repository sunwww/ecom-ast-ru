package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.ExternalDataMedium;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExternalDataMedium.class)
@Comment("Внешний носитель информации")
@WebTrail(comment = "Внешний носитель информации", nameProperties= "id", list="entityParentList-asset_externalDataMedium.do", view="entityParentView-asset_externalDataMedium.do")
@Parent(property="lpu", parentForm=MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/ExternalDataMedium")
public class ExternalDataMediumForm extends PermanentAssetForm{
	/**
	 * Емкость в Гб
	 */
	@Comment("Емкость в Гб")
	@Persist
	public String getCapacity() {
		return theCapacity;
	}
	public void setCapacity(String aCapacity) {
		theCapacity = aCapacity;
	}
	/**
	 * Емкость в Гб
	 */
	private String theCapacity;
}
