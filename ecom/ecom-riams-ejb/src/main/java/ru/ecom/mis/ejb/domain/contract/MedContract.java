package ru.ecom.mis.ejb.domain.contract;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.ContractGuarantee;
import ru.ecom.mis.ejb.domain.contract.ContractPerson;
import ru.ecom.mis.ejb.domain.contract.ContractRule;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.ecom.mis.ejb.domain.contract.PriceList;
import ru.ecom.mis.ejb.domain.contract.ServedPerson;
import ru.ecom.mis.ejb.domain.contract.voc.VocContractRulesProcessing;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Медицинский договор
	 */
	@Comment("Медицинский договор")
@Entity
@Table(schema="SQLUser")
@AIndexes(
		{
			@AIndex(properties={"parent"})
			,@AIndex(properties={"lpu"})
			,@AIndex(properties={"priceList"})
		}
)
public class MedContract extends BaseEntity{
	/**
	 * ЛПУ
	 */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {
		return theLpu;
	}
	public void setLpu(MisLpu aLpu) {
		theLpu = aLpu;
	}
	/**
	 * ЛПУ
	 */
	private MisLpu theLpu;
	/**
	 * Заказчик
	 */
	@Comment("Заказчик")
	@OneToOne
	public ContractPerson getCustomer() {
		return theCustomer;
	}
	public void setCustomer(ContractPerson aCustomer) {
		theCustomer = aCustomer;
	}
	/**
	 * Заказчик
	 */
	private ContractPerson theCustomer;
	@OneToMany(mappedBy="contract", cascade=CascadeType.ALL)
	public List<ServedPerson> getServedPersons() {
		return theServedPersons;
	}
	public void setServedPersons(List<ServedPerson> aServedPersons) {
		theServedPersons = aServedPersons;
	}
	/**
	 * Обслуживаемые персоны
	 */
	private List<ServedPerson> theServedPersons;
	@OneToMany(mappedBy="parent", cascade=CascadeType.ALL)
	public List<MedContract> getChildren() {
		return theChildren;
	}
	public void setChildren(List<MedContract> aChildren) {
		theChildren = aChildren;
	}
	/**
	 * Потомки
	 */
	private List<MedContract> theChildren;
	/**
	 * Родитель
	 */
	@Comment("Родитель")
	@ManyToOne
	public MedContract getParent() {
		return theParent;
	}
	public void setParent(MedContract aParent) {
		theParent = aParent;
	}
	/**
	 * Родитель
	 */
	private MedContract theParent;
	@OneToMany(mappedBy="contract", cascade=CascadeType.ALL)
	public List<ContractGuarantee> getGuarantees() {
		return theGuarantees;
	}
	public void setGuarantees(List<ContractGuarantee> aGuarantees) {
		theGuarantees = aGuarantees;
	}
	/**
	 * Гарантийные документы
	 */
	private List<ContractGuarantee> theGuarantees;
	@OneToMany(mappedBy="contract", cascade=CascadeType.ALL)
	public List<ContractRule> getRules() {
		return theRules;
	}
	public void setRules(List<ContractRule> aRules) {
		theRules = aRules;
	}
	/**
	 * Договорные правила
	 */
	private List<ContractRule> theRules;
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
	 * Описание
	 */
	@Comment("Описание")
	
	public String getComment() {
		return theComment;
	}
	public void setComment(String aComment) {
		theComment = aComment;
	}
	/**
	 * Описание
	 */
	private String theComment;
	/**
	 * Номер договора
	 */
	@Comment("Номер договора")
	
	public String getContractNumber() {
		return theContractNumber;
	}
	public void setContractNumber(String aContractNumber) {
		theContractNumber = aContractNumber;
	}
	/**
	 * Номер договора
	 */
	private String theContractNumber;
	/**
	 * Обработка правил
	 */
	@Comment("Обработка правил")
	@OneToOne
	public VocContractRulesProcessing getRulesProcessing() {
		return theRulesProcessing;
	}
	public void setRulesProcessing(VocContractRulesProcessing aRulesProcessing) {
		theRulesProcessing = aRulesProcessing;
	}
	/**
	 * Обработка правил
	 */
	private VocContractRulesProcessing theRulesProcessing;
	/**
	 * Прейскурант
	 */
	@Comment("Прейскурант")
	@OneToOne
	public PriceList getPriceList() {
		return thePriceList;
	}
	public void setPriceList(PriceList aPriceList) {
		thePriceList = aPriceList;
	}
	/**
	 * Прейскурант
	 */
	private PriceList thePriceList;
}
