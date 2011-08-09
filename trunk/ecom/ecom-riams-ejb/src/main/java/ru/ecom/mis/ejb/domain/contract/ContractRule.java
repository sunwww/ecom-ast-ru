package ru.ecom.mis.ejb.domain.contract;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.contract.ContractGuaranteeGroup;
import ru.ecom.mis.ejb.domain.contract.ContractMedServiceGroup;
import ru.ecom.mis.ejb.domain.contract.ContractNosologyGroup;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.ecom.mis.ejb.domain.contract.ServedPerson;
import ru.ecom.mis.ejb.domain.contract.voc.VocContractPermission;
import ru.ecom.mis.ejb.domain.contract.voc.VocContractRulePeriod;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Договорное правило
	 */
	@Comment("Договорное правило")
@Entity
@Table(schema="SQLUser")
public class ContractRule extends BaseEntity{
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
	 * Нозологическая группа
	 */
	@Comment("Нозологическая группа")
	@OneToOne
	public ContractNosologyGroup getNosologyGroup() {
		return theNosologyGroup;
	}
	public void setNosologyGroup(ContractNosologyGroup aNosologyGroup) {
		theNosologyGroup = aNosologyGroup;
	}
	/**
	 * Нозологическая группа
	 */
	private ContractNosologyGroup theNosologyGroup;
	/**
	 * Группа медицинских услуг
	 */
	@Comment("Группа медицинских услуг")
	@OneToOne
	public ContractMedServiceGroup getMedServiceGroup() {
		return theMedServiceGroup;
	}
	public void setMedServiceGroup(ContractMedServiceGroup aMedServiceGroup) {
		theMedServiceGroup = aMedServiceGroup;
	}
	/**
	 * Группа медицинских услуг
	 */
	private ContractMedServiceGroup theMedServiceGroup;
	/**
	 * Группа гарантийных документов
	 */
	@Comment("Группа гарантийных документов")
	@OneToOne
	public ContractGuaranteeGroup getGuaranteeGroup() {
		return theGuaranteeGroup;
	}
	public void setGuaranteeGroup(ContractGuaranteeGroup aGuaranteeGroup) {
		theGuaranteeGroup = aGuaranteeGroup;
	}
	/**
	 * Группа гарантийных документов
	 */
	private ContractGuaranteeGroup theGuaranteeGroup;
	/**
	 * Период действия
	 */
	@Comment("Период действия")
	@OneToOne
	public VocContractRulePeriod getPeriod() {
		return thePeriod;
	}
	public void setPeriod(VocContractRulePeriod aPeriod) {
		thePeriod = aPeriod;
	}
	/**
	 * Период действия
	 */
	private VocContractRulePeriod thePeriod;
	/**
	 * Количество медицинских услуг
	 */
	@Comment("Количество медицинских услуг")
	
	public Integer getMedserviceAmount() {
		return theMedserviceAmount;
	}
	public void setMedserviceAmount(Integer aMedserviceAmount) {
		theMedserviceAmount = aMedserviceAmount;
	}
	/**
	 * Количество медицинских услуг
	 */
	private Integer theMedserviceAmount;
	/**
	 * Количество курсов
	 */
	@Comment("Количество курсов")
	
	public Integer getCourseAmount() {
		return theCourseAmount;
	}
	public void setCourseAmount(Integer aCourseAmount) {
		theCourseAmount = aCourseAmount;
	}
	/**
	 * Количество курсов
	 */
	private Integer theCourseAmount;
	/**
	 * Количество медицинских услуг на курс
	 */
	@Comment("Количество медицинских услуг на курс")
	
	public Integer getMedserviceCourseAmount() {
		return theMedserviceCourseAmount;
	}
	public void setMedserviceCourseAmount(Integer aMedserviceCourseAmount) {
		theMedserviceCourseAmount = aMedserviceCourseAmount;
	}
	/**
	 * Количество медицинских услуг на курс
	 */
	private Integer theMedserviceCourseAmount;
	/**
	 * Разрешение
	 */
	@Comment("Разрешение")
	@OneToOne
	public VocContractPermission getPermission() {
		return thePermission;
	}
	public void setPermission(VocContractPermission aPermission) {
		thePermission = aPermission;
	}
	/**
	 * Разрешение
	 */
	private VocContractPermission thePermission;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	
	public Date getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(Date aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата начала действия
	 */
	private Date theDateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	
	public Date getDateTo() {
		return theDateTo;
	}
	public void setDateTo(Date aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата окончания действия
	 */
	private Date theDateTo;
	/**
	 * Обслуживаемая персона
	 */
	@Comment("Обслуживаемая персона")
	@OneToOne
	public ServedPerson getServedPerson() {
		return theServedPerson;
	}
	public void setServedPerson(ServedPerson aServedPerson) {
		theServedPerson = aServedPerson;
	}
	/**
	 * Обслуживаемая персона
	 */
	private ServedPerson theServedPerson;
	/**
	 * Раздельный учет по каждому виду медицинских услуг
	 */
	@Comment("Раздельный учет по каждому виду медицинских услуг")
	
	public Boolean getForEachMedservice() {
		return theForEachMedservice;
	}
	public void setForEachMedservice(Boolean aForEachMedservice) {
		theForEachMedservice = aForEachMedservice;
	}
	/**
	 * Раздельный учет по каждому виду медицинских услуг
	 */
	private Boolean theForEachMedservice;
}
