package ru.amokb.asset.ejb.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * ЛВС
	 */
	@Comment("ЛВС")
@Entity
@Table(schema="SQLUser")
public class Network extends PermanentAsset{
	@OneToMany(mappedBy="network")
	public List<AutomatedWorkplace> getAutomatedWorkplaces() {
		return theAutomatedWorkplaces;
	}
	public void setAutomatedWorkplaces(List<AutomatedWorkplace> aAutomatedWorkplaces) {
		theAutomatedWorkplaces = aAutomatedWorkplaces;
	}
	/**
	 * Автоматизированные рабочие места
	 */
	private List<AutomatedWorkplace> theAutomatedWorkplaces;
}
