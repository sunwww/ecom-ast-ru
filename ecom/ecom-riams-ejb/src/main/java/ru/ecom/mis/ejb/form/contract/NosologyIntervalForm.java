package ru.ecom.mis.ejb.form.contract;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.NosologyInterval;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;
@EntityForm
@EntityFormPersistance(clazz = NosologyInterval.class)
@Comment("Интервал нозологий")
@WebTrail(comment = "Интервал нозологий", nameProperties= "id", list="entityParentList-contract_nosologyInterval.do", view="entityParentView-contract_nosologyInterval.do")
@Parent(property="nosologyGroup", parentForm=ContractNosologyGroupForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/GroupRules/ContractNosologyGroup/NosologyInterval")
public class NosologyIntervalForm extends IdEntityForm{
	/**
	 * Начиная с кода
	 */
	@Comment("Начиная с кода")
	@Persist @DoUpperCase @Required
	public Long getFromCode() {
		return theFromCode;
	}
	public void setFromCode(Long aFromCode) {
		theFromCode = aFromCode;
	}
	/**
	 * Начиная с кода
	 */
	private Long theFromCode;
	/**
	 * Заканчивая кодом
	 */
	@Comment("Заканчивая кодом")
	@Persist @DoUpperCase @Required
	public Long getToCode() {
		return theToCode;
	}
	public void setToCode(Long aToCode) {
		theToCode = aToCode;
	}
	/**
	 * Заканчивая кодом
	 */
	private Long theToCode;
	/**
	 * Нозологическая группа
	 */
	@Comment("Нозологическая группа")
	@Persist 
	public Long getNosologyGroup() {
		return theNosologyGroup;
	}
	public void setNosologyGroup(Long aNosologyGroup) {
		theNosologyGroup = aNosologyGroup;
	}
	/**
	 * Нозологическая группа
	 */
	private Long theNosologyGroup;

	/**
	 * Начиная с код МКБ10
	 */
	@Comment("Начиная с код МКБ10")
	@Persist @DoUpperCase
	public String getFromIdc10Code() {
		return theFromIdc10Code;
	}
	public void setFromIdc10Code(String aFromIdc10Code) {
		theFromIdc10Code = aFromIdc10Code;
	}
	/**
	 * Начиная с код МКБ10
	 */
	private String theFromIdc10Code;
	/**
	 * Заканчивая кодом МКБ10
	 */
	@Comment("Заканчивая кодом МКБ10")
	@Persist @DoUpperCase
	public String getToIdc10Code() {
		return theToIdc10Code;
	}
	public void setToIdc10Code(String aToIdc10Code) {
		theToIdc10Code = aToIdc10Code;
	}
	/**
	 * Заканчивая кодом МКБ10
	 */
	private String theToIdc10Code;

	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @DoUpperCase
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	/**
	 * Название
	 */
	private String theName;
}
