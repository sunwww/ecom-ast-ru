package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.voc.VocServedPersonStatus;
import ru.ecom.mis.ejb.domain.contract.voc.VocServiceProgram;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;

	/**
	 * Интервал гарантийных документов
	 */
	@Comment("Интервал гарантийных документов")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(properties = {"name"})
	})
	@Getter
	@Setter
public class GuaranteeInterval extends BaseEntity{
	/**
	 * Группа гарантийных документов
	 */
	@Comment("Группа гарантийных документов")
	@ManyToOne
	public ContractGuaranteeGroup getGuaranteeGroup() {
		return guaranteeGroup;
	}
	/**
	 * Группа гарантийных документов
	 */
	private ContractGuaranteeGroup guaranteeGroup;
	/**
	 * Начиная с номера
	 */
	private String fromNumber;
	/**
	 * Заканчивая номером
	 */
	private String toNumber;
	/**
	 * Маска выбора номеров
	 */
	private String numberMask;
	/**
	 * Название
	 */
	private String name;
	/**
	 * Серия
	 */
	private String series;
	/**
	 * Программа обслуживания
	 */
	@Comment("Программа обслуживания")
	@OneToOne
	public VocServiceProgram getServiceProgram() {
		return serviceProgram;
	}
	/**
	 * Программа обслуживания
	 */
	private VocServiceProgram serviceProgram;
	/**
	 * Статус обслуживаемой персоны
	 */
	@Comment("Статус обслуживаемой персоны")
	@OneToOne
	public VocServedPersonStatus getServedPersonStatus() {
		return servedPersonStatus;
	}
	/**
	 * Статус обслуживаемой персоны
	 */
	private VocServedPersonStatus servedPersonStatus;
	
	@Transient
	public String getServedPersonStatusInfo() {
		return servedPersonStatus!=null? servedPersonStatus.getName():"" ;
	}
	@Transient
	public String getServiceProgramInfo() {
		return serviceProgram!=null? serviceProgram.getName() :"" ;
	}
}
