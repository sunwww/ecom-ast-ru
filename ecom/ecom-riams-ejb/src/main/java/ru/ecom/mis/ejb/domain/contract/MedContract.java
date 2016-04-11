package ru.ecom.mis.ejb.domain.contract;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.contract.ContractGuarantee;
import ru.ecom.mis.ejb.domain.contract.ContractPerson;
import ru.ecom.mis.ejb.domain.contract.ContractRule;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.ecom.mis.ejb.domain.contract.PriceList;
import ru.ecom.mis.ejb.domain.contract.ServedPerson;
import ru.ecom.mis.ejb.domain.contract.voc.VocContractLabel;
import ru.ecom.mis.ejb.domain.contract.voc.VocContractRulesProcessing;
import ru.ecom.mis.ejb.domain.contract.voc.VocContractTerm;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
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
			,@AIndex(properties={"customer"})
		}
)
@EntityListeners(DeleteListener.class)
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
	
	/** Информация */
	@Comment("Информация")
	@Transient
	public String getInfo() {
		return new StringBuilder()
		.append(theParent==null?"основной":"поддоговор")
		.append(" №").append(theContractNumber).toString();
	}
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	
	/** Дата последнего изменения */
	@Comment("Дата последнего изменения")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время, последнего изменения */
	@Comment("Время, последнего изменения")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	
	/** Пользователь, последний изменивший запись */
	@Comment("Пользователь, последний изменивший запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, последний изменивший запись */
	private String theEditUsername;
	/** Время, последнего изменения */
	private Time theEditTime;
	/** Дата последнего изменения */
	private Date theEditDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Время создания */
	private Time theCreateTime;
	/** Дата создания */
	private Date theCreateDate;
	
	/** Лимит денег */
	@Comment("Лимит денег")
	public BigDecimal getLimitMoney() {return theLimitMoney;}
	public void setLimitMoney(BigDecimal aLimitMoney) {theLimitMoney = aLimitMoney;}

	/** Лимит денег */
	private BigDecimal theLimitMoney;

	/** Обязательно гарантийный документ */
	@Comment("Обязательно гарантийный документ")
	public Boolean getIsRequiredGuaratee() {
		return theIsRequiredGuaratee;
	}

	public void setIsRequiredGuaratee(Boolean aIsRequiredGuaratee) {
		theIsRequiredGuaratee = aIsRequiredGuaratee;
	}

	/** Обязательно гарантийный документ */
	private Boolean theIsRequiredGuaratee;
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {
		return theServiceStream;
	}

	public void setServiceStream(VocServiceStream aServiceStream) {
		theServiceStream = aServiceStream;
	}

	/** Поток обслуживания */
	private VocServiceStream theServiceStream;
	
	/** Метка договора */
	@Comment("Метка договора")
	@OneToOne
	public VocContractLabel getContractLabel() {return theContractLabel;}
	public void setContractLabel(VocContractLabel aContractLabel) {theContractLabel = aContractLabel;}

	/** Метка договора */
	private VocContractLabel theContractLabel;
	
	/** Срок договора */
	@Comment("Срок договора")
	@OneToOne
	public VocContractTerm getContractTerm() {return theContractTerm;}
	public void setContractTerm(VocContractTerm aContractTerm) {theContractTerm = aContractTerm;}

	/** Срок договора */
	private VocContractTerm theContractTerm;

}
