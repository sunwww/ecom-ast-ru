package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties = {"person"})
	,@AIndex(properties = {"contract"})
})
	@Getter
	@Setter
public class ServedPerson extends BaseEntity{
	/**
	 * Договорная персона
	 */
	@Comment("Договорная персона")
	@OneToOne
	public ContractPerson getPerson() {
		return person;
	}
	/**
	 * Договорная персона
	 */
	private ContractPerson person;
	/**
	 * Договор
	 */
	@Comment("Договор")
	@ManyToOne
	public MedContract getContract() {
		return contract;
	}
	/**
	 * Договор
	 */
	private MedContract contract;
	/**
	 * Дата начала обслуживания
	 */
	private Date dateFrom;
	/**
	 * Дата окончания обслуживания
	 */
	private Date dateTo;

	/**
	 * Признак авто создания счета 
	 */	
	private Boolean autoAccount;
	
	/** Информация */
	@Comment("Информация")
	@Transient
	public String getInfo() {return person!=null?person.getInformation():"нет информации";}
	/** Пользователь, последний изменивший запись */
	private String editUsername;
	/** Время, последнего изменения */
	private Time editTime;
	/** Дата последнего изменения */
	private Date editDate;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Время создания */
	private Time createTime;
	/** Дата создания */
	private Date createDate;
	/** Счет */
	@Comment("Счет")
	@OneToOne
	public ContractAccount getAccount() {return account;}
	/** Счет */
	private ContractAccount account;
}
