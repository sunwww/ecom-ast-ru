package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.Territory;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Territory.class)
@Comment("Территория")
@WebTrail(comment = "Территория", nameProperties= "id", list="entityParentList-asset_territory.do", view="entityParentView-asset_territory.do",shortView="entityShortView-asset_territory.do")
@Parent(property="lpu", parentForm=MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/Territory")
public class TerritoryForm extends PermanentAssetForm{
}
