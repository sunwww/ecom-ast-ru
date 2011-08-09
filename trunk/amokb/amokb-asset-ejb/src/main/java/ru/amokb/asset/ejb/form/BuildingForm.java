package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.Building;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Building.class)
@Comment("Здание")
@WebTrail(comment = "Здание", nameProperties= "id", list="entityParentList-asset_building.do", view="entityParentView-asset_building.do",shortView="entityShortView-asset_building.do")
@Parent(property="territory", parentForm=TerritoryForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/Territory/Building")
public class BuildingForm extends PermanentAssetForm{
	/**
	 * Территория
	 */
	@Comment("Территория")
	@Persist
	public Long getTerritory() {
		return theTerritory;
	}
	public void setTerritory(Long aTerritory) {
		theTerritory = aTerritory;
	}
	/**
	 * Территория
	 */
	private Long theTerritory;
	/**
	 * Количество этажей
	 */
	@Comment("Количество этажей")
	@Persist
	public int getFloors() {
		return theFloors;
	}
	public void setFloors(int aFloors) {
		theFloors = aFloors;
	}
	/**
	 * Количество этажей
	 */
	private int theFloors;
}
