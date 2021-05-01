package ru.ecom.mis.ejb.form.contract;
import lombok.Setter;
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
@Setter
public class NosologyIntervalForm extends IdEntityForm{
	/**
	 * Начиная с кода
	 */
	@Comment("Начиная с кода")
	@Persist @DoUpperCase @Required
	public Long getFromCode() {
		return fromCode;
	}
	/**
	 * Начиная с кода
	 */
	private Long fromCode;
	/**
	 * Заканчивая кодом
	 */
	@Comment("Заканчивая кодом")
	@Persist @DoUpperCase @Required
	public Long getToCode() {
		return toCode;
	}
	/**
	 * Заканчивая кодом
	 */
	private Long toCode;
	/**
	 * Нозологическая группа
	 */
	@Comment("Нозологическая группа")
	@Persist 
	public Long getNosologyGroup() {
		return nosologyGroup;
	}
	/**
	 * Нозологическая группа
	 */
	private Long nosologyGroup;

	/**
	 * Начиная с код МКБ10
	 */
	@Comment("Начиная с код МКБ10")
	@Persist @DoUpperCase
	public String getFromIdc10Code() {
		return fromIdc10Code;
	}
	/**
	 * Начиная с код МКБ10
	 */
	private String fromIdc10Code;
	/**
	 * Заканчивая кодом МКБ10
	 */
	@Comment("Заканчивая кодом МКБ10")
	@Persist @DoUpperCase
	public String getToIdc10Code() {
		return toIdc10Code;
	}
	/**
	 * Заканчивая кодом МКБ10
	 */
	private String toIdc10Code;

	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @DoUpperCase
	public String getName() {
		return name;
	}
	/**
	 * Название
	 */
	private String name;
}
