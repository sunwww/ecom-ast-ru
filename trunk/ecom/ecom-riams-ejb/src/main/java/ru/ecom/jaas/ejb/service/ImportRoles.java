package ru.ecom.jaas.ejb.service;

import java.io.Serializable;
import java.util.List;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

public class ImportRoles implements Serializable{
	/** Роль */
	@Comment("Роль")
	public PolicyForm getRole() {return theRole;}
	public void setRole(PolicyForm aRole) {theRole = aRole;}

	/** Политики */
	@Comment("Политики")
	public List<PolicyForm> getPolicies() {return thePolicies;}
	public void setPolicies(List<PolicyForm> aPolicies) {thePolicies = aPolicies;}

	/** Политики */
	private List<PolicyForm> thePolicies;
	/** Роль */
	private PolicyForm theRole;

}
