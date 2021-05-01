package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.domain.contract.voc.VocJuridicalPerson;
import ru.ecom.mis.ejb.domain.contract.voc.VocServedPersonStatus;
import ru.ecom.mis.ejb.domain.contract.voc.VocServiceProgram;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
	/**
	 * Юридическое лицо
	 */
	@Comment("Юридическое лицо")
@Entity
@AIndexes(value = {
		@AIndex(properties = { "name" },table="ContractPerson")
		}
)

	@Getter
	@Setter
public class JuridicalPerson extends ContractPerson{
	/** Электронная почта */
	private String email;

	/** Телефоны */
	private String phones;

	/** Факс */
	private String fax;

	/** КПП */
	private String kpp;

	/** ИНН */
	private String inn;

	/** БИК */
	private String bic;

	/** Короткое название */
	private String shortName;

	/** Полное название */
	private String name;

	/** Директор */
	private String director;

	/** Почтовый адрес */
	private String postAddress;

	/** Счет */
	private String account;

	/** Корреспондентский счет */
	private String corAccount;

	/** Юридический адрес */
	private String juridicalAddress;

	/** Тип юридической персоны */
	@Comment("Тип юридической персоны")
	@OneToOne
	public VocJuridicalPerson getJuridicalPersonType() {
		return juridicalPersonType;
	}
	private VocJuridicalPerson juridicalPersonType;

	/** Организация */
	@Comment("Организация")
	@OneToOne
	public VocOrg getOrganization() {
		return organization;
	}
	private VocOrg organization;

	/** Статус обслуживаемой персоны */
	@Comment("Статус обслуживаемой персоны")
	@OneToOne
	public VocServedPersonStatus getServedPersonStatus() {
		return servedPersonStatus;
	}
	private VocServedPersonStatus servedPersonStatus;

	/** Программа обслуживания */
	@Comment("Программа обслуживания")
	@OneToOne
	public VocServiceProgram getServiceProgram() {
		return serviceProgram;
	}
	private VocServiceProgram serviceProgram;

	/** Территория */
	@Comment("Территория")
	@OneToOne
	public OmcKodTer getTerritory() {
		return territory;
	}
	private OmcKodTer territory;
	
	@Transient
	public String getInformation() {
		return "Юрид. лицо: " + name;
	}

	/** Страховая компания */
	@Comment("Страховая компания")
	@OneToOne
	public RegInsuranceCompany getRegCompany() {return regCompany;}
	private RegInsuranceCompany regCompany;
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return serviceStream;}
	private VocServiceStream serviceStream;

	/** ЛПУ*/
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {
		return lpu;
	}
	private MisLpu lpu;
}