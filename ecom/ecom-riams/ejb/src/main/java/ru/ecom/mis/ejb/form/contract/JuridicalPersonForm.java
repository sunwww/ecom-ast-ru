package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.JuridicalPerson;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = JuridicalPerson.class)
@Comment("Юридическое лицо")
@WebTrail(comment = "Юридическое лицо", nameProperties= "id", list="entityParentList-contract_juridicalPerson.do", view="entityParentView-contract_juridicalPerson.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/ContractPerson/JuridicalPerson")
@Setter
public class JuridicalPersonForm extends ContractPersonForm{
	/**
	 * Электронная почта
	 */
	@Comment("Электронная почта")
	@Persist
	public String getEmail() {
		return email;
	}
	/**
	 * Электронная почта
	 */
	private String email;
	/**
	 * Телефоны
	 */
	@Comment("Телефоны")
	@Persist
	public String getPhones() {
		return phones;
	}
	/**
	 * Телефоны
	 */
	private String phones;
	/**
	 * Факс
	 */
	@Comment("Факс")
	@Persist
	public String getFax() {
		return fax;
	}
	/**
	 * Факс
	 */
	private String fax;
	/**
	 * КПП
	 */
	@Comment("КПП")
	@Persist
	public String getKpp() {
		return kpp;
	}
	/**
	 * КПП
	 */
	private String kpp;
	/**
	 * ИНН
	 */
	@Comment("ИНН")
	@Persist
	public String getInn() {
		return inn;
	}
	/**
	 * ИНН
	 */
	private String inn;
	/**
	 * БИК
	 */
	@Comment("БИК")
	@Persist
	public String getBic() {
		return bic;
	}
	/**
	 * БИК
	 */
	private String bic;
	/**
	 * Короткое название
	 */
	@Comment("Короткое название")
	@Persist @DoUpperCase
	public String getShortName() {
		return shortName;
	}
	/**
	 * Короткое название
	 */
	private String shortName;
	/**
	 * Полное название
	 */
	@Comment("Полное название")
	@Persist @Required @DoUpperCase
	public String getName() {
		return name;
	}
	/**
	 * Полное название
	 */
	private String name;
	/**
	 * Директор
	 */
	@Comment("Директор")
	@Persist
	public String getDirector() {
		return director;
	}
	/**
	 * Директор
	 */
	private String director;
	/**
	 * Почтовый адрес
	 */
	@Comment("Почтовый адрес")
	@Persist
	public String getPostAddress() {
		return postAddress;
	}
	/**
	 * Почтовый адрес
	 */
	private String postAddress;
	/**
	 * Счет
	 */
	@Comment("Счет")
	@Persist
	public String getAccount() {
		return account;
	}
	/**
	 * Счет
	 */
	private String account;
	/**
	 * Корреспондентский счет
	 */
	@Comment("Корреспондентский счет")
	@Persist
	public String getCorAccount() {
		return corAccount;
	}
	/**
	 * Корреспондентский счет
	 */
	private String corAccount;
	/**
	 * Юридический адрес
	 */
	@Comment("Юридический адрес")
	@Persist
	public String getJuridicalAddress() {
		return juridicalAddress;
	}
	/**
	 * Юридический адрес
	 */
	private String juridicalAddress;
	/**
	 * Тип юридической персоны
	 */
	@Comment("Тип юридической персоны")
	@Persist
	public Long getJuridicalPersonType() {
		return juridicalPersonType;
	}
	/**
	 * Тип юридической персоны
	 */
	private Long juridicalPersonType;
	/**
	 * Организация
	 */
	@Comment("Организация")
	@Persist
	public Long getOrganization() {
		return organization;
	}
	/**
	 * Организация
	 */
	private Long organization;
	/**
	 * Статус обслуживаемой персоны
	 */
	@Comment("Статус обслуживаемой персоны")
	@Persist
	public Long getServedPersonStatus() {
		return servedPersonStatus;
	}
	/**
	 * Статус обслуживаемой персоны
	 */
	private Long servedPersonStatus;
	/**
	 * Программа обслуживания
	 */
	@Comment("Программа обслуживания")
	@Persist
	public Long getServiceProgram() {
		return serviceProgram;
	}
	/**
	 * Программа обслуживания
	 */
	private Long serviceProgram;
	/**
	 * Территория
	 */
	@Comment("Территория")
	@Persist
	public Long getTerritory() {
		return territory;
	}
	/**
	 * Территория
	 */
	private Long territory;

	/** Страховая компания */
	@Comment("Страховая компания")
	@Persist
	public Long getRegCompany() {return regCompany;}

	/** Страховая компания */
	private Long regCompany;

	/**
	 * ЛПУ
	 */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {
		return lpu;
	}
	/**
	 * ЛПУ
	 */
	private Long lpu;
}
