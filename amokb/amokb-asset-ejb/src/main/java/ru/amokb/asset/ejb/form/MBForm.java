package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.MB;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.validators.IntegerString;

@EntityForm
@EntityFormPersistance(clazz = MB.class)
@Comment("Материнская плата")
@WebTrail(comment = "Материнская плата", nameProperties= "id", list="entityParentList-asset_mB.do", view="entityParentView-asset_mB.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class MBForm extends ComponentForm{
	/**
	 * Тип разъема процессора
	 */
	@Comment("Тип разъема процессора")
	@Persist
	public Long getCpuSocketType() {
		return theCpuSocketType;
	}
	public void setCpuSocketType(Long aCpuSocketType) {
		theCpuSocketType = aCpuSocketType;
	}
	/**
	 * Тип разъема процессора
	 */
	private Long theCpuSocketType;
	/**
	 * Частота шины в МГц
	 */
	@Comment("Частота шины в МГц")
	@Persist
	public int getBusRate() {
		return theBusRate;
	}
	public void setBusRate(int aBusRate) {
		theBusRate = aBusRate;
	}
	/**
	 * Частота шины в МГц
	 */
	private int theBusRate;
	/**
	 * Количество слотов RAM
	 */
	@Comment("Количество слотов RAM")
	@Persist
	public int getRamSlots() {
		return theRamSlots;
	}
	public void setRamSlots(int aRamSlots) {
		theRamSlots = aRamSlots;
	}
	/**
	 * Количество слотов RAM
	 */
	private int theRamSlots;
	/**
	 * Тип RAM
	 */
	@Comment("Тип RAM")
	@Persist
	public Long getRamType() {
		return theRamType;
	}
	public void setRamType(Long aRamType) {
		theRamType = aRamType;
	}
	/**
	 * Тип RAM
	 */
	private Long theRamType;
}
