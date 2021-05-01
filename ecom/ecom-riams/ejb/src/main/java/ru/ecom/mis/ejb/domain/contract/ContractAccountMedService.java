package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Мед услуги по счету
 */
@Comment("Мед услуги по счету")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties = {"account"})
	,@AIndex(properties = {"medService"})
	
})
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class ContractAccountMedService extends BaseEntity{

	/** Рабочая функция */
	@OneToOne
	public WorkFunction getWorkFunction() {return workFunction;}
	private WorkFunction workFunction;

	/** Признак удаленной записи */
	private Boolean isDeleted ;

	/** Договорной счет */
	@Comment("Договорной счет")
	@ManyToOne
	public ContractAccount getAccount() {
		return account;
	}
	private ContractAccount account;

	/** Мед Услуга */
	@Comment("Мед Услуга")
	@OneToOne
	public PriceMedService getMedService() {
		return medService;
	}
	private PriceMedService medService;

	/** Количество */
	private Integer countMedService;
	
	private BigDecimal cost;

	/** Обслуживаемая персона */
	@Comment("Обслуживаемая персона")
	@OneToOne
	public ServedPerson getServedPerson() {return servedPerson;}
	private ServedPerson servedPerson;
	
	/** Гарантийное письмо */
	@Comment("Гарантийное письмо")
	@OneToOne
	public ContractGuarantee getGuarantee() {return guarantee;}
	private ContractGuarantee guarantee;
	
	/** Диагноз */
	@Comment("Диагноз")
	@OneToOne
	public VocIdc10 getIdc10() {return idc10;}
	private VocIdc10 idc10;

	/** Дата начала */
	private Date dateFrom;
	
	/** Дата окончания */
	private Date dateTo;

	/** СМО */
	private Long smo;

	/** Фамилия */
	private String lastname;

	/** Имя */
	private String firstname;

	/** Отчество */
	private String middlename;

	/** Дата рождения */
	private Date birthday;

	/** Тип услуги */
	private String typeService;

	/** ИД мед. случая */
	private Long idService ;
	
	/** Летальный исход */
	private Boolean isDeath;
	
	/** Полис */
	private String polSeries;

	/** Номер полиса */
	private String polNumber;

	/** Услуга внутр */
	private Long serviceIn;

	/** Рабочая функция */
	private Long doctor;
	
	/** Основной СМО */
	private Long mainParent;
	
	/** Диагноз */
	private Long diagnosis;
	
	/** Проверино */
	private Boolean isCheck;
	
	/** Удаленная запись */
	private Boolean isDelete;

	/** Отредактированная запись */
	private Boolean isEdit;
	
	/** Добавленная запись */
	private Boolean isCreate;
	
	/** Пациент */
	private Long patient;

	/** Комлексная услуга, частью которой является текущий объект */
	private Long fromComplexMedServiceId;
}
