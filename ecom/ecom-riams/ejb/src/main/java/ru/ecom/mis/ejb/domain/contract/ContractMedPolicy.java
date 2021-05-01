package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.mis.ejb.domain.contract.voc.VocServedPersonStatus;
import ru.ecom.mis.ejb.domain.contract.voc.VocServiceProgram;
import ru.ecom.mis.ejb.domain.patient.MedPolicy;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;
	/**
	 * Медицинский полис по договору
	 */
	@Comment("Медицинский полис по договору")
@Entity
	@Getter
	@Setter
public class ContractMedPolicy extends ContractGuarantee {
	/** Медицинский полис */
	@Comment("Медицинский полис")
	@OneToOne
	public MedPolicy getMedPolicy() {
		return medPolicy;
	}
	private MedPolicy medPolicy;

	/** Фамилия */
	private String lastname;

	/** Имя */
	private String firstname;

	/** Отчество */
	private String middlename;

	/** День рождения */
	private Date birthday;

	/** Серия */
	private String series;

	/** Номер */
	private String number;

	/** Дата начала действия */
	private Date dateFrom;

	/** Дата окончания действия */
	private Date dateTo;

	/** Дата объявления недействительности */
	private Date nullityDate;

	/** Программа обслуживания */
	@Comment("Программа обслуживания")
	@OneToOne
	public VocServiceProgram getServiceProgram() {
		return serviceProgram;
	}
	private VocServiceProgram serviceProgram;

	/** Статус обслуживаемой персоны */
	@Comment("Статус обслуживаемой персоны")
	@OneToOne
	public VocServedPersonStatus getServedPersonStatus() {
		return servedPersonStatus;
	}
	private VocServedPersonStatus servedPersonStatus;

	/** Территория */
	@Comment("Территория")
	@OneToOne
	public OmcKodTer getTerritory() {
		return territory;
	}
	private OmcKodTer territory;
}