package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.AssetWorkcard;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = AssetWorkcard.class)
@Comment("Рабочая карточка актива")
@WebTrail(comment = "Рабочая карточка актива", nameProperties= "id", list="entityParentList-asset_assetWorkcard.do", view="entityParentView-asset_assetWorkcard.do")
@Parent(property="asset", parentForm=AssetForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Asset/AssetWorkcard")
public class AssetWorkcardForm extends IdEntityForm{
	/**
	 * Работа
	 */
	@Comment("Работа")
	@Persist
	public Long getAssetWork() {
		return theAssetWork;
	}
	public void setAssetWork(Long aAssetWork) {
		theAssetWork = aAssetWork;
	}
	/**
	 * Работа
	 */
	private Long theAssetWork;
	/**
	 * Дата работы
	 */
	@Comment("Дата работы")
	@Persist
	@DateString @DoDateString
	public String getWorkDate() {
		return theWorkDate;
	}
	public void setWorkDate(String aWorkDate) {
		theWorkDate = aWorkDate;
	}
	/**
	 * Дата работы
	 */
	private String theWorkDate;
	/**
	 * Исполнитель
	 */
	@Comment("Исполнитель")
	@Persist
	public Long getWorker() {
		return theWorker;
	}
	public void setWorker(Long aWorker) {
		theWorker = aWorker;
	}
	/**
	 * Исполнитель
	 */
	private Long theWorker;
	/**
	 * Заказчик
	 */
	@Comment("Заказчик")
	@Persist
	public Long getCustomer() {
		return theCustomer;
	}
	public void setCustomer(Long aCustomer) {
		theCustomer = aCustomer;
	}
	/**
	 * Заказчик
	 */
	private Long theCustomer;
	/**
	 * Время работы в часах
	 */
	@Comment("Время работы в часах")
	@Persist
	public String getWorkTime() {
		return theWorkTime;
	}
	public void setWorkTime(String aWorkTime) {
		theWorkTime = aWorkTime;
	}
	/**
	 * Время работы в часах
	 */
	private String theWorkTime;
	/**
	 * Нарушение опечатывания
	 */
	@Comment("Нарушение опечатывания")
	@Persist
	public Boolean getSealingBreak() {
		return theSealingBreak;
	}
	public void setSealingBreak(Boolean aSealingBreak) {
		theSealingBreak = aSealingBreak;
	}
	/**
	 * Нарушение опечатывания
	 */
	private Boolean theSealingBreak;
	/**
	 * Проведено опечатывание
	 */
	@Comment("Проведено опечатывание")
	@Persist
	public Boolean getSealing() {
		return theSealing;
	}
	public void setSealing(Boolean aSealing) {
		theSealing = aSealing;
	}
	/**
	 * Проведено опечатывание
	 */
	private Boolean theSealing;
	/**
	 * Актив
	 */
	@Comment("Актив")
	@Persist
	public Long getAsset() {
		return theAsset;
	}
	public void setAsset(Long aAsset) {
		theAsset = aAsset;
	}
	/**
	 * Актив
	 */
	private Long theAsset;
}
