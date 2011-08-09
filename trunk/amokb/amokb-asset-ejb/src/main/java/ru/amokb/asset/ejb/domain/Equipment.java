package ru.amokb.asset.ejb.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Оборудование
	 */
	@Comment("Оборудование")
@Entity
@Table(schema="SQLUser")
public class Equipment extends PermanentAsset{
	/**
	 * Модель
	 */
	@Comment("Модель")
	
	public String getModel() {
		return theModel;
	}
	public void setModel(String aModel) {
		theModel = aModel;
	}
	/**
	 * Модель
	 */
	private String theModel;
	/**
	 * Серийный номер
	 */
	@Comment("Серийный номер")
	
	public String getSerialNumber() {
		return theSerialNumber;
	}
	public void setSerialNumber(String aSerialNumber) {
		theSerialNumber = aSerialNumber;
	}
	/**
	 * Серийный номер
	 */
	private String theSerialNumber;
	/**
	 * Учетный номер
	 */
	@Comment("Учетный номер")
	
	public String getAccountNumber() {
		return theAccountNumber;
	}
	public void setAccountNumber(String aAccountNumber) {
		theAccountNumber = aAccountNumber;
	}
	/**
	 * Учетный номер
	 */
	private String theAccountNumber;
	/**
	 * Автоматизированное рабочее место
	 */
	@Comment("Автоматизированное рабочее место")
	@ManyToOne
	public AutomatedWorkplace getAutomatedWorkplace() {
		return theAutomatedWorkplace;
	}
	public void setAutomatedWorkplace(AutomatedWorkplace aAutomatedWorkplace) {
		theAutomatedWorkplace = aAutomatedWorkplace;
	}
	/**
	 * Автоматизированное рабочее место
	 */
	private AutomatedWorkplace theAutomatedWorkplace;
	@OneToMany(mappedBy="equipment", cascade=CascadeType.ALL)
	public List<NetworkPointLink> getNetworkPointLinks() {
		return theNetworkPointLinks;
	}
	public void setNetworkPointLinks(List<NetworkPointLink> aNetworkPointLinks) {
		theNetworkPointLinks = aNetworkPointLinks;
	}
	/**
	 * Соединения с сетевыми точками
	 */
	private List<NetworkPointLink> theNetworkPointLinks;
}
