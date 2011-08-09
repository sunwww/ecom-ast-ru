package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.AutomatedWorkplace;
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
@EntityFormPersistance(clazz = AutomatedWorkplace.class)
@Comment("Автоматизированное рабочее место")
@WebTrail(comment = "Автоматизированное рабочее место", nameProperties= "id", list="entityParentList-asset_automatedWorkplace.do", view="entityParentView-asset_automatedWorkplace.do",shortView="entityShortView-asset_automatedWorkplace.do")
@Parent(property="lpu", parentForm=MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace")
public class AutomatedWorkplaceForm extends PermanentAssetForm {
	/**
	 * Вычислительная сеть
	 */
	@Comment("Вычислительная сеть")
	@Persist
	public Long getNetwork() {
		return theNetwork;
	}
	public void setNetwork(Long aNetwork) {
		theNetwork = aNetwork;
	}
	/**
	 * Вычислительная сеть
	 */
	private Long theNetwork;
	/**
	 * Помещение
	 */
	@Comment("Помещение")
	@Persist
	public Long getRoom() {
		return theRoom;
	}
	public void setRoom(Long aRoom) {
		theRoom = aRoom;
	}
	/**
	 * Помещение
	 */
	private Long theRoom;
	/**
	 * Ответственный пользователь
	 */
	@Comment("Ответственный пользователь")
	@Persist
	public Long getResponsibleUser() {
		return theResponsibleUser;
	}
	public void setResponsibleUser(Long aResponsibleUser) {
		theResponsibleUser = aResponsibleUser;
	}
	/**
	 * Ответственный пользователь
	 */
	private Long theResponsibleUser;
	/** Территория */
	@Comment("Территория")
	@Persist
	public Long getTerritory() {
		return theTerritory;
	}

	public void setTerritory(Long aTerritory) {
		theTerritory = aTerritory;
	}

	/** Территория */
	private Long theTerritory;
	
	/** Здание */
	@Comment("Здание")
	@Persist
	public Long getBuilding() {
		return theBuilding;
	}

	public void setBuilding(Long aBuilding) {
		theBuilding = aBuilding;
	}

	/** Здание */
	private Long theBuilding;
}
