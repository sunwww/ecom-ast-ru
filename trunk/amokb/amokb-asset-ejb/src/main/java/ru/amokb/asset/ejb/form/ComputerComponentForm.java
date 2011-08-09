package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.ComputerComponent;
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
@EntityFormPersistance(clazz = ComputerComponent.class)
@Comment("Компонент компьютера")
@WebTrail(comment = "Компонент компьютера", nameProperties= "id", list="entityParentList-asset_computerComponent.do", view="entityParentView-asset_computerComponent.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class ComputerComponentForm extends IdEntityForm{
	/**
	 * Дата состояния
	 */
	@Comment("Дата состояния")
	@Persist
	@DateString @DoDateString
	public String getStateDate() {
		return theStateDate;
	}
	public void setStateDate(String aStateDate) {
		theStateDate = aStateDate;
	}
	/**
	 * Дата состояния
	 */
	private String theStateDate;
	/**
	 * Компьютер
	 */
	@Comment("Компьютер")
	@Persist
	public Long getComputer() {
		return theComputer;
	}
	public void setComputer(Long aComputer) {
		theComputer = aComputer;
	}
	/**
	 * Компьютер
	 */
	private Long theComputer;
	/**
	 * Компонент
	 */
	@Comment("Компонент")
	@Persist
	public Long getComponent() {
		return theComponent;
	}
	public void setComponent(Long aComponent) {
		theComponent = aComponent;
	}
	/**
	 * Компонент
	 */
	private Long theComponent;
	/**
	 * Дата установки
	 */
	@Comment("Дата установки")
	@Persist
	@DateString @DoDateString
	public String getEntranceDate() {
		return theEntranceDate;
	}
	public void setEntranceDate(String aEntranceDate) {
		theEntranceDate = aEntranceDate;
	}
	/**
	 * Дата установки
	 */
	private String theEntranceDate;
	/**
	 * Дата удаления
	 */
	@Comment("Дата удаления")
	@Persist
	@DateString @DoDateString
	public String getRemoveDate() {
		return theRemoveDate;
	}
	public void setRemoveDate(String aRemoveDate) {
		theRemoveDate = aRemoveDate;
	}
	/**
	 * Дата удаления
	 */
	private String theRemoveDate;
}
