package ru.ecom.mis.ejb.domain.contract;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.contract.ContractPerson;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
@Entity
@Table(schema="SQLUser")
public class ServedPerson extends BaseEntity{
	/**
	 * Договорная персона
	 */
	@Comment("Договорная персона")
	@OneToOne
	public ContractPerson getPerson() {
		return thePerson;
	}
	public void setPerson(ContractPerson aPerson) {
		thePerson = aPerson;
	}
	/**
	 * Договорная персона
	 */
	private ContractPerson thePerson;
	/**
	 * Договор
	 */
	@Comment("Договор")
	@ManyToOne
	public MedContract getContract() {
		return theContract;
	}
	public void setContract(MedContract aContract) {
		theContract = aContract;
	}
	/**
	 * Договор
	 */
	private MedContract theContract;
	/**
	 * Дата начала обслуживания
	 */
	@Comment("Дата начала обслуживания")
	
	public Date getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(Date aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата начала обслуживания
	 */
	private Date theDateFrom;
	/**
	 * Дата окончания обслуживания
	 */
	@Comment("Дата окончания обслуживания")
	
	public Date getDateTo() {
		return theDateTo;
	}
	public void setDateTo(Date aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата окончания обслуживания
	 */
	private Date theDateTo;
}
