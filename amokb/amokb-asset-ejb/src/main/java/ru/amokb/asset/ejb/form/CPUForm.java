package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.CPU;
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
@EntityFormPersistance(clazz = CPU.class)
@Comment("Процессор")
@WebTrail(comment = "Процессор", nameProperties= "id", list="entityParentList-asset_cPU.do", view="entityParentView-asset_cPU.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class CPUForm extends ComponentForm{
	/**
	 * Количество ядер
	 */
	@Comment("Количество ядер")
	@Persist
	public int getCoreAmount() {
		return theCoreAmount;
	}
	public void setCoreAmount(int aCoreAmount) {
		theCoreAmount = aCoreAmount;
	}
	/**
	 * Количество ядер
	 */
	private int theCoreAmount;
	/**
	 * Частота в МГц
	 */
	@Comment("Частота в МГц")
	@Persist
	public int getRate() {
		return theRate;
	}
	public void setRate(int aRate) {
		theRate = aRate;
	}
	/**
	 * Частота в МГц
	 */
	private int theRate;
	/**
	 * Разъем
	 */
	@Comment("Разъем")
	@Persist
	public String getSocket() {
		return theSocket;
	}
	public void setSocket(String aSocket) {
		theSocket = aSocket;
	}
	/**
	 * Разъем
	 */
	private String theSocket;
	/**
	 * Тип разъема
	 */
	@Comment("Тип разъема")
	@Persist
	public Long getSocketType() {
		return theSocketType;
	}
	public void setSocketType(Long aSocketType) {
		theSocketType = aSocketType;
	}
	/**
	 * Тип разъема
	 */
	private Long theSocketType;
}
